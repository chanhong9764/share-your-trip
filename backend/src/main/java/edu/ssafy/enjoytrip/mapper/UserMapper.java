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
	void createUser(UserDto dto);
	String getUserSalt(String userId);
	int modifyUser(UserDto dto);
	String checkById(String userId);
	Optional<String> findByEmail(String email);
	int changePassword(UserDto dto);
	int deleteUser(String userId);
	Optional<UserDto> login(UserDto dto);
	List<UserDto> searchUser(String userId);
	int changeProfile(UserDto userDto);
}
