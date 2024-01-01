package edu.ssafy.enjoytrip.service.user;

import java.util.List;

import edu.ssafy.enjoytrip.dto.board.BoardImagesDto;
import edu.ssafy.enjoytrip.dto.user.UserDto;

public interface UserService {

	int idCheck(String userId) throws Exception;
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
