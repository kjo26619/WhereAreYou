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
    @RequestMapping(value = "/api/appointment2UserPlaceList/{appointmentNo}", method=RequestMethod.GET)
    public List<UserPlace> updateUserPlace(@PathVariable("appointmentNo") String appointmentNo, @RequestParam String userX, @RequestParam String userY, @RequestParam String userId) {
        float uX = Float.parseFloat(userX);
        float uY = Float.parseFloat(userY);
        userService.updateUser(userId, uX, uY);

        long apNo = Long.parseLong(appointmentNo);
        List<UserPlace> userPlaceList = new ArrayList<UserPlace>();

        if(apNo >= 0) {  // apNo 존재 --> 리스트
            List<Long> userList = uaMapService.getUserNoList(apNo);
            userPlaceList = getUserList2PlaceList(userList);
        }
        else { // apNo 음수일 때
            UserPlace up = new UserPlace(userId, uX, uY);
            userPlaceList.add(up);
        }


        return userPlaceList;
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
