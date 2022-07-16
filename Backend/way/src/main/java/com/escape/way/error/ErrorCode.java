package com.escape.way.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // COMMON
    INVALID_CODE(HttpStatus.BAD_REQUEST, "C001", "Invalid Code"),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "C002","잘못된 User ID 나 Password가 입력되었습니다"),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,  "C003", "해당 User 정보를 찾을 수 없습니다"),
    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "C004","Access Token이 만료되었거나 잘못된 값입니다"),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "C005","Refresh Token이 만료되었거나 잘못된 값입니다"),

    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "C006","중복된 데이터 값입니다"),
    INVALID_APPOINTMENT_NO(HttpStatus.NOT_FOUND, "C007", "잘못된 약속 정보입니다")
    ;

    private HttpStatus httpStatus;
    private String code;
    private String detail;
}