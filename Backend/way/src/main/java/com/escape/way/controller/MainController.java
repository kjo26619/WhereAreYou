package com.escape.way.controller;

import java.util.List;

import com.escape.way.repository.UserRepository;
import com.escape.way.service.UserService;
import com.escape.way.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/join")
    public String join(@RequestBody User user){
        User newUser = userService.joinUser(user);
        if (newUser != null) return "가입 실패";
        return "가입 완료";
    }

    @GetMapping(value = "/user")
    public @ResponseBody
    User getUser(){
        Long no = 1L;
        return userService.getUser(no);
    }

    @GetMapping(value = "/userlist")
    public @ResponseBody List<User> getUserList(){
        return userService.getUserList();
    }

}
