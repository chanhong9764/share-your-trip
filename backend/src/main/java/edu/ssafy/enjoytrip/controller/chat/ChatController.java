package edu.ssafy.enjoytrip.controller.chat;

import java.util.ArrayList;
import java.util.Map;

import edu.ssafy.enjoytrip.response.code.SuccessCode;
import edu.ssafy.enjoytrip.response.structure.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ssafy.enjoytrip.dto.chat.ChattingDto;
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
	public ResponseEntity<Object> GetChattingRoomList(@PathVariable("userId") String userId) {
		ArrayList<ChattingRoomDto> chattingRoomList = service.getChattingRoomList(userId);

		return SuccessResponse.createSuccess(SuccessCode.LOAD_ROOM_LIST_SUCCESS, chattingRoomList);
	}
	
	@GetMapping
	public ResponseEntity<Object> GetChattingList(@RequestParam Map<String, String> map) {
		ArrayList<ChattingDto> chattingList = service.getChattingList(map);

		return SuccessResponse.createSuccess(SuccessCode.LOAD_CHATTING_LIST_SUCCESS, chattingList);
	}
	
	@DeleteMapping
	public ResponseEntity<Object> deleteChattingRoom(@RequestParam Map<String, Object> map) {
		service.deleteChattingRoom(map);

		return SuccessResponse.createSuccess(SuccessCode.DELETE_CHATTING_ROOM_SUCCESS);
	}
	
	@DeleteMapping("/invitations")
	public ResponseEntity<Object> deleteChattingRoomById(@RequestParam("participantId") String participantId) {
		service.deleteChattingRoomById(participantId);

		return SuccessResponse.createSuccess(SuccessCode.DELETE_ROOM_INVITATION_SUCCESS);
	}

	@PatchMapping
	public ResponseEntity<Object> updateChattingRoom(@RequestParam("participantId") String participantId) {
		service.updateParticipantRoomById(participantId);

		return SuccessResponse.createSuccess(SuccessCode.ACCEPTED_ROOM_INVITATION_SUCCESS);
	}
	
	@GetMapping("/invitations")
	public ResponseEntity<Object> getInvitationsRoom(@RequestParam("userId") String userId) {
		ArrayList<InvitationDto> invitation = service.getInvitation(userId);

		return SuccessResponse.createSuccess(SuccessCode.LOAD_ROOM_INVITATION_SUCCESS, invitation);
	}
}
