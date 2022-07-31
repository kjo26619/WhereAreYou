package com.escape.way.controller;

import com.escape.way.config.logging.LogEntry;
import com.escape.way.error.CustomException;
import com.escape.way.error.ErrorCode;
import com.escape.way.model.User;
import com.escape.way.service.UAMapService;
import com.escape.way.service.UserService;
import com.escape.way.vo.UserPlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequestMapping("/api/appointmentUserMap/*")
@Controller
public class UAMapController {
    @Autowired
    UAMapService uaMapService;
    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "/{appointmentNo}", method=RequestMethod.GET)
    @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
    public List<UserPlace> updateUserPlace(@PathVariable("appointmentNo") String appointmentNo,
                                           @RequestParam String latitude, @RequestParam String longitude, @RequestParam String userId)
    throws RuntimeException{

        double uLatitude = Double.parseDouble(latitude);
        double uLongitude = Double.parseDouble(longitude);
        userService.updateUser(userId, uLatitude, uLongitude);

        long apNo = Long.parseLong(appointmentNo);
        List<UserPlace> userPlaceList = new ArrayList<UserPlace>();

        if(apNo >= 0) {  // apNo 존재 --> 리스트
            List<Long> userList = uaMapService.getUserNoList(apNo);
            if (userList.isEmpty()) { throw new CustomException(ErrorCode.INVALID_APPOINTMENT_NO); }

            userPlaceList = getUserList2PlaceList(userList);
        }
        else { // apNo 음수일 때
            UserPlace up = new UserPlace(userId, uLatitude, uLongitude);
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
