package edu.ssafy.enjoytrip.controller.user;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import edu.ssafy.enjoytrip.response.code.SuccessCode;
import edu.ssafy.enjoytrip.response.structure.SuccessResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.ssafy.enjoytrip.dto.board.BoardImagesDto;
import edu.ssafy.enjoytrip.dto.user.UserDto;
import edu.ssafy.enjoytrip.response.code.CustomResponseCode;
import edu.ssafy.enjoytrip.response.exception.RestApiException;
import edu.ssafy.enjoytrip.service.user.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Api(tags = { "멤버 컨트롤러 API" })
public class UserController {
	@Value("${file.path}")
	private String uploadPath;
	private final UserService service;

	@PostMapping
	public ResponseEntity<Object> CreateUser(@RequestBody UserDto dto) {
		service.createUser(dto);
		return SuccessResponse.createSuccess(SuccessCode.CREATED_USER_SUCCESS);
	}

	@PutMapping
	public ResponseEntity<Object> ModifyUser(@RequestBody UserDto dto) {
		UserDto user = service.modifyUser(dto);
		return SuccessResponse.createSuccess(SuccessCode.MODIFY_USER_SUCCESS, user);
	}

	@DeleteMapping("/{userid}")
	public ResponseEntity<Object> DeleteUser(@PathVariable("userid") String userId) {
		service.deleteUser(userId);
		return SuccessResponse.createSuccess(SuccessCode.DELETE_USER_SUCCESS);
	}

	@GetMapping("/{userid}")
	public ResponseEntity<Object> GetUser(@PathVariable("userid") String userId) {
		UserDto user = service.findById(userId);
		return SuccessResponse.createSuccess(SuccessCode.READ_USER_SUCCESS, user);
	}

	@GetMapping("/search/{userid}")
	public ResponseEntity<Object> SearchUser(@PathVariable("userid") String userId) {
		List<UserDto> userList = service.searchUser(userId);
		return SuccessResponse.createSuccess(SuccessCode.SEARCH_USER_SUCCESS, userList);
	}

	@PostMapping("/login")
	public ResponseEntity<Object> Login(@RequestBody UserDto dto) {
		UserDto user = service.login(dto);

		return SuccessResponse.createSuccess(SuccessCode.LOGIN_USER_SUCCESS, user);
	}

	@GetMapping("/check/{userid}")
	public ResponseEntity<Object> CheckById(@PathVariable("userid") String userId) {
		service.checkById(userId);
		return SuccessResponse.createSuccess(SuccessCode.VALID_USER_ID_SUCCESS);
	}

	@GetMapping("/findId/{email}")
	public ResponseEntity<Map<String, Object>> FindId(@PathVariable("email") String email) {
		Map<String, Object> map = new HashMap();
		try {
			System.out.println("아이디 찾기 오냐? : " + email);
			String mail = service.findId(email);
			map.put("msg", "아이디 찾기 성공!");
			map.put("resdata", mail);
		} catch (Exception e) {
			map.put("resmsg", "중복확인불가");
			map.put("resdata", e.getMessage());
		}
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<>(map, HttpStatus.OK);
		return res;
	}

	@PostMapping("/changeProfile")
	public ResponseEntity<Map<String, Object>> changeProfile(@RequestParam("userId") String userId,
			@RequestParam(value = "images") MultipartFile images) throws Exception {
		Map<String, Object> result = new HashMap<>();
		UserDto userDto = new UserDto();
		userDto.setUserId(userId);
		BoardImagesDto imageInfo = new BoardImagesDto();
		String today = new SimpleDateFormat("yyMMdd").format(new Date());
		if (!images.isEmpty()) {
			String saveFolder = uploadPath + today;
			File folder = new File(saveFolder);

			if (!folder.exists()) {
				folder.mkdirs();
			}

			String originalFileName = images.getOriginalFilename();
			if (!originalFileName.isEmpty()) {
				String saveFileName = UUID.randomUUID().toString()
						+ originalFileName.substring(originalFileName.lastIndexOf('.'));
				imageInfo.setSaveFolder(saveFolder);
				imageInfo.setOriginalName(originalFileName);
				imageInfo.setSaveFile(saveFileName);
				images.transferTo(new File(folder, saveFileName));
			}
		}
		userDto.setProfile("/"+today+"/" + imageInfo.getSaveFile());
		try {
			service.changeProfile(userDto);
			result.put("msg", "프로필 수정 성공!");
			result.put("result", userDto.getProfile());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "프로필 수정 실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String, Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}
}
