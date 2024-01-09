package edu.ssafy.enjoytrip.response.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ErrorCode {
    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),
    CREATED_USER(HttpStatus.CREATED, "유저 정보를 생성했습니다.")
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
