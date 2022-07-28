package com.escape.way.service;

import com.escape.way.dto.AppointmentRe;
import com.escape.way.error.CustomException;
import com.escape.way.error.ErrorCode;
import com.escape.way.model.Appointment;
import com.escape.way.model.User;
import com.escape.way.repository.AppointmentRepository;
import com.escape.way.repository.UAMapRepository;
import com.escape.way.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UAMapService uaMapService;
    @Autowired
    private UserService userService;

    public Appointment getAppointment(Long no){
        return appointmentRepository.findById(no).orElseThrow(() ->
            new CustomException(ErrorCode.INVALID_APPOINTMENT_NO));
    }

    public List<Appointment> getAppointmentList(String userId) throws Exception{
//        - userID에 해당하는 appointmentList API -> appointmentList(Service)
        List<Appointment> appointmentList = new ArrayList<>();

        User user = userService.getUserById(userId);
        Long userNo = user.getUserNo();
        List<Long> appointmentIdList = uaMapService.getAppointmentNoListByUserNo(userNo);

        for (Iterator<Long> iter = appointmentIdList.iterator(); iter.hasNext();) {
            appointmentList.add(getAppointment(iter.next()));
        }

        return appointmentList;
    }

    public Long createAppointment(Appointment appointment){
        Appointment ap = appointmentRepository.save(appointment);
        Long appointmentNo = ap.getAppointmentNo();
        System.out.println(appointmentNo);
        return appointmentNo;
    }

    public int updateAppointment(Long id, Appointment appointment){
        Optional<Appointment> oAp = appointmentRepository.findById(id);
        if(oAp.isPresent()) {
            appointmentRepository.save(oAp.get());
            return 1;
        }
        return 0;
    }
    public int deleteAppointment(Long id) {
        Optional<Appointment> oAp = appointmentRepository.findById(id);
        if (oAp.isPresent()) {
            appointmentRepository.delete(oAp.get());
            return 1;
        }
        return 0;
    }

    public List<Appointment> getAppointmentList(){
        return appointmentRepository.findAll();
    }
}