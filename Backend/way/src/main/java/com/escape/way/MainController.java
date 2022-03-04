package com.escape.way;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @Autowired
    UserService userService;
    
    @GetMapping(value = "/user")
    public @ResponseBody User getUser(){
        Long no = 1L;
        return userService.getUser(no);
    }

    @GetMapping(value = "/userlist")
    public @ResponseBody List<User> getUserList(){
        return userService.getUserList();
    }

}
