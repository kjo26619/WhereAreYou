package com.escape.way.vo;

public  class UserPlace{
    private String userId;
    private float userX;
    private float userY;

    public UserPlace(String id, float x, float y) {
        this.userId = id;
        this.userX = x;
        this.userY = y;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getUserX() {
        return userX;
    }

    public void setUserX(float userX) {
        this.userX = userX;
    }

    public float getUserY() {
        return userY;
    }

    public void setUserY(float userY) {
        this.userY = userY;
    }
}