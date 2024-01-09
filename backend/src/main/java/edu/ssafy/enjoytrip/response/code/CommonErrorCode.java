package edu.ssafy.enjoytrip.response.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    // 400 BAD_REQUEST : 유효하지 않은 파라미터
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "유효하지 않은 파라미터입니다."),
    // 401 UNAUTHORIZED : 인증되지 않은 사용자
    UNAUTHORIZED_REQUEST(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    // 403 FORBIDDEN : 권한이 없는 사용자
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "권한이 존재하지 않은 사용자입니다."),
    // 404 NOT_FOUND : 리소스가 존재하지 않음
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "리소스가 존재하지 않습니다."),
    // 405 METHOD_NOT_ALLOWED : 허용되지 않은 Request Method 호출
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 METHOD 요청입니다."),
    // 500 INTERNAL_SERVER_ERROR : 내부 서버 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 오류가 발생했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}