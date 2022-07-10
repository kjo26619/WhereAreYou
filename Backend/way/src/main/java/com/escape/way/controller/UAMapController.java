package com.escape.way.controller;

import com.escape.way.model.User;
import com.escape.way.service.UAMapService;
import com.escape.way.service.UserService;
import com.escape.way.vo.UserPlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class UAMapController {
    @Autowired
    UAMapService uaMapService;
    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "/api/appointment2UserPlaceList", method=RequestMethod.GET)
    public List<UserPlace> getUserList(@RequestParam(value = "no") String no) {
        System.out.println(no);
        long appointmentNo = Long.parseLong(no);
        System.out.println(appointmentNo);
        List<Long> res = uaMapService.getUserNoList(appointmentNo);

        System.out.print("Appointment Mapping User List : ");
        for(Iterator<Long> iter = res.iterator();iter.hasNext();) {
            System.out.print(iter.next() + " ");
        }
        System.out.println(" ");

        List<UserPlace> result =getUserList2PlaceList(res);

        return result;
    }

    public List<UserPlace> getUserList2PlaceList(List<Long> userList) {
        List<UserPlace> resList = new ArrayList<UserPlace>();

        for(Iterator<Long> iter = userList.iterator();iter.hasNext();) {
            UserPlace userPlace = userService.getUserPlace(iter.next());

            resList.add(userPlace);
        }

        return resList;
    }
}
