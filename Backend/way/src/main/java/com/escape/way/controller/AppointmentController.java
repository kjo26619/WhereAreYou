package com.escape.way.controller;

import com.escape.way.config.logging.LogEntry;
import com.escape.way.dto.AppointmentRe;
import com.escape.way.dto.AppointmentUserListResponse;
import com.escape.way.dto.CreateAppointmentRequest;
import com.escape.way.error.CustomException;
import com.escape.way.error.ErrorCode;
import com.escape.way.model.Appointment;
import com.escape.way.model.UAMap;
import com.escape.way.model.User;
import com.escape.way.service.AppointmentService;
import com.escape.way.service.UAMapService;
import com.escape.way.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RequestMapping(value = "/api/appointment/*")
@Controller
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;
    @Autowired
    UAMapService uaMapService;
    @Autowired
    UserService userService;

    //약속 생성
    @PostMapping(value = "/create")
    @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
    public @ResponseBody ResponseEntity<String> createAppointment(
            @RequestParam String userId, @RequestBody CreateAppointmentRequest body) throws Exception {
        Appointment appointment = new Appointment();
        User u = userService.getUserById(userId);

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
        System.out.println(curTime);

        appointment.setUpdateTime(curTime);
        appointment.setName(body.getName());
        appointment.setPlaceName(body.getPlaceName());
        appointment.setLatitude(body.getLatitude());
        appointment.setLongitude(body.getLongitude());

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime localAppointmentTime = LocalDateTime.parse(body.getTime(), formatter);
            ZonedDateTime appointmentTime = localAppointmentTime.atZone(ZoneId.of("UTC"));
            System.out.println(appointmentTime);
            appointment.setTime(appointmentTime);
        }
        catch(Exception e) {
            throw new CustomException(ErrorCode.INVALID_APPOINTMENT_TIME);
        }

        Long appointmentNo = appointmentService.createAppointment(appointment);

        uaMapService.setUAMap(appointmentNo, u.getUserNo());

        return ResponseEntity.ok("success");
    }

    @ResponseBody
    @RequestMapping(value = "/addUser", method=RequestMethod.GET)
    @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
    public AppointmentRe addUser(@RequestParam Long no, @RequestParam String userId) throws Exception {
        //1. user id로 user no 검색
        User u = userService.getUserById(userId);

        //2. uaMap에 같은 항목 있으면 안녕
        if (uaMapService.existUAMap(no, u.getUserNo())) {

            //3. uaMap에 insert
            uaMapService.setUAMap(no, u.getUserNo());
        }else throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        //4. no 해당하는 약속정보 가져와서 return
        Appointment a = appointmentService.getAppointment(no);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String appointmentTime = a.getTime().format(formatter);
        AppointmentRe appointment =
                new AppointmentRe(a.getAppointmentNo(), a.getName(), a.getPlaceName(),
                        a.getLatitude(), a.getLongitude(), appointmentTime);

        return appointment;
    }

    // userid를 가진 약속 리스트
    @RequestMapping(value = "/getList", method=RequestMethod.GET)
    @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
    public @ResponseBody
    ResponseEntity<List<AppointmentRe>> getAppointmentByUserId(@RequestParam String userId) throws Exception {

        List<Appointment> appointments = appointmentService.getAppointmentList(userId);
        List<AppointmentRe> listResponse = new ArrayList<>();

        for (Iterator<Appointment> iter = appointments.iterator(); iter.hasNext();) {
            Appointment temp = iter.next();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String appointmentTime = temp.getTime().format(formatter);

            AppointmentRe appointmentRe =
                    new AppointmentRe(temp.getAppointmentNo(), temp.getName(),
                            temp.getPlaceName(), temp.getLatitude(), temp.getLongitude(), appointmentTime);
            listResponse.add(appointmentRe);
        }

        if(appointments == null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(listResponse);
    }

    // appointment ID에 해당되는 약속
    @RequestMapping(value = "/getAppointment", method=RequestMethod.GET)
    public  ResponseEntity<AppointmentRe> getAppointmentById(@RequestParam Long no) throws Exception {
        Appointment appointment = appointmentService.getAppointment(no);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String appointmentTime = appointment.getTime().format(formatter);

        AppointmentRe resp = new AppointmentRe(appointment.getAppointmentNo(), appointment.getName(),
                appointment.getPlaceName(), appointment.getLatitude(), appointment.getLongitude(), appointmentTime);

        return ResponseEntity.ok(resp);
    }

    // 약속 삭제
    @RequestMapping(value="/{no}", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteAppointment(@PathVariable("no") Long no) throws Exception {
        appointmentService.deleteAppointment(no);

        return ResponseEntity.ok("Success");
    }

    @RequestMapping(value = "/setTime", method=RequestMethod.GET)
    public  ResponseEntity<String> setAppointmentTime(@RequestParam Long no, @RequestBody String time) throws Exception {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime localAppointmentTime = LocalDateTime.parse(time, formatter);
            ZonedDateTime appointmentTime = localAppointmentTime.atZone(ZoneId.of("UTC"));
            System.out.println(appointmentTime);

            appointmentService.updateAppointmentTime(no, appointmentTime);

            return ResponseEntity.ok("Success");
        }
        catch(Exception e) {
            throw new CustomException(ErrorCode.INVALID_APPOINTMENT_TIME);
        }
    }

    @RequestMapping(value = "/getUserList", method=RequestMethod.GET)
    public ResponseEntity<List<AppointmentUserListResponse>> getUserList(@RequestParam Long no) throws Exception {
        List<AppointmentUserListResponse> result = new ArrayList<>();

        List<User> appointmentUserList = appointmentService.getAppointmentUserList(no);

        for (Iterator<User> iter = appointmentUserList.iterator(); iter.hasNext();) {
            User tempUser= iter.next();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String userUpdateTime = tempUser.getUpdateTime().format(formatter);
            AppointmentUserListResponse resp = new AppointmentUserListResponse(tempUser.getName(), tempUser.getUserId(),
                    tempUser.getLatitude(), tempUser.getLongitude(), userUpdateTime);

            result.add(resp);
        }

        return ResponseEntity.ok(result);
    }
}
