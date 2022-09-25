package com.escape.way.repository;

import com.escape.way.model.UAMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UAMapRepository extends JpaRepository<UAMap, Long> {

    @Query("SELECT u.user.userNo FROM UAMap u WHERE u.appointment.appointmentNo = :appointment_no")
    List<Long> findAllByAppointmentId(@Param("appointment_no") Long appointmentNo);

    @Query("SELECT u.user.userNo FROM UAMap u WHERE u.appointment.appointmentNo = :appointment_no AND u.user.userNo = :user_no")
    Long existsUAMap(@Param("appointment_no") Long appointmentNo, @Param("user_no") Long userNo);

    @Query("SELECT u.appointment.appointmentNo FROM UAMap u WHERE u.user.userNo = :user_no")
    List<Long> findAppointmentListByUserNo(@Param("user_no") Long userNo);

    @Query("SELECT u.user.userNo FROM UAMap u WHERE u.appointment.appointmentNo = :appointment_no")
    List<Long> findUserListByAppointmentNo(@Param("appointment_no") Long appointmentNo);
}
