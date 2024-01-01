package edu.ssafy.enjoytrip.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;

import edu.ssafy.enjoytrip.dto.user.UserDto;

@Mapper
public interface UserMapper {

	int idCheck(String userId) throws SQLException;
	int joinMember(UserDto memberDto) throws SQLException;
	UserDto getSaltMember(Map<String, String> map) throws SQLException;
	boolean modifyUser(UserDto dto);
	int findPw(UserDto dto);
	String findId(String email);
	void changePwd(UserDto dto);
	boolean deleteMember(UserDto dto);
	UserDto loginMemberSalt(Map<String, String> map);
	UserDto getMember(String userId);
	List<UserDto> searchMember(String userId);
	void changeProfile(UserDto userDto);
}
