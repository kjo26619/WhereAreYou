package com.escape.way.dto;

import com.escape.way.vo.UserPlace;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
}
