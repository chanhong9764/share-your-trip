package edu.ssafy.enjoytrip.dto.user;

import lombok.*;
import org.apache.ibatis.type.Alias;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Alias("user")
public class User {
	private String userId;
	private String userPassword;
	private String userName;
	private String email;
	private String joinDate;
	private String salt;
	private String profile;
	@Builder
	public User(String userId, String userPassword, String userName, String email, String joinDate, String salt, String profile) {
		this.userId = userId;
		this.userPassword = userPassword;
		this.userName = userName;
		this.email = email;
		this.joinDate = joinDate;
		this.salt = salt;
		this.profile = profile;
	}

	public void passwordAndSaltUpdate(String userPassword, String salt) {
		this.userPassword = userPassword;
		this.salt = salt;
	}

	public void passwordUpdate(String userPassword) {
		this.userPassword = userPassword;
	}

	public UserDto.UserInfoResponseDTO toUserInfoResponse() {
		return UserDto.UserInfoResponseDTO.builder()
				.userId(userId)
				.userName(userName)
				.email(email)
				.joinDate(joinDate)
				.profile(profile)
				.build();
	}
}
