package edu.ssafy.enjoytrip.dto.user;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias("user")
public class UserDto {
	@NotBlank(message = "아이디를 입력해주세요.")
	@Length(min = 4, max = 10, message = "최소 4자 이상, 10자 이하를 입력해주세요.")
	@Pattern(regexp = "^[a-z0-9]*$", message = "알파벳 소문자(a~z), 숫자(0~9)만 입력 가능합니다.")
	private String userId;

	@NotBlank(message = "패스워드를 입력해주세요.")
	@Length(min = 8, max = 16, message = "최소 8자 이상, 16자 이하를 입력해주세요.")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$", message = "비밀번호는 영문 대/소문자, 숫자, 특수문자를 사용하세요.")
	private String userPassword;

	@NotBlank(message = "이름을 입력해주세요.")
	@Length(min = 2, max = 20, message = "최소 2자 이상, 20자 이하를 입력해주세요.")
	@Pattern(regexp = "^[가-힣]*$", message = "한글만 입력해주세요.")
	private String userName;

	@NotBlank(message = "이메일을 입력해주세요.")
	@Length(min = 1, max = 64, message = "최소 1자 이상, 64자 이하를 입력해주세요.")
	@Pattern(regexp = "^[0-9a-zA-Z]([-_\\\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\\\.]?[0-9a-zA-Z])*\\\\.[a-zA-Z]{2,3}+$", message = "이메일 형식이 올바르지 않습니다.")
	private String email;
	private String joinDate;
	private String salt;
	private String profile;
}
