package com.escape.way.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTimeRequest {
    String time;

    public UpdateTimeRequest(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "time : " + this.time;
    }
}
