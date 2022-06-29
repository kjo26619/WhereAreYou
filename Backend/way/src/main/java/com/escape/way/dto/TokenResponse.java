package com.escape.way.dto;

import java.io.Serializable;

public class TokenResponse implements Serializable {

    private String name;
    private String token;

    public TokenResponse(String name, String token) {
        this.name = name;
        this.token = token;
    }

    public String getName() {
        return this.name;
    }

    public String getToken() {
        return this.token;
    }
}