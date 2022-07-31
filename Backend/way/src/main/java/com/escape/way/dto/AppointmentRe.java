package com.escape.way.dto;

import java.util.Date;

public class AppointmentRe {
    private Long appointmentNo;
    private String name;
    private String placeName;
    private double latitude;
    private double longitude;
    private String time;

    public AppointmentRe(Long appointmentNo, String name, String placeName,
                         double latitude, double longitude, String time) {
        this.appointmentNo = appointmentNo;
        this.name = name;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;

    }

    public Long getAppointmentNo() {
        return appointmentNo;
    }

    public void setAppointmentNo(Long appointmentNo) {
        this.appointmentNo = appointmentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public double getLatitude() { return this.latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return this.longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getTime() { return this.time; }

    public void setTime(String time) { this.time = time; }
}
