package edu.ssafy.enjoytrip.service.chat;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import edu.ssafy.enjoytrip.dto.chat.ChattingDto;
import edu.ssafy.enjoytrip.dto.chat.ChattingParticipantDto;
import edu.ssafy.enjoytrip.dto.chat.ChattingRoomDto;
import edu.ssafy.enjoytrip.dto.chat.InvitationDto;
import edu.ssafy.enjoytrip.dto.user.UserDto;

public interface ChatService {
	ArrayList<ChattingRoomDto> getChattingRoomList(String userId);

	void createChattingRoom(ChattingRoomDto chattingRoomDto) throws Exception;

	void deleteChattingRoom(Map<String, Object> map);

	// 채팅방 참여 기능
	void createParticipantRoom(ChattingParticipantDto chattingParticipantDto) throws Exception;
	void updateParticipantRoomById(String participantId);
	int updateParticipantRoom(Map<String, Object> map) throws Exception;
	ArrayList<InvitationDto> getInvitation(String userId);
	void deleteChattingRoomById(String participantId);
	InvitationDto getInvitationById(Map<String, Object> map) throws Exception;
	
	// 채팅 관련 기능
	void createChatting(ChattingDto chattingDto) throws Exception;
	ArrayList<ChattingDto> getChattingList(Map<String, String> map);
}
