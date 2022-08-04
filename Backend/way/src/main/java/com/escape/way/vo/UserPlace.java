package com.escape.way.vo;

public  class UserPlace{
    private String userId;
    private double latitude;
    private double longitude;

    public UserPlace(String id, double latitude, double longitude) {
        this.userId = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "userId : " + this.userId + " | latitude : " + this.latitude + " | longitude : " + this.longitude;
    }
}