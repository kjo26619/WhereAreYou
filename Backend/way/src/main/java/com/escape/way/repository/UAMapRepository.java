package com.escape.way.repository;

import com.escape.way.model.UAMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

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

    @Query("SELECT u.user.userNo FROM UAMap u WHERE u.owner = true AND u.appointment.appointmentNo = :appointment_no")
    Long findOwnerNoByAppointmentNo(@Param("appointment_no") Long appointmentNo);

    @Query("SELECT u.appointment.appointmentNo FROM UAMap u WHERE u.owner = true AND u.user.userNo = :user_no")
    List<Long> findOwnerAppointmentByUserNo(@Param("user_no") Long userNo);

    @Transactional
    @Modifying
    @Query("DELETE FROM UAMap u WHERE u.user.userNo = :user_no")
    int deleteUAMapByUserNo(@Param("user_no") Long userNo);

    @Transactional
    @Modifying
    @Query("DELETE FROM UAMap u WHERE u.appointment.appointmentNo = :appointment_no")
    int deleteUAMapByAppointmentNo(@Param("appointment_no") Long appointmentNo);
}
