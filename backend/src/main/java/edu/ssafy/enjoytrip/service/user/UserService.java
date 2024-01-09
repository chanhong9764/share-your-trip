package edu.ssafy.enjoytrip.service.user;

import java.util.List;
import java.util.Optional;

import edu.ssafy.enjoytrip.dto.board.BoardImagesDto;
import edu.ssafy.enjoytrip.dto.user.UserDto;

public interface UserService {

	// 유저 ID를 이용하여 사용자 검색
	UserDto findById(String userId);
	void createUser(UserDto dto);
	UserDto login(UserDto dto);
	UserDto modifyUser(UserDto dto);
	void checkById(String userId);
	int findPw(UserDto dto);
	void deleteUser(String userId);
	String findId(String email);
	void changePwd(UserDto dto);
	List<UserDto> searchUser(String userId);
	void changeProfile(UserDto dto);
}
