package com.escape.way;

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

}
