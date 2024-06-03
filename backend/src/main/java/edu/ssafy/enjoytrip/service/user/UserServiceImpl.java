package edu.ssafy.enjoytrip.service.user;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import edu.ssafy.enjoytrip.dto.user.JwtToken;
import edu.ssafy.enjoytrip.dto.user.UserDto;
import edu.ssafy.enjoytrip.response.code.CommonResponseCode;
import edu.ssafy.enjoytrip.response.code.CustomResponseCode;
import edu.ssafy.enjoytrip.response.exception.RestApiException;
import edu.ssafy.enjoytrip.response.structure.ErrorResponse;
import edu.ssafy.enjoytrip.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.ssafy.enjoytrip.dto.user.User;
import edu.ssafy.enjoytrip.mapper.BoardMapper;
import edu.ssafy.enjoytrip.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service("UserServiceImpl")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    @Value("${jwt.refresh-expiration-time}")
    private long refreshTokenExpiresIn;

    private final JavaMailSender javaMailSender;
    private static final String senderEmail= "chan97842@gmail.com";
    private static int number;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto.UserInfoResponseDTO findById(final String userId) {
        return userMapper.findById(userId)
                .orElseThrow(()-> new RestApiException(CustomResponseCode.USER_NOT_FOUND)).toUserInfoResponse();
    }

    @Override
    public void addUser(final UserDto.AddRequestDTO requestDTO) {
        userMapper.findById(requestDTO.getUserId()).ifPresent((user) -> {
                    throw new RestApiException(CustomResponseCode.USER_EXIST);
                });
        requestDTO.updatePassword(passwordEncoder.encode(requestDTO.getUserPassword()));

        User user = requestDTO.toEntity();
        try {
            userMapper.addUser(user);
        } catch (DataIntegrityViolationException e) {
            throw new RestApiException(CustomResponseCode.INVALID_USER_INFO);
        }
    }

    @Override
    public void deleteUser(final String userId) {
        int cnt = userMapper.deleteUser(userId);

        if(cnt == 0) {
            throw new RestApiException(CustomResponseCode.USER_NOT_FOUND);
        }
    }

    @Override
    public UserDto.UserInfoResponseDTO login(final UserDto.LoginRequestDTO requestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestDTO.getUserId(), requestDTO.getUserPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        User user = userMapper.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RestApiException(CustomResponseCode.USER_NOT_FOUND));

        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        redisTemplate.opsForValue().set(
                user.getUserId(),
                jwtToken.getRefreshToken(),
                refreshTokenExpiresIn,
                TimeUnit.MILLISECONDS
        );

        return UserDto.UserInfoResponseDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .joinDate(user.getJoinDate())
                .profile(user.getProfile())
                .role(user.getRole())
                .email(user.getEmail())
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken())
                .build();
    }

    @Override
    public JwtToken regenerateToken(UserDto.RegenerateTokenDto requestDto) {
        Authentication authentication = jwtTokenProvider.getAuthentication(requestDto.getRefreshToken());

        // Redis에서 refresh token 값을 가져온다.
        String refreshToken = redisTemplate.opsForValue().get(authentication.getName());

        if(!requestDto.getRefreshToken().equals(refreshToken)) {
            throw new RestApiException(CommonResponseCode.INVALID_PARAMETER);
        }

        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        redisTemplate.opsForValue().set(
                authentication.getName(),
                jwtToken.getRefreshToken(),
                refreshTokenExpiresIn,
                TimeUnit.MILLISECONDS
        );
        return jwtToken;
    }

    @Override
    public UserDto.UserInfoResponseDTO modifyUser(final UserDto.ModifyRequestDTO requestDTO) {
        if(!requestDTO.getUserPassword().equals(requestDTO.getUserConfirmPassword())) {
            throw new RestApiException(CustomResponseCode.INVALID_USER_PARAMETER);
        }

        User user = requestDTO.toEntity();

        int cnt = userMapper.modifyUser(user);
        if(cnt == 0) {
            throw new RestApiException(CustomResponseCode.USER_NOT_FOUND);
        }
        return userMapper.findById(user.getUserId())
                .orElseThrow(() -> new RestApiException(CustomResponseCode.USER_NOT_FOUND)).toUserInfoResponse();
    }

    @Override
    public void checkById(final String userId) {
        if (userMapper.checkById(userId) != null) {
            throw new RestApiException(CustomResponseCode.INVALID_USER_ID);
        }
    }

    @Override
    public List<UserDto.UserInfoResponseDTO> searchUser(final String userId) {
        List<User> userList = userMapper.searchUser(userId);

        if(userList == null || userList.isEmpty()) {
            throw new RestApiException(CustomResponseCode.USER_NOT_FOUND);
        }
        return userList.stream()
                .map(User::toUserInfoResponse)
                .collect(Collectors.toList());
    }
    @Override
    public int sendEmail(final String email) {
        MimeMessage message = CreateMail(email);
        javaMailSender.send(message);
        return number;
    }

    @Override
    public String findByEmail(final String email) {
        return userMapper.findByEmail(email)
                .orElseThrow(() -> new RestApiException(CustomResponseCode.USER_NOT_FOUND));
    }

    @Override
    public void modifyPassword(final UserDto.ModifyRequestDTO requestDTO) {
        if(!requestDTO.getUserPassword().equals(requestDTO.getUserConfirmPassword())) {
            throw new RestApiException(CustomResponseCode.INVALID_USER_PARAMETER);
        }

        User user = requestDTO.toEntity();
        int cnt = userMapper.changePassword(user);
        if(cnt == 0) {
            throw new RestApiException(CustomResponseCode.USER_NOT_FOUND);
        }
    }

    @Override
    public void modifyProfile(final UserDto.ModifyProfileRequestDTO requestDTO) {
        int cnt = userMapper.modifyProfile(requestDTO.toEntity());
        if(cnt == 0) {
            throw new RestApiException(CustomResponseCode.USER_NOT_FOUND);
        }
    }

    // 인증번호 생성기
    public void createNumber(){
        number = (int)(Math.random() * (90000)) + 100000;// (int) Math.random() * (최댓값-최소값+1) + 최소값
    }

    // 메일 내용 생성
    public MimeMessage CreateMail(final String mail){
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            throw new RestApiException(CustomResponseCode.EMAIL_NOT_CREATED);
        }
        return message;
    }
}
