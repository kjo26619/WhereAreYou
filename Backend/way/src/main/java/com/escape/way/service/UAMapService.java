package com.escape.way.service;

import com.escape.way.config.logging.LogEntry;
import com.escape.way.error.CustomException;
import com.escape.way.error.ErrorCode;
import com.escape.way.model.Appointment;
import com.escape.way.model.UAMap;
import com.escape.way.model.User;
import com.escape.way.repository.AppointmentRepository;
import com.escape.way.repository.UAMapRepository;
import com.escape.way.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
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

        return uaMapRepository.findAllByAppointmentId(appointmentNo);
    }

    public boolean existUAMap(Long appointmentNo, Long userNo) {
        Long res = uaMapRepository.existsUAMap(appointmentNo , userNo);
        if(res == null) return true;
        else return false;
    }
}