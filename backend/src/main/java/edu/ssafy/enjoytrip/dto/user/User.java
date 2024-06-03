package edu.ssafy.enjoytrip.dto.user;

import lombok.*;
import org.apache.ibatis.type.Alias;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Alias("user")
public class User {
	private String userId;
	private String userPassword;
	private String userName;
	private String email;
	private String joinDate;
	private String profile;
	private Role role;
	private String refreshToken;
	@Builder
	public User(String userId, String userPassword, String userName, String email, String joinDate, String profile, Role role) {
		this.userId = userId;
		this.userPassword = userPassword;
		this.userName = userName;
		this.email = email;
		this.joinDate = joinDate;
		this.profile = profile;
		this.role = role;
	}

	public UserDto.UserInfoResponseDTO toUserInfoResponse() {
		return UserDto.UserInfoResponseDTO.builder()
				.userId(userId)
				.userName(userName)
				.email(email)
				.joinDate(joinDate)
				.profile(profile)
				.role(role)
				.build();
	}
}
