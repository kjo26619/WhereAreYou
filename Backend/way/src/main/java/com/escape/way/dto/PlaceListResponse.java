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

}
