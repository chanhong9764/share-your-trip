package edu.ssafy.enjoytrip.dto.user;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias("userdto")
public class UserDto {
	private String userId;
	private String userPassword;
	private String userName;
	private String email;
	private String joinDate;
	private String salt;
	private String profile;
}
