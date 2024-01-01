package edu.ssafy.enjoytrip.controller.user;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.servlet.ModelAndView;

import edu.ssafy.enjoytrip.dto.board.BoardDto;
import edu.ssafy.enjoytrip.dto.board.BoardImagesDto;
import edu.ssafy.enjoytrip.dto.user.UserDto;
import edu.ssafy.enjoytrip.error.errorcode.CustomErrorCode;
import edu.ssafy.enjoytrip.error.exception.RestApiException;
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
	public ResponseEntity<Map<String, Object>> CreateUser(@RequestBody UserDto dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int res = service.joinMember(dto);
			map.put("resmsg", "입력성공");
			map.put("resdata", res);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "입력실패");
			map.put("resdata", e.getMessage());
		}
		ResponseEntity<Map<String, Object>> res = new ResponseEntity(map, HttpStatus.OK);
		return res;
	}

	@PutMapping
	public ResponseEntity<Map<String, Object>> ModifyUser(@RequestBody UserDto dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			boolean res = service.modifyUser(dto);
			map.put("resdata", res);
			map.put("resmsg", "수정성공");

		} catch (Exception e) {
			map.put("resmsg", "수정실패");
			map.put("resdata", e.getMessage());
		}
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<>(map, HttpStatus.OK);
		return res;
	}

	@DeleteMapping("/{userid}")
	public ResponseEntity<Map<String, Object>> DeleteUser(@PathVariable("userid") String id) {
		Map<String, Object> map = new HashMap<>();
		UserDto dto = new UserDto();
		dto.setUserId(id);
		try {
			boolean res = service.deleteMember(dto);
			map.put("resdata", res);
			map.put("resmsg", "삭제성공");

		} catch (Exception e) {
			map.put("resmsg", "삭제실패");
			map.put("resdata", e.getMessage());
		}
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<>(map, HttpStatus.OK);
		return res;
	}

	@GetMapping("/{userid}")
	public ResponseEntity<Map<String, Object>> GetUserInfo(@PathVariable("userid") String userId) throws Exception {
		Map<String, Object> map = new HashMap();
		try {
			UserDto member = service.getMember(userId);
			map.put("msg", "유저 정보 확인 성공!");
			map.put("result", member);
		} catch (Exception e) {
			map.put("resmsg", "중복확인불가");
			map.put("resdata", e.getMessage());
		}
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<>(map, HttpStatus.OK);
		return res;
	}

	@GetMapping("/search/{userid}")
	public ResponseEntity<Map<String, Object>> SearchUser(@PathVariable("userid") String userId) throws Exception {
		Map<String, Object> map = new HashMap();
		try {
			List<UserDto> searchMember = service.searchMember(userId);
			map.put("msg", "유저 검색 성공!");
			map.put("result", searchMember);
		} catch (Exception e) {
			map.put("resmsg", "중복확인불가");
			map.put("resdata", e.getMessage());
		}
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<>(map, HttpStatus.OK);
		return res;
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> Login(@RequestBody UserDto memberDto) {
		Map<String, Object> map = new HashMap();
		try {
			UserDto res = service.loginMember(memberDto.getUserId(), memberDto.getUserPassword());
			System.out.println(res);
			map.put("result", res);
			map.put("resmsg", "로그인성공");

		} catch (Exception e) {
			map.put("resmsg", "로그인실패");
			map.put("result", e.getMessage());
		}
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<>(map, HttpStatus.OK);
		return res;
	}

	@GetMapping("/check/{userid}")
	public ResponseEntity<Map<String, Object>> CheckId(@PathVariable("userid") String userId) throws Exception {
		Map<String, Object> map = new HashMap();
		try {
			if (service.idCheck(userId) == 0) {
				map.put("resdata", false);
			} else {
				map.put("resdata", true);
			}
			map.put("resmsg", "중복확인");

		} catch (Exception e) {
			map.put("resmsg", "중복확인불가");
			map.put("resdata", e.getMessage());
		}
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<>(map, HttpStatus.OK);
		return res;
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
