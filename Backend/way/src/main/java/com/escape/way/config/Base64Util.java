package com.escape.way.config;

import java.util.Base64;

public class Base64Util {

    //Base64
    public String encoding(String str) throws Exception {
        return Base64.getUrlEncoder().encodeToString(str.getBytes());
    }
    //Base464
    public String decoding(String str) throws Exception {
        return new String(Base64.getUrlDecoder().decode(str));
    }
}
