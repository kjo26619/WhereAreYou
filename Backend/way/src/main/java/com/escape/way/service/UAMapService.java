package com.escape.way.service;

import com.escape.way.model.Appointment;
import com.escape.way.model.UAMap;
import com.escape.way.model.User;
import com.escape.way.repository.UAMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UAMapService {
    
    @Autowired
    private UAMapRepository uaMapRepository;

    public void setUAMap(Long appointmentNo, Long userNo, boolean owner){
        User u = new User();
        u.setUserNo(userNo);
        Appointment a = new Appointment();
        a.setAppointmentNo(appointmentNo);
        UAMap uamap = new UAMap(u, a, owner);
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

    public List<Long> getAppointmentNoListByUserNo(Long no) {
        return uaMapRepository.findAppointmentListByUserNo(no);
    }

    public List<Long> findUserListByAppointmentNo(Long no) { return uaMapRepository.findUserListByAppointmentNo(no);   }

    public List<Long> findOwnerAppointmentNoByUserNo(Long userNo) { return uaMapRepository.findOwnerAppointmentByUserNo(userNo); }

    public int deleteUAMapByAppointmentNo(Long appointmentNo) throws Exception{
        int deletedCnt = uaMapRepository.deleteUAMapByAppointmentNo(appointmentNo);

        return deletedCnt;
    }

    public int deleteUAMapByUserNo(Long userNo) throws Exception{
        int deletedCnt = uaMapRepository.deleteUAMapByUserNo(userNo);

        return deletedCnt;
    }
}