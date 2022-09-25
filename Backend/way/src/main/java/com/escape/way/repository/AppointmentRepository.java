package com.escape.way.repository;

import com.escape.way.model.Appointment;
import com.escape.way.model.User;
import com.escape.way.vo.UserPlace;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

    @Transactional
    @Modifying
    @Query("update Appointment a set a.time = :time where a.appointmentNo = :no ")
    int setTime(@Param("no") Long no, @Param("time") ZonedDateTime time);

    @Transactional
    @Modifying
    @Query("update Appointment a set a.updateTime = :time where a.appointmentNo = :no ")
    int setUpdateTime(@Param("no") Long no, @Param("time") ZonedDateTime time);
}
