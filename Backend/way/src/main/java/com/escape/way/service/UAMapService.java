package com.escape.way.service;

import com.escape.way.model.Appointment;
import com.escape.way.model.UAMap;
import com.escape.way.model.User;
import com.escape.way.repository.AppointmentRepository;
import com.escape.way.repository.UAMapRepository;
import com.escape.way.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UAMapService {
    
    @Autowired
    private UAMapRepository uaMapRepository;

    public void setUAMap(Long appointmentNo, Long userNo){
        User u = new User();
        u.setUserNo(userNo);
        Appointment a = new Appointment();
        a.setAppointmentNo(appointmentNo);
        UAMap uamap = new UAMap(u, a);
        uaMapRepository.save(uamap);
    }

    public List<Long> getUserNoList(Long appointmentNo){
        // Appointment No -> User ID

        List<Long> userNoList = uaMapRepository.findAllByAppointmentId(appointmentNo);

        return userNoList;

    }
}