package com.escape.way.dto;

public class AppointmentRe {
    private Long appointmentNo;
    private String name;
    private String placeName;
    private float placeX;
    private float placeY;

    public AppointmentRe(Long appointmentNo, String name, String placeName, float placeX, float placeY) {
        this.appointmentNo = appointmentNo;
        this.name = name;
        this.placeName = placeName;
        this.placeX = placeX;
        this.placeY = placeY;
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

    public float getPlaceX() {
        return placeX;
    }

    public void setPlaceX(float placeX) {
        this.placeX = placeX;
    }

    public float getPlaceY() {
        return placeY;
    }

    public void setPlaceY(float placeY) {
        this.placeY = placeY;
    }
}
