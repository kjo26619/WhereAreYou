package com.escape.way.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAppointmentRequest {
    private String userId;
    private String name;
    private String placeName;
    private double latitude;
    private double longitude;
    private String time;

    public CreateAppointmentRequest(String userId, String name, String placeName, double latitude, double longitude, String time) {
        this.userId = userId;
        this.name = name;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    @Override
    public String toString() {
        return "name : " + this.name + " | userId : " + this.userId + " | latitude : " + this.latitude +
                " | longitude : " + this.longitude + " | time : " + this.time;
    }
}
