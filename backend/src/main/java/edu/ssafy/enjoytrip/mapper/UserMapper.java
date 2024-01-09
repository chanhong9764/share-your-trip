package edu.ssafy.enjoytrip.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;

import edu.ssafy.enjoytrip.dto.user.UserDto;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface UserMapper {

	Optional<UserDto> findById(String userId);
	void createUser(UserDto memberDto);
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
