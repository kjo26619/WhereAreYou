package com.escape.way.service;

import com.escape.way.model.UAMap;
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

    public List<Long> getUserNoList(Long appointmentNo){
        // Appointment No -> User ID

        List<Long> userNoList = uaMapRepository.findAllByAppointmentId(appointmentNo);

        return userNoList;

    }
}