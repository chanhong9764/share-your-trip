package edu.ssafy.enjoytrip.response.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomResponseCode implements ResponseCode {

    /*
        USER
     */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저의 정보가 존재하지 않습니다."),
    INVALID_USER_INFO(HttpStatus.BAD_REQUEST, "유저의 정보가 올바르지 않습니다."),
    PASSWORD_NOT_CREATED(HttpStatus.INTERNAL_SERVER_ERROR, "패스워드를 생성하지 못하였습니다."),
    INVALID_USER_ID(HttpStatus.BAD_REQUEST, "사용할 수 없는 ID입니다."),
    EMAIL_NOT_CREATED(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 인증번호 생성에 실패했습니다."),

    /*
        TRIP
     */
    TRIP_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "여행지 리스트가 존재하지 않습니다."),

    /*
        IMAGE
     */
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "이미지 정보가 존재하지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

}