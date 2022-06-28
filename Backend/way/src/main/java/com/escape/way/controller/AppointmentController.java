package com.escape.way.controller;

import com.escape.way.error.ErrorCode;
import com.escape.way.model.Appointment;
import com.escape.way.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    //약속 생성
    @PostMapping(value = "/api/appointment")
    public @ResponseBody ResponseEntity<String> join(@RequestBody Appointment appointment) {
        Appointment newAp = appointmentService.createAppointment(appointment);
        return ResponseEntity.ok("success");
    }

    // 해당 id를 가진 약속
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

    //약속 업데이트
    @PatchMapping("/api/appointment/{no}")
    public ResponseEntity<String> updateAppointment(@PathVariable("no") Long no, @RequestBody Appointment appointment) {
        if (appointmentService.updateAppointment(no, appointment) > 0) return ResponseEntity.ok("Success");
        else return ResponseEntity.ok("Fail");
    }

    //약속 삭제
    @DeleteMapping("/api/appointment/{no}")
    public ResponseEntity<String> deleteAppointment(@PathVariable("no") Long no) {

        if (appointmentService.deleteAppointment(no) > 0) return ResponseEntity.ok("Success");
        else return ResponseEntity.ok("Fail");
    }
}
