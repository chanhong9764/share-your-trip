package edu.ssafy.enjoytrip.response.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저의 정보가 존재하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}