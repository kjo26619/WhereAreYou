package com.escape.way.controller;

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

import java.util.List;

@Controller
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;
    @Autowired
    UAMapService uaMapService;
    @Autowired
    UserService userService;

    //약속 생성
    @PostMapping(value = "/api/appointment")
    public @ResponseBody ResponseEntity<String> createAppointment(@RequestParam String userId, @RequestBody Appointment appointment) {
        System.out.println(userId + " , " + appointment.getPlaceName());
        
        Long appointmentNo = appointmentService.createAppointment(appointment);
        User u = userService.getUserById(userId);
        uaMapService.setUAMap(appointmentNo, u.getUserNo());
        return ResponseEntity.ok("success");
    }

    // userid를 가진 약속 리스트
    @GetMapping(value = "/api/appointmentList")
    public @ResponseBody
    ResponseEntity<List<Appointment>> getAppointmentByUserId(@RequestParam String userId) {
        List<Appointment> appointments = appointmentService.getAppointmentList(userId);
        if(appointments == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(appointments);
    }

    // userID에 해당되는 약속 List
    @GetMapping(value = "/api/appointment")
    public  ResponseEntity<Appointment> getAppointmentById(@RequestParam Long no) {
        return ResponseEntity.ok(appointmentService.getAppointment(no));
    }

    //약속 삭제
    @DeleteMapping("/api/appointment/{no}")
    public ResponseEntity<String> deleteAppointment(@PathVariable("no") Long no) {

        if (appointmentService.deleteAppointment(no) > 0) return ResponseEntity.ok("Success");
        else return ResponseEntity.ok("Fail");
    }
}
