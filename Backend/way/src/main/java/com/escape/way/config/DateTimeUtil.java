package com.escape.way.config;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateTimeUtil {

    public String dateTime2String(ZonedDateTime time) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String timeString = time.format(formatter);

        return timeString;
    }

    public ZonedDateTime String2DateTime(String timeString) throws Exception{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime localAppointmentTime = LocalDateTime.parse(timeString, formatter);
        ZonedDateTime time = localAppointmentTime.atZone(ZoneId.of("UTC"));

        return time;
    }

}
