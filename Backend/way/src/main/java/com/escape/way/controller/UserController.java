package com.escape.way.controller;

import com.escape.way.config.logging.LogEntry;
import com.escape.way.dto.UserRegisterRequest;
import com.escape.way.error.CustomException;
import com.escape.way.error.ErrorCode;
import com.escape.way.model.User;
import com.escape.way.service.UAMapService;
import com.escape.way.service.UserService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.escape.way.vo.UserPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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

  @Value("${time.timezone}")
  String timezone = "Asia/Seoul";

  //가입
  @RequestMapping(value = "/join", method = RequestMethod.POST)
  @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
  public ResponseEntity<String> join(UserRegisterRequest userInfo) throws RuntimeException{

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

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    String curTime = ZonedDateTime.now(ZoneId.of(timezone)).format(formatter);

    System.out.println(curTime);
    user.setUpdateTime(curTime);
    System.out.println(user.getUpdateTime());

    userService.joinUser(user);
    return ResponseEntity.ok("Success");
  }

  // 구현 예정
  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public String logoutPage(HttpServletRequest req, HttpServletResponse res) {
    new SecurityContextLogoutHandler().logout(req, res, SecurityContextHolder.getContext().getAuthentication());
    return "redirect:/login";
  }

  // 중복 확인
  @RequestMapping(value = "/exists", method = RequestMethod.GET)
  @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
  public ResponseEntity<Boolean> checkIdDuplicate(@RequestParam String id) {
    return ResponseEntity.ok(userService.checkIdDuplicate(id));
  }

  //유저 삭제
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {
    if (userService.deleteUser(id) > 0) return ResponseEntity.ok("Success");
    else return ResponseEntity.ok("Fail");
  }

  // 중복 확인
  @RequestMapping(value = "/getTime", method = RequestMethod.GET)
  @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
  public ResponseEntity<String> getUpdateTime(@RequestParam String userId) throws Exception {
    Long no = userService.getUserById(userId).getUserNo();
    return ResponseEntity.ok(userService.getUpdateTime(no));
  }
}
