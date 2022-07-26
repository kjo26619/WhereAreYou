package com.escape.way.controller;

import com.escape.way.config.logging.LogEntry;
import com.escape.way.dto.AppointmentRe;
import com.escape.way.error.CustomException;
import com.escape.way.error.ErrorCode;
import com.escape.way.model.Appointment;
import com.escape.way.model.User;
import com.escape.way.service.AppointmentService;
import com.escape.way.service.UAMapService;
import com.escape.way.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
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
    public @ResponseBody ResponseEntity<String> createAppointment(@RequestParam String userId, @RequestBody Appointment appointment) throws Exception {
        Long appointmentNo = appointmentService.createAppointment(appointment);
        User u = userService.getUserById(userId);
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
        AppointmentRe appointment = new AppointmentRe(a.getAppointmentNo(), a.getName(), a.getPlaceName(), a.getLatitude(), a.getLongitude());

        return appointment;
    }

    // userid를 가진 약속 리스트
    // 구현 예정
    @RequestMapping(value = "/getAppointmentList", method=RequestMethod.GET)
    @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
    public @ResponseBody
    ResponseEntity<List<Appointment>> getAppointmentByUserId(@RequestParam String userId) throws Exception {
        try {
            List<Appointment> appointments = appointmentService.getAppointmentList(userId);
            if(appointments == null) return ResponseEntity.badRequest().build();

            return ResponseEntity.ok(appointments);
        }
        catch(Exception e) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }

    // appointment ID에 해당되는 약속
    // 구현 예정
    @RequestMapping(value = "/getAppointment", method=RequestMethod.GET)
    public  ResponseEntity<Appointment> getAppointmentById(@RequestParam Long no) {
        return ResponseEntity.ok(appointmentService.getAppointment(no));
    }

    // 약속 삭제
    @RequestMapping(value="/{no}", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteAppointment(@PathVariable("no") Long no) {

        if (appointmentService.deleteAppointment(no) > 0) return ResponseEntity.ok("Success");
        else return ResponseEntity.ok("Fail");
    }
}
