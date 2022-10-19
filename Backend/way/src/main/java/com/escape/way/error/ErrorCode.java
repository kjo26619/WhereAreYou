package com.escape.way.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
    INVALID_APPOINTMENT_NO(HttpStatus.NOT_FOUND, "C007", "잘못된 약속 번호입니다"),
    EMPTY_APPOINTMENT_NAME(HttpStatus.BAD_REQUEST, "C008", "약속 이름이 비어있습니다"),
    EMPTY_APPOINTMENT_PLACE(HttpStatus.BAD_REQUEST, "C009", "약속 장소가 비어있습니다"),
    EMPTY_APPOINTMENT_TIME(HttpStatus.BAD_REQUEST, "C010", "약속 시간이 비어있습니다"),
    INVALID_APPOINTMENT_TIME(HttpStatus.BAD_REQUEST, "C011", "잘못된 약속 시간입니다"),
    EMPTY_USER_NAME(HttpStatus.BAD_REQUEST, "C012", "유저 이름이 비어있습니다"),
    EMPTY_PASSWORD(HttpStatus.BAD_REQUEST, "C013", "패스워드가 비어있습니다"),
    EMPTY_USER_ID(HttpStatus.BAD_REQUEST, "C014", "유저 ID가 비어있습니다"),
    INVALID_LINK(HttpStatus.BAD_REQUEST, "C015", "만료되거나 잘못된 invite Code입니다."),
    UNAUTHORIZED_APPOINTMENT(HttpStatus.UNAUTHORIZED, "C016", "Appointment Owner 권한이 없습니다."),
    NOT_MATCH_NO(HttpStatus.BAD_REQUEST, "C017", "일치하지 않는 no 값입니다.");
    ;

    private HttpStatus httpStatus;
    private String code;
    private String detail;
}