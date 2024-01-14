package edu.ssafy.enjoytrip.service.user;

import java.util.List;

import edu.ssafy.enjoytrip.dto.user.User;
import edu.ssafy.enjoytrip.dto.user.UserDto;

public interface UserService {
	UserDto.UserInfoResponseDTO findById(final String userId);
	void addUser(UserDto.AddRequestDTO requestDTO);
	UserDto.UserInfoResponseDTO login(UserDto.LoginRequestDTO requestDTO);
	UserDto.UserInfoResponseDTO modifyUser(final UserDto.ModifyRequestDTO requestDTO);
	void checkById(final String userId);
	int sendEmail(final String email);
	void deleteUser(final String userId);
	String findByEmail(final String email);
	void modifyPassword(UserDto.ModifyRequestDTO requestDTO);
	List<UserDto.UserInfoResponseDTO> searchUser(final String userId);
	void modifyProfile(UserDto.ModifyProfileRequestDTO requestDTO);
}
