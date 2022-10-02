package com.escape.way.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentUserListResponse {
    private String name;
    private String userId;
    private double latitude;
    private double longitude;
    private String updateTime;

    public AppointmentUserListResponse(String name, String userId, double latitude, double longitude, String updateTime) {
        this.name = name;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "name : " + this.name + " | userId : " + this.userId + " | latitude : " + this.latitude +
                " | longitude : " + this.longitude + " | updateTime : " + this.updateTime;
    }
}
