package edu.ssafy.enjoytrip.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import edu.ssafy.enjoytrip.dto.user.User;

@Mapper
public interface UserMapper {

	Optional<User> findById(String userId);
	void addUser(User user);
	Optional<String> getUserSalt(String userId);
	int modifyUser(User user);
	String checkById(String userId);
	Optional<String> findByEmail(String email);
	int changePassword(User dto);
	int deleteUser(String userId);
	Optional<User> login(User dto);
	List<User> searchUser(String userId);
	int modifyProfile(User user);
}
