package com.escape.way.controller;

import com.escape.way.config.DateTimeUtil;
import com.escape.way.config.RedisUtil;
import com.escape.way.config.TokenUtil;
import com.escape.way.config.logging.LogEntry;
import com.escape.way.dto.*;
import com.escape.way.error.CustomException;
import com.escape.way.error.ErrorCode;
import com.escape.way.model.Appointment;
import com.escape.way.model.User;
import com.escape.way.service.AppointmentService;
import com.escape.way.service.UAMapService;
import com.escape.way.service.UserService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/api/user/*")
@Controller
public class UserController {

  @Autowired
  UserService userService;
  @Autowired
  UAMapService uaMapService;
  @Autowired
  AppointmentService appointmentService;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private TokenUtil tokenUtil;
  @Autowired
  private RedisUtil redisUtil;
  @Autowired
  private DateTimeUtil dateTimeUtil;

  //가입
  @RequestMapping(value = "/", method = RequestMethod.POST)
  @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
  public ResponseEntity<String> join(@RequestBody UserRegisterRequest userInfo) throws RuntimeException{

    if (userInfo.getName() == null || userInfo.getName().isEmpty()) {
      throw new CustomException(ErrorCode.EMPTY_USER_NAME);
    }

    if (userInfo.getPassword() == null || userInfo.getPassword().isEmpty()) {
      throw new CustomException(ErrorCode.EMPTY_PASSWORD);
    }

    if (userInfo.getUserId() == null || userInfo.getUserId().isEmpty()) {
      throw new CustomException(ErrorCode.EMPTY_USER_ID);
    }

    if (userService.checkIdDuplicate(userInfo.getUserId())) {
      throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
    }

    User user = new User();
    user.setUserId(userInfo.getUserId());
    user.setName(userInfo.getName());
    user.setPw(userInfo.getPassword());

    user.setLatitude(0);
    user.setLongitude(0);

    user.setAuth("ROLE_USER");
    user.setKakaoId(1L);

    ZonedDateTime curTime = ZonedDateTime.now();

    user.setUpdateTime(curTime);

    userService.joinUser(user);
    return ResponseEntity.ok("Success");
  }

  @RequestMapping(value = "/login", method= RequestMethod.POST)
  @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
  public ResponseEntity<?> createAuthenticationToken(@RequestBody UserAuthRequest userInfo) throws Exception {
    String userId = userInfo.getUserId();
    String password = userInfo.getPassword();

    try {
      authenticate(userId, password);
    }
    catch(Exception e) {
      throw new CustomException(ErrorCode.LOGIN_FAILED);
    }

    final User user = userService.loadUserByUsername(userId);

    final String accessToken = tokenUtil.generateAccessToken(user);
    final String refreshToken = tokenUtil.generateRefreshToken(user);

    redisUtil.setDataExpire(refreshToken, user.getUserId(), tokenUtil.REFRESH_TOKEN_VALIDATION);

    userService.setUpdateTime(user.getUserNo(), ZonedDateTime.now(ZoneId.of("UTC")));

    return ResponseEntity.ok(new TokenResponse(user.getUserId(), accessToken, refreshToken));
  }

  @ResponseBody
  @RequestMapping(value = "/reAuth", method= RequestMethod.POST)
  @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
  public ResponseEntity<?> createRefreshToAccessToken(@RequestBody HashMap<String, String> RefreshToken) throws RuntimeException {
    String refreshToken = RefreshToken.get("RefreshToken");
    String userId = null;

    try {
      userId = redisUtil.getData(refreshToken);

      if (userId.equals(tokenUtil.getUsernameFromToken(refreshToken))) {
        User user = userService.loadUserByUsername(userId);

        String returnAccessToken = tokenUtil.generateAccessToken(user);

        return ResponseEntity.ok(new TokenResponse(user.getUserId(), returnAccessToken, "None"));
      }
      else {
        throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
      }
    }
    catch (Exception e) {
      throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
    }
  }

  private void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
    catch(DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    }
    catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }

  // 구현 예정
  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public String logoutPage(HttpServletRequest req, HttpServletResponse res) {
    new SecurityContextLogoutHandler().logout(req, res, SecurityContextHolder.getContext().getAuthentication());
    return "redirect:/login";
  }

  // 중복 확인
  @RequestMapping(value = "/{userId}/is-user", method = RequestMethod.GET)
  @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
  public ResponseEntity<Boolean> checkIdDuplicate(@PathVariable("userId") String id) {
    return ResponseEntity.ok(userService.checkIdDuplicate(id));
  }

  //유저 삭제
  @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
  public ResponseEntity<String> deleteUser(@PathVariable("userId") String id) {
    User u = userService.getUserById(id);
    try {
      List<Long> ownerAppointmentList = uaMapService.findOwnerAppointmentNoByUserNo(u.getUserNo());

      for (Iterator<Long> iter = ownerAppointmentList.iterator(); iter.hasNext(); ) {
        Long appointmentNo = iter.next();
        System.out.println(appointmentNo);

        uaMapService.deleteUAMapByAppointmentNo(appointmentNo);
        appointmentService.deleteAppointment(appointmentNo);
      }

      uaMapService.deleteUAMapByUserNo(u.getUserNo());
      int userDeletedCnt = userService.deleteUser(u.getUserId());

      if (userDeletedCnt > 0) return ResponseEntity.ok("Success");
      else return ResponseEntity.ok("Fail");
    }
    catch (Exception e) {
      throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
    }
  }

  @RequestMapping(value="/{userId}/owner-appointment-list", method=RequestMethod.GET)
  public ResponseEntity<List<AppointmentResponse>> getOwnerAppointmentList(@PathVariable("userId") String id) throws Exception {
    User u = userService.getUserById(id);
    try {
      List<Long> ownerAppointmentList = uaMapService.findOwnerAppointmentNoByUserNo(u.getUserNo());

      List<AppointmentResponse> result = new ArrayList<>();

      for (Iterator<Long> iter = ownerAppointmentList.iterator(); iter.hasNext(); ) {
        Long appointmentNo = iter.next();
        Appointment appointment = appointmentService.getAppointment(appointmentNo);

        AppointmentResponse tempAppointment = new AppointmentResponse(appointment.getAppointmentNo(), appointment.getName(),
                appointment.getPlaceName(), appointment.getLatitude(), appointment.getLongitude(),
                dateTimeUtil.dateTime2String(appointment.getTime()) );
        result.add(tempAppointment);
      }

      return ResponseEntity.ok(result);
    }
    catch (Exception e) {
      throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
    }
  }

  // 유저의 최근 업데이트 시간
  @RequestMapping(value = "/{userId}/time", method = RequestMethod.GET)
  @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
  public ResponseEntity<String> getUpdateTime(@PathVariable("userId") String userId) throws Exception {
    Long no = userService.getUserById(userId).getUserNo();
    return ResponseEntity.ok(userService.getUpdateTime(no));
  }

  // 유저의 업데이트 시간 갱신
  @RequestMapping(value = "/{userId}/time", method=RequestMethod.PUT)
  public  ResponseEntity<String> setUserUpdateTime(@PathVariable("userId") String userId, @RequestBody UpdateTimeRequest time) throws Exception {
    try {
      User user = userService.getUserById(userId);

      ZonedDateTime updateTime = dateTimeUtil.String2DateTime(time.getTime());
      System.out.println(updateTime);

      userService.setUpdateTime(user.getUserNo(), updateTime);

      return ResponseEntity.ok("Success");
    }
    catch(Exception e) {
      throw new CustomException(ErrorCode.INVALID_APPOINTMENT_TIME);
    }
  }
}
