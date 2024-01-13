package edu.ssafy.enjoytrip.service.user;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import edu.ssafy.enjoytrip.response.code.CommonResponseCode;
import edu.ssafy.enjoytrip.response.code.CustomResponseCode;
import edu.ssafy.enjoytrip.response.exception.RestApiException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import edu.ssafy.enjoytrip.dto.user.UserDto;
import edu.ssafy.enjoytrip.mapper.BoardMapper;
import edu.ssafy.enjoytrip.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service("UserServiceImpl")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JavaMailSender javaMailSender;
    private static final String senderEmail= "chan97842@gmail.com";
    private static int number;
    private final UserMapper userMapper;
    private final BoardMapper BoardMapper;

    @Override
    public UserDto findById(String userId) {
        return userMapper.findById(userId)
                .orElseThrow(()-> new RestApiException(CustomResponseCode.USER_NOT_FOUND));
    }

    @Override
    public void createUser(UserDto dto) {
        byte[] salt = getSalt();

        byte[] byteDigestPsw = getSaltHashSHA512(dto.getUserPassword(), salt);
        String strDigestPsw = toHex(byteDigestPsw);
        String strSalt = toHex(salt);

        dto.setUserPassword(strDigestPsw);
        dto.setSalt(strSalt);

        try {
            userMapper.createUser(dto);
        } catch (DataIntegrityViolationException e) {
            throw new RestApiException(CustomResponseCode.INVALID_USER_INFO);
        }
    }

    @Override
    public void deleteUser(String userId) {
        int cnt = userMapper.deleteUser(userId);

        if(cnt == 0) {
            throw new RestApiException(CustomResponseCode.USER_NOT_FOUND);
        }
    }

    @Override
    public UserDto login(UserDto dto) {
        String strSalt = getSaltById(dto.getUserId());

        byte[] salt = fromHex(strSalt);
        byte[] byteDigestPsw = getSaltHashSHA512(dto.getUserPassword(), salt);
        String strDigestPsw = toHex(byteDigestPsw);

        dto.setUserPassword(strDigestPsw);

        return userMapper.login(dto)
                .orElseThrow(() -> new RestApiException(CommonResponseCode.UNAUTHORIZED_REQUEST));
    }

    @Override
    public UserDto modifyUser(UserDto dto) {
        String strSalt = getSaltById(dto.getUserId());

        byte[] salt = fromHex(strSalt);
        byte[] byteDigestPsw = getSaltHashSHA512(dto.getUserPassword(), salt);
        String strDigestPsw = toHex(byteDigestPsw);

        dto.setUserPassword(strDigestPsw);
        int cnt = userMapper.modifyUser(dto);
        if(cnt == 0) {
            throw new RestApiException(CustomResponseCode.USER_NOT_FOUND);
        }
        return userMapper.findById(dto.getUserId())
                .orElseThrow(() -> new RestApiException(CustomResponseCode.USER_NOT_FOUND));
    }

    @Override
    public void checkById(String userId) {
        if (userMapper.checkById(userId) != null) {
            throw new RestApiException(CustomResponseCode.INVALID_USER_ID);
        }
    }

    @Override
    public List<UserDto> searchUser(String userId) {
        List<UserDto> userList = userMapper.searchUser(userId);

        if(userList == null || userList.isEmpty()) {
            throw new RestApiException(CustomResponseCode.USER_NOT_FOUND);
        }
        return userList;
    }
    @Override
    public int SendEmail(String mail){
        MimeMessage message = CreateMail(mail);
        javaMailSender.send(message);
        return number;
    }

    @Override
    public String FindByEmail(String email) {
        return userMapper.findByEmail(email)
                .orElseThrow(() -> new RestApiException(CustomResponseCode.USER_NOT_FOUND));
    }

    @Override
    public void ChangePassword(UserDto dto) {
        String strSalt = getSaltById(dto.getUserId());
        byte[] salt = fromHex(strSalt);
        byte[] byteDigestPsw = getSaltHashSHA512(dto.getUserPassword(), salt);
        String strDigestPsw = toHex(byteDigestPsw);

        dto.setUserPassword(strDigestPsw);
        int cnt = userMapper.changePassword(dto);
        if(cnt == 0) {
            throw new RestApiException(CustomResponseCode.USER_NOT_FOUND);
        }
    }

    @Override
    public void changeProfile(UserDto userDto) {
        int cnt = userMapper.changeProfile(userDto);
        if(cnt == 0) {
            throw new RestApiException(CustomResponseCode.USER_NOT_FOUND);
        }
    }

    public String getSaltById(String userId) {
        return Optional.of(userMapper.getUserSalt(userId))
                .orElseThrow(() -> new RestApiException(CustomResponseCode.USER_NOT_FOUND));
    }

    // 인증번호 생성기
    public void createNumber(){
        number = (int)(Math.random() * (90000)) + 100000;// (int) Math.random() * (최댓값-최소값+1) + 최소값
    }

    // 메일 내용 생성
    public MimeMessage CreateMail(String mail){
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + (int)(Math.random() * (90000)) + 100000 + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            throw new RestApiException(CustomResponseCode.EMAIL_NOT_CREATED);
        }
        return message;
    }

    private byte[] getSaltHashSHA512(String userPassword, byte[] salt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RestApiException(CustomResponseCode.PASSWORD_NOT_CREATED);
        }
        md.update(salt);
        byte[] byteData = md.digest(userPassword.getBytes());
        md.reset();
        return byteData;
    }

    private byte[] getSalt() {
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RestApiException(CustomResponseCode.PASSWORD_NOT_CREATED);
        }
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    public byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    public String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
}
