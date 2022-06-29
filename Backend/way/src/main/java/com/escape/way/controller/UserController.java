package com.escape.way.controller;

import com.escape.way.model.User;
import com.escape.way.service.UserService;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class UserController {

  @Autowired
  UserService userService;

  //가입
  @PostMapping(value = "/api/join")
  public ResponseEntity<String> join(@RequestParam String userId, @RequestParam String password, @RequestParam String name){
    User user = new User();
    user.setUserId(userId);
    user.setName(name);
    user.setPw(password);

    user.setUserX(0);
    user.setUserY(0);
    user.setAuth("ROLE_USER");
    user.setKakaoId(1L);

    userService.joinUser(user);
    return ResponseEntity.ok("Success");
  }

  @GetMapping(value = "/login")
  public String loginPage() {
    return "login";
  }

  @GetMapping(value = "/api/logout")
  public String logoutPage(HttpServletRequest req, HttpServletResponse res) {
    new SecurityContextLogoutHandler().logout(req, res, SecurityContextHolder.getContext().getAuthentication());
    return "redirect:/login";
  }

  // 해당 id를 가진 유저
  @GetMapping(value = "/api/user")
  public ResponseEntity<User> getUserById(@RequestParam String id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }

  // 전체 유저 리스트
  @GetMapping(value = "/api/userList")
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
