package edu.ssafy.enjoytrip.controller.user;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import edu.ssafy.enjoytrip.dto.user.JwtToken;
import edu.ssafy.enjoytrip.dto.user.UserDto;
import edu.ssafy.enjoytrip.response.code.CustomResponseCode;
import edu.ssafy.enjoytrip.response.code.SuccessCode;
import edu.ssafy.enjoytrip.response.exception.RestApiException;
import edu.ssafy.enjoytrip.response.structure.SuccessResponse;
import edu.ssafy.enjoytrip.validation.common.Image;
import edu.ssafy.enjoytrip.validation.user.UserEmail;
import edu.ssafy.enjoytrip.validation.user.UserId;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import edu.ssafy.enjoytrip.dto.board.BoardImagesDto;
import edu.ssafy.enjoytrip.service.user.UserService;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/users")
public class UserController {
	@Value("${file.path}")
	private String uploadPath;
	private final UserService service;

	@PostMapping
	public ResponseEntity<Object> AddUser(@RequestBody @Valid UserDto.AddRequestDTO requestDTO) {
		service.addUser(requestDTO);
		return SuccessResponse.createSuccess(SuccessCode.CREATED_USER_SUCCESS);
	}

	@PatchMapping
	public ResponseEntity<Object> ModifyUser(@RequestBody @Valid UserDto.ModifyRequestDTO requestDTO) {
		UserDto.UserInfoResponseDTO responseDTO = service.modifyUser(requestDTO);
		return SuccessResponse.createSuccess(SuccessCode.MODIFY_USER_SUCCESS, responseDTO);
	}

	@DeleteMapping("/{userid}")
	public ResponseEntity<Object> DeleteUser(@PathVariable("userid") @UserId final String userId) {
		service.deleteUser(userId);
		return SuccessResponse.createSuccess(SuccessCode.DELETE_USER_SUCCESS);
	}

	@PostAuthorize("(returnObject.body.data.userId == principal.username) or hasRole('ROLE_ADMIN')")
	@GetMapping("/{userid}")
	public ResponseEntity<Object> SelectUser(@PathVariable("userid") @UserId final String userId) {
		UserDto.UserInfoResponseDTO responseDTO = service.findById(userId);
		return SuccessResponse.createSuccess(SuccessCode.READ_USER_SUCCESS, responseDTO);
	}

	@GetMapping("/search/{userid}")
	public ResponseEntity<Object> SearchUser(@PathVariable("userid") @UserId final String userId) {
		List<UserDto.UserInfoResponseDTO> reponseDtoList = service.searchUser(userId);
		return SuccessResponse.createSuccess(SuccessCode.SEARCH_USER_SUCCESS, reponseDtoList);
	}

	@PostMapping("/login")
	public ResponseEntity<Object> Login(@RequestBody @Valid UserDto.LoginRequestDTO requestDTO) {
		UserDto.UserInfoResponseDTO responseDTO = service.login(requestDTO);
		return SuccessResponse.createSuccess(SuccessCode.LOGIN_USER_SUCCESS, responseDTO);
	}

	@PostMapping("/regenerateToken")
	public ResponseEntity<Object> regenerateToken(@RequestBody UserDto.RegenerateTokenDto requestDTO) throws JwtException {
		JwtToken jwtToken = service.regenerateToken(requestDTO);
		return SuccessResponse.createSuccess(SuccessCode.CREATED_TOKEN_SUCCESS, jwtToken);
	}

	@GetMapping("/check/{userid}")
	public ResponseEntity<Object> CheckById(@PathVariable("userid") @UserId final String userId) {
		service.checkById(userId);
		return SuccessResponse.createSuccess(SuccessCode.VALID_USER_ID_SUCCESS);
	}

	@GetMapping("/send/{email}")
	public ResponseEntity<Object> SendMail(@PathVariable("email") @UserEmail final String email) {
		int authNumber = service.sendEmail(email);
		return SuccessResponse.createSuccess(SuccessCode.SEND_USER_EMAIL_SUCCESS, authNumber);
	}

	@GetMapping("/find-id/{email}")
	public ResponseEntity<Object> FindByEmail(@PathVariable("email") @UserEmail final String email) {
		String userId = service.findByEmail(email);
		return SuccessResponse.createSuccess(SuccessCode.FIND_USER_ID_SUCCESS, userId);
	}

	@PatchMapping("/password")
	public ResponseEntity<Object> ModifyPassword(@RequestBody @Valid UserDto.ModifyRequestDTO requestDTO) {
		service.modifyPassword(requestDTO);
		return SuccessResponse.createSuccess(SuccessCode.CHANGE_PASSWORD_USER_SUCCESS);
	}

	@PostMapping("/modify-profile")
	public ResponseEntity<Object> ModifyProfile(@RequestParam("userId") @UserId final String userId,
			@RequestParam(value = "profile") @Image MultipartFile profile) {
		BoardImagesDto imageInfo = new BoardImagesDto();
		String today = new SimpleDateFormat("yyMMdd").format(new Date());
		if (!profile.isEmpty()) {
			String saveFolder = uploadPath + today;
			File folder = new File(saveFolder);

			if (!folder.exists()) {
				folder.mkdirs();
			}

			String originalFileName = profile.getOriginalFilename();
			if (originalFileName != null && !originalFileName.isEmpty()) {
				String saveFileName = UUID.randomUUID().toString()
						+ originalFileName.substring(originalFileName.lastIndexOf('.'));
				imageInfo.setSaveFolder(saveFolder);
				imageInfo.setOriginalName(originalFileName);
				imageInfo.setSaveFile(saveFileName);
				try {
					profile.transferTo(new File(folder, saveFileName));
				} catch (IOException e) {
					throw new RestApiException(CustomResponseCode.IMAGE_NOT_CREATED);
				}
			}
		}
		UserDto.ModifyProfileRequestDTO requestDTO = new UserDto.ModifyProfileRequestDTO(userId, "/"+today+"/" + imageInfo.getSaveFile());
		service.modifyProfile(requestDTO);

		return SuccessResponse.createSuccess(SuccessCode.CHANGE_PROFILE_USER_SUCCESS, requestDTO.getProfile());
	}
}
