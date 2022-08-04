package com.escape.way.dto;

public class AppointmentRe {
    private Long appointmentNo;
    private String name;
    private String placeName;
    private double latitude;
    private double longitude;

    public AppointmentRe(Long appointmentNo, String name, String placeName, double latitude, double longitude) {
        this.appointmentNo = appointmentNo;
        this.name = name;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
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
}
