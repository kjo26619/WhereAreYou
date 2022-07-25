package com.escape.way.dto;

import java.io.Serializable;

public class TokenResponse implements Serializable {

    private String name;
    private String accessToken;
    private String refreshToken;

    public TokenResponse(String name, String accessToken, String refreshToken) {
        this.name = name;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getName() {
        return this.name;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    @Override
    public String toString() {
        return "name : " + this.name + " | AccessToken : " + this.accessToken + " | RefreshToken : " + this.refreshToken;
    }
}