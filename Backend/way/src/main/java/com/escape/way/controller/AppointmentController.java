package com.escape.way.controller;

import com.escape.way.model.Appointment;
import com.escape.way.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    //약속 생성
    @PostMapping(value = "/api/appointment")
    public Map<String, Object> join(@RequestBody Appointment appointment) {
        Map<String, Object> response = new HashMap<>();

        Appointment newAp = appointmentService.createAppointment(appointment);

        if (newAp != null) response.put("result", "SUCCESS");
        else response.put("result","SUCCESS" );

        return response;
    }

    // 해당 id를 가진 약속
    @GetMapping(value = "/api/appointment")
    public @ResponseBody Appointment getAppointmentById(@RequestParam Long no) {
        return appointmentService.getAppointment(no);
    }

    //약속 업데이트
    @PatchMapping("/api/appointment/{no}")
    public Map<String, Object> updateAppointment(@PathVariable("no") Long no, @RequestBody Appointment appointment) {
        Map<String, Object> response = new HashMap<>();

        if (appointmentService.updateAppointment(no, appointment) > 0) {
            response.put("result", "SUCCESS");
        } else {
            response.put("result", "SUCCESS");
        }
        return response;
    }

    //약속 삭제
    @DeleteMapping("/api/appointment/{no}")
    public Map<String, Object> deleteAppointment(@PathVariable("no") Long no) {
        Map<String, Object> response = new HashMap<>();

        if (appointmentService.deleteAppointment(no) > 0) {
            response.put("result", "SUCCESS");
        } else {
            response.put("result", "FAIL");
        }
        return response;
    }
}
