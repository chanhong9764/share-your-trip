package edu.ssafy.enjoytrip.service.user;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ssafy.enjoytrip.dto.board.BoardImagesDto;
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
	public int idCheck(String userId) throws Exception {
		System.out.println(userId);
		return userMapper.idCheck(userId);
	}

	@Override
	public int joinMember(UserDto memberDto) throws Exception {
		byte[] salt = null;
		try {
			salt = getSalt();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println(memberDto);
		byte[] byteDigestPsw = getSaltHashSHA512(memberDto.getUserPassword(), salt);
		String strDigestPsw = toHex(byteDigestPsw);
		String strSalt = toHex(salt);

		memberDto.setUserPassword(strDigestPsw);
		memberDto.setSalt(strSalt);
		return userMapper.joinMember(memberDto);
	}

	@Override
	public UserDto loginMember(String userId, String userPwd) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("userPassword", userPwd);
		UserDto dto = userMapper.getSaltMember(map);
		byte[] salt = null;
		String strSalt = dto.getSalt();
		salt = fromHex(strSalt);
		byte[] byteDigestPsw = getSaltHashSHA512(userPwd, salt);
		String strDigestPsw = toHex(byteDigestPsw);

		map.put("userPassword", strDigestPsw);

		UserDto result = userMapper.loginMemberSalt(map);
		return result;
	}

	@Override
	public boolean modifyUser(UserDto dto) {
		byte[] salt = null;
		UserDto temp = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", dto.getUserId());
		try {
			temp = userMapper.getSaltMember(map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String strSalt = temp.getSalt();
		salt = fromHex(strSalt);
		byte[] byteDigestPsw = getSaltHashSHA512(dto.getUserPassword(), salt);
		String strDigestPsw = toHex(byteDigestPsw);

		dto.setUserPassword(strDigestPsw);
		dto.setSalt(strSalt);
		return userMapper.modifyUser(dto);
	}

	@Override
	public int findPw(UserDto dto) {
		return userMapper.findPw(dto);
	}

	@Override
	public boolean deleteMember(UserDto dto) {
		return userMapper.deleteMember(dto);

	}

	@Override
	public UserDto getMember(String userId) {
		return userMapper.getMember(userId);
	}

	private byte[] getSaltHashSHA512(String userPwd, byte[] salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			byte[] byteData = md.digest(userPwd.getBytes());
			md.reset();
			return byteData;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
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
	public List<UserDto> searchMember(String userId) {
		return userMapper.searchMember(userId);
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
