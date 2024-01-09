package edu.ssafy.enjoytrip.service.user;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import edu.ssafy.enjoytrip.response.code.CommonResponseCode;
import edu.ssafy.enjoytrip.response.code.CustomResponseCode;
import edu.ssafy.enjoytrip.response.exception.RestApiException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ssafy.enjoytrip.dto.user.UserDto;
import edu.ssafy.enjoytrip.mapper.BoardMapper;
import edu.ssafy.enjoytrip.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

@Service("UserServiceImpl")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
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
        String strSalt = Optional.of(userMapper.getUserSalt(dto.getUserId()))
                .orElseThrow(() -> new RestApiException(CustomResponseCode.USER_NOT_FOUND));

        byte[] salt = fromHex(strSalt);
        byte[] byteDigestPsw = getSaltHashSHA512(dto.getUserPassword(), salt);
        String strDigestPsw = toHex(byteDigestPsw);

        dto.setUserPassword(strDigestPsw);

        return userMapper.login(dto)
                .orElseThrow(() -> new RestApiException(CommonResponseCode.UNAUTHORIZED_REQUEST));
    }

    @Override
    public UserDto modifyUser(UserDto dto) {
        String strSalt = Optional.of(userMapper.getUserSalt(dto.getUserId()))
                .orElseThrow(() -> new RestApiException(CustomResponseCode.USER_NOT_FOUND));

        byte[] salt = fromHex(strSalt);
        byte[] byteDigestPsw = getSaltHashSHA512(dto.getUserPassword(), salt);
        String strDigestPsw = toHex(byteDigestPsw);

        dto.setUserPassword(strDigestPsw);
        userMapper.modifyUser(dto);
        return userMapper.findById(dto.getUserId())
                .orElseThrow(() -> new RestApiException(CustomResponseCode.USER_NOT_FOUND));
    }

    @Override
    public void checkById(String userId) {
        if(userMapper.checkById(userId) != null) {
            throw new RestApiException(CustomResponseCode.INVALID_USER_ID);
        }
    }

    @Override
    public int findPw(UserDto dto) {
        return userMapper.findPw(dto);
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
            sr = SecureRandom.getInstance("SHA1PRNG");
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

    @Override
    public List<UserDto> searchUser(String userId) {
        List<UserDto> userList = userMapper.searchUser(userId);

        if(userList == null || userList.isEmpty()) {
            throw new RestApiException(CustomResponseCode.USER_NOT_FOUND);
        }
        return userList;
    }

    @Override
    public String findId(String email) {
        return userMapper.findId(email);
    }

    @Override
    public void changePwd(UserDto dto) {
        // TODO Auto-generated method stub

    }

    @Override
    public void changeProfile(UserDto userDto) {
        System.out.println(userDto);
        userMapper.changeProfile(userDto);
    }
}
