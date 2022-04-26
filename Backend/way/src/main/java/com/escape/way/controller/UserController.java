package com.escape.way.controller;

import com.escape.way.model.User;
import com.escape.way.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

  @Autowired
  UserService userService;

  //가입
  @PostMapping(value = "/api/join")
  public @ResponseBody String join(@RequestBody User user){
    User newUser = userService.joinUser(user);
    if (newUser != null) return "FAIL";
    else return "SUCCESS";
  }

  // 해당 id를 가진 유저
  @GetMapping(value = "/api/user")
  public ResponseEntity<User> getUserById(@RequestParam String id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }

  // 전체 유저 리스트
  @GetMapping(value = "/api/userlist")
  public ResponseEntity<List<User>> getUserList() {
    return ResponseEntity.ok(userService.getUserList());
  }

  // 중복 확인
  @GetMapping(value = "/api/user/exists")
  public ResponseEntity<Boolean> checkIdDuplicate(@RequestParam String id) {
    return ResponseEntity.ok(userService.checkIdDuplicate(id));
  }

  //유저 업데이트
  @PatchMapping("/api/user/{id}")
  public ResponseEntity<String> updateUser(@PathVariable("id") String id, @RequestBody User user) {
    if (userService.updateUser(id, user) > 0) return ResponseEntity.ok("Success");
    else return ResponseEntity.ok("Fail");
  }

  //유저 삭제
  @DeleteMapping("/api/user/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {
    if (userService.deleteUser(id) > 0) return ResponseEntity.ok("Success");
    else return ResponseEntity.ok("Fail");
  }
}
