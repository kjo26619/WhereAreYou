package com.escape.way.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthRequest {
    private String userId;
    private String password;

    public UserAuthRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    @Override
    public String toString() {
        return "userId : " + this.userId + " | password : " + this.password;
    }
}
