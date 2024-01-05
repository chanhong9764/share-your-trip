package edu.ssafy.enjoytrip.service.user;

import java.util.List;
import java.util.Optional;

import edu.ssafy.enjoytrip.dto.board.BoardImagesDto;
import edu.ssafy.enjoytrip.dto.user.UserDto;

public interface UserService {

	// 유저 ID를 이용하여 사용자 검색
	Optional<UserDto> findById(String userId) throws Exception;
	int joinMember(UserDto memberDto) throws Exception;
	UserDto loginMember(String userId, String userPwd) throws Exception;
	boolean modifyUser(UserDto dto);
	int findPw(UserDto dto);
	boolean deleteMember(UserDto dto);
	UserDto getMember(String userId);
	String findId(String email);
	void changePwd(UserDto dto);
	List<UserDto> searchMember(String userId);
	void changeProfile(UserDto userDto);
}
