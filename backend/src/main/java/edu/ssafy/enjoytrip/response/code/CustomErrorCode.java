package edu.ssafy.enjoytrip.response.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저의 정보가 존재하지 않습니다."),
    INVALID_USER_INFO(HttpStatus.BAD_REQUEST, "유저의 id 혹은 email이 중복됩니다."),

    PASSWORD_NOT_CREATED(HttpStatus.INTERNAL_SERVER_ERROR, "패스워드를 생성하지 못하였습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String message;

}