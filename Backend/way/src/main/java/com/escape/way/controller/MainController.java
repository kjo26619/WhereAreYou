package com.escape.way.controller;

import com.escape.way.model.Appointment;
import com.escape.way.model.User;
import com.escape.way.service.AppointmentService;
import com.escape.way.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @Autowired
    UserService userService;
    AppointmentService appointmentService;

    //가입
    @PostMapping(value = "/api/join")
    public @ResponseBody String join(@RequestBody User user){

        User newUser = userService.joinUser(user);

        if (newUser != null) return "FAIL";
        else return "SUCCESS";
    }

  // 걍 유저 1명
  @GetMapping(value = "/api/user")
  public @ResponseBody User getUser() {
    Long no = 1L;
    return userService.getUser(no);
  }

  // 해당 id를 가진 유저
  @GetMapping(value = "/api/user/{id}")
  public @ResponseBody User getUserById(@PathVariable String id) {
    return userService.getUserById(id);
  }

  // 전체 유저 리스트
  @GetMapping(value = "/api/userlist")
  public @ResponseBody List<User> getUserList() {
    return userService.getUserList();
  }

  // 중복 확인
  @GetMapping(value = "/api/user/{id}/exists")
  public ResponseEntity<Boolean> checkIdDuplicate(@PathVariable String id) {
    return ResponseEntity.ok(userService.checkIdDuplicate(id));
  }

  //유저 업데이트
  @PatchMapping("/api/user/{id}")
  public Map<String, Object> updateUser(
    @PathVariable("id") String id,
    @RequestBody User user
  ) {
    Map<String, Object> response = new HashMap<>();

    if (userService.updateUser(id, user) > 0) {
      response.put("result", "SUCCESS");
    } else {
      response.put("result", "SUCCESS");
    }
    return response;
  }

  //유저 삭제
  @DeleteMapping("/api/user/{id}")
  public Map<String, Object> deleteUser(@PathVariable("id") String id) {
    Map<String, Object> response = new HashMap<>();

    if (userService.deleteUser(id) > 0) {
      response.put("result", "SUCCESS");
    } else {
      response.put("result", "FAIL");
    }
    return response;
  }

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
  @GetMapping(value = "/api/appointment/{no}")
  public @ResponseBody Appointment getAppointmentById(@PathVariable Long no) {
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
