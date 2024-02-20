package edu.ssafy.enjoytrip.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import edu.ssafy.enjoytrip.validation.user.UserEmail;
import edu.ssafy.enjoytrip.validation.user.UserId;
import edu.ssafy.enjoytrip.validation.user.UserName;
import edu.ssafy.enjoytrip.validation.user.UserPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class AddRequestDTO {
        @UserId
        private String userId;
        @UserPassword
        private String userPassword;
        @UserName
        private String userName;
        @UserEmail
        private String email;
        @Builder
        public AddRequestDTO(String userId, String userPassword, String userName, String email) {
            this.userId = userId;
            this.userPassword = userPassword;
            this.userName = userName;
            this.email = email;
        }

        public void updatePassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public User toEntity() {
            return User.builder()
                    .userId(userId)
                    .userPassword(userPassword)
                    .userName(userName)
                    .email(email).build();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class ModifyProfileRequestDTO {
        @UserId
        private String userId;
        @NotBlank
        private String profile;
        public User toEntity() {
            return User.builder()
                    .userId(userId)
                    .profile(profile).build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LoginRequestDTO {
        @UserId
        private String userId;
        @UserPassword
        private String userPassword;

        public User toEntity() {
            return User.builder()
                    .userId(userId)
                    .userPassword(userPassword).build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UserInfoResponseDTO {
        @UserId
        private String userId;
        @UserName
        private String userName;
        @UserEmail
        private String email;
        @NotBlank
        private String joinDate;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private String profile;
        private Role role;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private String accessToken;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private String refreshToken;
        @Builder
        public UserInfoResponseDTO(
                String userId,
                String userName,
                String email,
                String joinDate,
                String profile,
                Role role,
                String accessToken,
                String refreshToken
        ) {
            this.userId = userId;
            this.userName = userName;
            this.email = email;
            this.joinDate = joinDate;
            this.profile = profile;
            this.role = role;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ModifyRequestDTO {
        @UserId
        private String userId;
        @UserPassword
        private String userPassword;
        @UserPassword
        private String userConfirmPassword;

        public User toEntity() {
            return User.builder()
                    .userId(userId)
                    .userPassword(userPassword).build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RegenerateTokenDto {
        private String refreshToken;

        public RegenerateTokenDto(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
}
