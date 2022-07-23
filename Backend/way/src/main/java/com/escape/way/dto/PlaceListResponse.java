package com.escape.way.dto;

import com.escape.way.vo.UserPlace;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class PlaceListResponse implements Serializable {

    private String reqUserId;

    private UserPlace[] userPlaceList = new UserPlace[10];

    @Override
    public String toString() {
        String result = "reqUserId : " + reqUserId;

        for (int i = 0; i < 10; i++) {
            if (userPlaceList[i].getUserId() == null) break;
            result += userPlaceList[i].toString() + "\r\n";
        }

        return result;
    }
}
