package com.escape.way.service;

import com.escape.way.model.Appointment;
import com.escape.way.repository.AppointmentRepository;
import com.escape.way.repository.UAMapRepository;
import com.escape.way.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    private UserRepository userRepository;
    private UAMapRepository uaMapRepository;

    public Appointment getAppointment(Long no){
        return appointmentRepository.findById(no).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found appointment"));
    }

    public List<Appointment> getAppointmentList(String userId){
//        - userID에 해당하는 appointmentList API -> appointmentList(Service)
        List<Appointment> appointmentList = null;
        if(!userRepository.existsByUserId(userId)) return appointmentList;
        else {
            Long userNo = userRepository.findByUserId(userId).get().getUserNo();
//            List<Long> appointmentIds = uaMapRepository.findByUserNo(userNo);
//            appointmentList = appointmentRepository.findAllByAppointmentNoMatches(appointmentIds);

        }
        return appointmentList;
    }

    public Appointment createAppointment(Appointment appointment){
        Long no = appointment.getAppointmentNo();
        return appointmentRepository.save(appointment);
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