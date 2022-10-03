package com.escape.way.controller;

import com.escape.way.config.CryptUtil;
import com.escape.way.config.DateTimeUtil;
import com.escape.way.config.RedisUtil;
import com.escape.way.config.logging.LogEntry;
import com.escape.way.dto.AppointmentResponse;
import com.escape.way.dto.AppointmentUserListResponse;
import com.escape.way.dto.CreateAppointmentRequest;
import com.escape.way.dto.UpdateTimeRequest;
import com.escape.way.error.CustomException;
import com.escape.way.error.ErrorCode;
import com.escape.way.model.Appointment;
import com.escape.way.model.User;
import com.escape.way.service.AppointmentService;
import com.escape.way.service.UAMapService;
import com.escape.way.service.UserService;
import com.escape.way.vo.UserPlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RequestMapping(value = "/api/appointment/*")
@Controller
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;
    @Autowired
    UAMapService uaMapService;
    @Autowired
    UserService userService;
    @Autowired
    DateTimeUtil dateTimeUtil;
    @Autowired
    RedisUtil redisUtil;

    //약속 생성
    @PostMapping(value = "/")
    @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
    public @ResponseBody ResponseEntity<String> createAppointment(
            @RequestBody CreateAppointmentRequest body) throws Exception {
        Appointment appointment = new Appointment();
        User u = userService.getUserById(body.getUserId());

        if (body.getName() == null || body.getName().isEmpty()) {
            throw new CustomException(ErrorCode.EMPTY_APPOINTMENT_NAME);
        }

        if (body.getPlaceName() == null || body.getPlaceName().isEmpty()) {
            throw new CustomException(ErrorCode.EMPTY_APPOINTMENT_PLACE);
        }

        if (body.getTime() == null || body.getTime().isEmpty()) {
            throw new CustomException(ErrorCode.EMPTY_APPOINTMENT_TIME);
        }

        ZonedDateTime curTime = ZonedDateTime.now(ZoneId.of("UTC"));

        appointment.setUpdateTime(curTime);
        appointment.setName(body.getName());
        appointment.setPlaceName(body.getPlaceName());
        appointment.setLatitude(body.getLatitude());
        appointment.setLongitude(body.getLongitude());

        try {
            ZonedDateTime appointmentTime = dateTimeUtil.String2DateTime(body.getTime());

            appointment.setTime(appointmentTime);
        }
        catch(Exception e) {
            throw new CustomException(ErrorCode.INVALID_APPOINTMENT_TIME);
        }

        Long appointmentNo = appointmentService.createAppointment(appointment);

        uaMapService.setUAMap(appointmentNo, u.getUserNo(), true);

        return ResponseEntity.ok("success");
    }

    @RequestMapping(value = "/link", method=RequestMethod.GET)
    @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
    public ResponseEntity<String> createLink(@RequestParam Long no, @RequestParam String userId) throws Exception {
        User u = userService.getUserById(userId);
        Appointment a = appointmentService.getAppointment(no);

        if (u.getUserNo() != uaMapService.findOwnerNoByAppointmentNo(no)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_APPOINTMENT);
        }

        String apNo = no.toString();
        String curTime = dateTimeUtil.dateTime2String(ZonedDateTime.now(ZoneId.of("UTC")));

        String beforeEncrtpy = apNo+"+"+curTime;
        CryptUtil cryptUtil = new CryptUtil();
        String link = cryptUtil.encrypt(beforeEncrtpy);

        redisUtil.setDataExpire(link, apNo, 1000);

        return ResponseEntity.ok(link);
    }

    @ResponseBody
    @RequestMapping(value = "/add-user", method=RequestMethod.GET)
    @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
    public AppointmentResponse addUser(@RequestParam String link, @RequestParam String userId) throws Exception {

        String apNo = redisUtil.getData(link);
        Long no = Long.parseLong(apNo);
        if(apNo == null) throw new CustomException(ErrorCode.INVALID_LINK);

        //1. user id로 user no 검색
        User u = userService.getUserById(userId);

        //2. uaMap에 같은 항목 있으면 안녕
        if (uaMapService.existUAMap(no, u.getUserNo())) {

            //3. uaMap에 insert
            uaMapService.setUAMap(no, u.getUserNo(), false);
        } else throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);

        //4. no 해당하는 약속정보 가져와서 return
        Appointment a = appointmentService.getAppointment(no);

        String appointmentTime = dateTimeUtil.dateTime2String(a.getTime());
        AppointmentResponse appointment =
                new AppointmentResponse(a.getAppointmentNo(), a.getName(), a.getPlaceName(),
                        a.getLatitude(), a.getLongitude(), appointmentTime);

        return appointment;
    }

    // userid를 가진 약속 리스트
    @RequestMapping(value = "/{userId}/lists", method=RequestMethod.GET)
    @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
    public @ResponseBody
    ResponseEntity<List<AppointmentResponse>> getAppointmentByUserId(@PathVariable("userId") String userId) throws Exception {

        List<Appointment> appointments = appointmentService.getAppointmentList(userId);
        List<AppointmentResponse> listResponse = new ArrayList<>();

        for (Iterator<Appointment> iter = appointments.iterator(); iter.hasNext();) {
            Appointment temp = iter.next();

            String appointmentTime = dateTimeUtil.dateTime2String(temp.getTime());

            AppointmentResponse appointmentRe =
                    new AppointmentResponse(temp.getAppointmentNo(), temp.getName(),
                            temp.getPlaceName(), temp.getLatitude(), temp.getLongitude(), appointmentTime);
            listResponse.add(appointmentRe);
        }

        if(appointments == null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(listResponse);
    }

    // appointment ID에 해당되는 약속
    @RequestMapping(value = "/{appointmentNo}", method=RequestMethod.GET)
    public  ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable("appointmentNo") Long no) throws Exception {
        Appointment appointment = appointmentService.getAppointment(no);

        String appointmentTime = dateTimeUtil.dateTime2String(appointment.getTime());

        AppointmentResponse resp = new AppointmentResponse(appointment.getAppointmentNo(), appointment.getName(),
                appointment.getPlaceName(), appointment.getLatitude(), appointment.getLongitude(), appointmentTime);

        return ResponseEntity.ok(resp);
    }

    // 약속 삭제
    @RequestMapping(value="/{appointmentNo}", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteAppointment(@PathVariable("appointmentNo") Long no) throws Exception {

        try {
            int uaMapDeletedCnt = uaMapService.deleteUAMapByAppointmentNo(no);
            boolean appointmentDeleted = appointmentService.deleteAppointment(no);

            if (appointmentDeleted && uaMapDeletedCnt > 0) {
                return ResponseEntity.ok("Success");
            }
            else {
                return ResponseEntity.ok("Failed");
            }
        }
        catch(Exception e) {
            throw new CustomException(ErrorCode.INVALID_APPOINTMENT_NO);
        }
    }

    @RequestMapping(value = "/{appointmentNo}/times", method=RequestMethod.PUT)
    public  ResponseEntity<String> setAppointmentTime(@PathVariable("appointmentNo") Long no, @RequestBody UpdateTimeRequest time) throws Exception {
        try {
            ZonedDateTime appointmentTime = dateTimeUtil.String2DateTime(time.getTime());


            appointmentService.updateAppointmentTime(no, appointmentTime);

            return ResponseEntity.ok("Success");
        }
        catch(Exception e) {
            throw new CustomException(ErrorCode.INVALID_APPOINTMENT_TIME);
        }
    }

    @RequestMapping(value = "/{appointmentNo}/user-lists", method=RequestMethod.GET)
    public ResponseEntity<List<AppointmentUserListResponse>> getUserList(@PathVariable("appointmentNo") Long no) throws Exception {
        List<AppointmentUserListResponse> result = new ArrayList<>();

        List<User> appointmentUserList = appointmentService.getAppointmentUserList(no);

        for (Iterator<User> iter = appointmentUserList.iterator(); iter.hasNext();) {
            User tempUser= iter.next();

            String userUpdateTime = dateTimeUtil.dateTime2String(tempUser.getUpdateTime());
            AppointmentUserListResponse resp = new AppointmentUserListResponse(tempUser.getName(), tempUser.getUserId(),
                    tempUser.getLatitude(), tempUser.getLongitude(), userUpdateTime);

            result.add(resp);
        }

        return ResponseEntity.ok(result);
    }

    @ResponseBody
    @RequestMapping(value = "/places", method=RequestMethod.GET)
    @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
    public List<UserPlace> updateUserPlace(@RequestParam(required = false) Long appointmentNo,
                                           @RequestParam String latitude, @RequestParam String longitude, @RequestParam String userId)
            throws RuntimeException{

        double uLatitude = Double.parseDouble(latitude);
        double uLongitude = Double.parseDouble(longitude);
        userService.updateUser(userId, uLatitude, uLongitude);

        List<UserPlace> userPlaceList = new ArrayList<UserPlace>();

        if(appointmentNo != null) {  // apNo 존재 --> 리스트
            List<Long> userList = uaMapService.getUserNoList(appointmentNo);
            if (userList.isEmpty()) { throw new CustomException(ErrorCode.INVALID_APPOINTMENT_NO); }

            userPlaceList = getUserList2PlaceList(userList);
        }
        else { // apNo Null 일 때
            UserPlace up = new UserPlace(userId, uLatitude, uLongitude);
            userPlaceList.add(up);
        }

        return userPlaceList;
    }

    public List<UserPlace> getUserList2PlaceList(List<Long> userList) {
        List<UserPlace> resList = new ArrayList<UserPlace>();

        for(Iterator<Long> iter = userList.iterator();iter.hasNext();) {
            UserPlace userPlace = userService.getUserPlace(iter.next());

            resList.add(userPlace);
        }

        return resList;
    }
}
