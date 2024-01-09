package edu.ssafy.enjoytrip.response.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResponseCode {
    LOGIN_USER_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),
    CREATED_USER_SUCCESS(HttpStatus.CREATED, "유저 정보를 생성했습니다."),
    MODIFY_USER_SUCCESS(HttpStatus.OK, "유저 정보를 수정했습니다."),
    DELETE_USER_SUCCESS(HttpStatus.NO_CONTENT, "회원탈퇴가 완료되었습니다."),
    READ_USER_SUCCESS(HttpStatus.OK, "유저 정보를 조회했습니다."),
    VALID_USER_ID_SUCCESS(HttpStatus.OK, "사용 가능한 ID입니다."),
    SEARCH_USER_SUCCESS(HttpStatus.OK, "유저 검색을 완료했습니다."),
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
