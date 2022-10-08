package com.escape.way.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetLinkRequest {
    private String link;
    private String userId;

    public GetLinkRequest(String link, String userId) {
        this.link = link;
        this.userId = userId;
    }
}
