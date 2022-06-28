package com.escape.way.controller;

import com.escape.way.model.User;
import com.escape.way.service.UAMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@Controller
public class UAMapController {
    @Autowired
    UAMapService uaMapService;

    @ResponseBody
    @RequestMapping(value = "/api/getUserList", method=RequestMethod.GET)
    public List<Long> getUserList(@RequestParam(value = "no") String no) {
        System.out.println(no);
        long appointmentNo = Long.parseLong(no);
        System.out.println(appointmentNo);
        List<Long> res = uaMapService.getUserNoList(appointmentNo);

        System.out.print("Appointment Mapping User List : ");
        for(Iterator<Long> iter = res.iterator();iter.hasNext();) {
            System.out.print(iter.next() + " ");
        }
        System.out.println(" ");

        return res;
    }
}
