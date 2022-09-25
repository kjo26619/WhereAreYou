package com.escape.way.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class CreateAppointmentRequest {
    private String name;
    private String placeName;
    private double latitude;
    private double longitude;
    private String time;

    public CreateAppointmentRequest(String name, String placeName, double latitude, double longitude, String time) {
        this.name = name;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }
}
