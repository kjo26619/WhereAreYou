package com.escape.way.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    private String userId;
    private String password;
    private String name;


    public UserRegisterRequest(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    @Override
    public String toString() {
        return "userId : " + this.userId + " | password : " + this.password + " | name : " + this.name;
    }
}
