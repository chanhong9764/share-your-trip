package edu.ssafy.enjoytrip.controller.user;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import edu.ssafy.enjoytrip.response.code.SuccessCode;
import edu.ssafy.enjoytrip.response.structure.SuccessResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import edu.ssafy.enjoytrip.dto.board.BoardImagesDto;
import edu.ssafy.enjoytrip.dto.user.UserDto;
import edu.ssafy.enjoytrip.service.user.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

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
	public ResponseEntity<Object> CreateUser(@RequestBody @Valid UserDto dto) {
		service.createUser(dto);
		return SuccessResponse.createSuccess(SuccessCode.CREATED_USER_SUCCESS);
	}

	@PatchMapping
	public ResponseEntity<Object> ModifyUser(@RequestBody @Valid UserDto dto) {
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

	@GetMapping("/send/{email}")
	public ResponseEntity<Object> sendMail(@PathVariable("email") String email) {
		int authNumber = service.SendEmail(email);
		return SuccessResponse.createSuccess(SuccessCode.SEND_USER_EMAIL_SUCCESS, authNumber);
	}

	@GetMapping("/find-id/{email}")
	public ResponseEntity<Object> FindByEmail(@PathVariable("email") String email) {
		String userId = service.FindByEmail(email);
		return SuccessResponse.createSuccess(SuccessCode.FIND_USER_ID_SUCCESS, userId);
	}

	@PatchMapping("/password")
	public ResponseEntity<Object> ChangePassword(@RequestBody UserDto dto) {
		service.ChangePassword(dto);
		return SuccessResponse.createSuccess(SuccessCode.CHANGE_PASSWORD_USER_SUCCESS);
	}

	@PostMapping("/changeProfile")
	public ResponseEntity<Object> changeProfile(@RequestParam("userId") String userId,
			@RequestParam(value = "images") MultipartFile images) throws Exception {
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
			if (originalFileName != null && !originalFileName.isEmpty()) {
				String saveFileName = UUID.randomUUID().toString()
						+ originalFileName.substring(originalFileName.lastIndexOf('.'));
				imageInfo.setSaveFolder(saveFolder);
				imageInfo.setOriginalName(originalFileName);
				imageInfo.setSaveFile(saveFileName);
				images.transferTo(new File(folder, saveFileName));
			}
		}
		userDto.setProfile("/"+today+"/" + imageInfo.getSaveFile());

		service.changeProfile(userDto);

		return SuccessResponse.createSuccess(SuccessCode.CHANGE_PROFILE_USER_SUCCESS, userDto.getProfile());
	}
}
