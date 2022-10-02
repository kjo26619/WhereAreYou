package com.escape.way.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentResponse {
    private Long appointmentNo;
    private String name;
    private String placeName;
    private double latitude;
    private double longitude;
    private String time;

    public AppointmentResponse(Long appointmentNo, String name, String placeName,
                         double latitude, double longitude, String time) {
        this.appointmentNo = appointmentNo;
        this.name = name;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;

    }

    @Override
    public String toString() {
        return "appointmentNo : " + this.appointmentNo + " | name : " + this.name + " | placeName : " + this.placeName
                + " | latitude : " + this.latitude + " | longitude : " + this.longitude + " | time : " + this.time;
    }
}
