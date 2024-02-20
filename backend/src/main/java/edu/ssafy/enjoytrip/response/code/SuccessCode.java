package edu.ssafy.enjoytrip.response.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResponseCode {
    /*
        USER
     */
    LOGIN_USER_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),
    CREATED_USER_SUCCESS(HttpStatus.CREATED, "유저 정보를 생성했습니다."),
    MODIFY_USER_SUCCESS(HttpStatus.OK, "유저 정보를 수정했습니다."),
    DELETE_USER_SUCCESS(HttpStatus.NO_CONTENT, "회원탈퇴가 완료되었습니다."),
    READ_USER_SUCCESS(HttpStatus.OK, "유저 정보를 조회했습니다."),
    VALID_USER_ID_SUCCESS(HttpStatus.OK, "사용 가능한 ID입니다."),
    SEARCH_USER_SUCCESS(HttpStatus.OK, "유저 검색을 완료했습니다."),
    FIND_USER_ID_SUCCESS(HttpStatus.OK, "유저 ID를 찾았습니다."),
    SEND_USER_EMAIL_SUCCESS(HttpStatus.OK, "인증번호를 발송했습니다."),
    CHANGE_PASSWORD_USER_SUCCESS(HttpStatus.OK, "비밀번호를 변경했습니다."),
    CHANGE_PROFILE_USER_SUCCESS(HttpStatus.OK, "프로필을 변경했습니다."),
    CREATED_TOKEN_SUCCESS(HttpStatus.OK, "토큰 재생성에 성공했습니다."),
    /*
        TRIP
     */
    LOAD_LIST_TRIP_SUCCESS(HttpStatus.OK, "여행지 리스르 정보를 조회했습니다."),

    /*
        IMAGE
     */
    LOAD_IMAGE_SUCCESS(HttpStatus.OK, "이미지를 조회했습니다."),
    
    /*
        COMMENT
     */
    LOAD_COMMENT_SUCCESS(HttpStatus.OK, "댓글을 조회했습니다."),
    CREATED_COMMENT_SUCCESS(HttpStatus.OK, "댓글을 생성했습니다."),
    DELETE_COMMENT_SUCCESS(HttpStatus.NO_CONTENT, "댓글을 삭제했습니다."),

    /*
        CHATTING
     */
    LOAD_ROOM_LIST_SUCCESS(HttpStatus.OK, "채팅방 리스트를 조회했습니다."),
    LOAD_CHATTING_LIST_SUCCESS(HttpStatus.OK, "채팅 리스트를 조회했습니다."),
    DELETE_CHATTING_ROOM_SUCCESS(HttpStatus.NO_CONTENT, "채팅방을 나갔습니다."),
    DELETE_ROOM_INVITATION_SUCCESS(HttpStatus.NO_CONTENT, "초대장을 삭제했습니다."),
    ACCEPTED_ROOM_INVITATION_SUCCESS(HttpStatus.NO_CONTENT, "초대장을 수락했습니다."),
    LOAD_ROOM_INVITATION_SUCCESS(HttpStatus.NO_CONTENT, "초대장을 불러왔습니다."),

    /*
        BOARD
     */
    LOAD_LIST_BOARD_SUCCESS(HttpStatus.OK, "게시글 리스트를 조회했습니다."),
    LOAD_POST_BOARD_SUCCESS(HttpStatus.OK, "게시글을 조회했습니다."),
    CREATED_POST_BOARD_SUCCESS(HttpStatus.CREATED, "게시글을 등록하였습니다."),
    MODIFY_POST_BOARD_SUCCESS(HttpStatus.CREATED, "게시글을 수정하였습니다."),
    DELETE_POST_BOARD_SUCCESS(HttpStatus.CREATED, "게시글을 삭제하였습니다."),
    CREATED_LIKE_BOARD_SUCCESS(HttpStatus.CREATED, "추천을 생성하였습니다."),
    DELETE_LIKE_BOARD_SUCCESS(HttpStatus.CREATED, "추천을 제거하였습니다."),
    LOAD_HOT_HASHTAG_BOARD_SUCCESS(HttpStatus.OK, "인기 해시태그를 조회했습니다."),
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
