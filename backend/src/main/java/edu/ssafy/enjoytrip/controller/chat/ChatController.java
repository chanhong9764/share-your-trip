package edu.ssafy.enjoytrip.controller.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ssafy.enjoytrip.dto.chat.ChattingDto;
import edu.ssafy.enjoytrip.dto.chat.ChattingParticipantDto;
import edu.ssafy.enjoytrip.dto.chat.ChattingRoomDto;
import edu.ssafy.enjoytrip.dto.chat.InvitationDto;
import edu.ssafy.enjoytrip.service.chat.ChatService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
@Api(tags = { "Chat 컨트롤러 API" })
public class ChatController {
	private final ChatService service;
	
	@GetMapping("/{userId}")
	public ResponseEntity<Map<String, Object>> GetChattingRoomList(@PathVariable("userId") String userId) {
		Map<String, Object> result = new HashMap<>();
		try {
			ArrayList<ChattingRoomDto> chattingRoomList = service.getChattingRoomList(userId);
			result.put("msg", "여행 그룹 리스트 불러오기 성공!");
			result.put("result", chattingRoomList);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "여행 그룹 리스트 불러오기 실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> GetChattingList(@RequestParam Map<String, String> map) {
		System.out.println(map);
		Map<String, Object> result = new HashMap<>();
		try {
			ArrayList<ChattingDto> chattingList = service.getChattingList(map);
			result.put("msg", "채팅 리스트 불러오기 성공!");
			result.put("result", chattingList);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "채팅 리스트 불러오기 실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}
	
	@DeleteMapping
	public ResponseEntity<Map<String, Object>> deleteChattingRoom(@RequestParam Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		try {
			service.deleteChattingRoom(map);
			result.put("msg", "채팅 리스트 불러오기 성공!");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "채팅 리스트 불러오기 실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}
	
	@DeleteMapping("/invitations")
	public ResponseEntity<Map<String, Object>> deleteChattingRoomById(@RequestParam Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		System.out.println(map);
		try {
			service.deleteChattingRoomById(String.valueOf(map.get("participantId")));
			result.put("msg", "채팅 리스트 불러오기 성공!");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "채팅 리스트 불러오기 실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}
	
	// status, roomid, userid
	@PatchMapping
	public ResponseEntity<Map<String, Object>> updateChattingRoom(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		try {
			service.updateParticipantRoomById(String.valueOf(map.get("participantId")));
			result.put("msg", "채팅방 상태 업데이트 성공!");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "채팅 상태 업데이트 실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}
	
	@GetMapping("/invitations")
	public ResponseEntity<Map<String, Object>> getInvitationsRoom(@RequestParam("userId") String userId) {
		Map<String, Object> result = new HashMap<>();
		try {
			ArrayList<InvitationDto> invitation = service.getInvitation(userId);
			result.put("msg", "초대장 불러오기 성공!");
			result.put("result", invitation);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "초대장 불러오기  실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}
	
	@GetMapping("/ranges")
	public ResponseEntity<Map<String, Object>> getChattingRange(@RequestParam Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		try {
			ArrayList<ChattingDto> chattingListRange = service.getChattingListRange(map);
			result.put("msg", "채팅 불러오기 성공!");
			result.put("result", chattingListRange);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "채팅 불러오기  실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}
}
