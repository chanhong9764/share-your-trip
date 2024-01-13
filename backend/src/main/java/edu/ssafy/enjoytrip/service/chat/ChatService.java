package edu.ssafy.enjoytrip.service.chat;

import java.util.ArrayList;
import java.util.Map;

import edu.ssafy.enjoytrip.dto.chat.ChattingDto;
import edu.ssafy.enjoytrip.dto.chat.ChattingRoomDto;
import edu.ssafy.enjoytrip.dto.chat.InvitationDto;

public interface ChatService {
	ArrayList<ChattingRoomDto> getChattingRoomList(String userId);
	void createChattingRoom(ChattingRoomDto chattingRoomDto);
	void deleteChattingRoom(Map<String, Object> map);
	// 채팅방 참여 기능
	void updateParticipantRoomById(String participantId);
	ArrayList<InvitationDto> getInvitation(String userId);
	void deleteChattingRoomById(String participantId);
	InvitationDto getInvitationById(Map<String, Object> map);
	
	// 채팅 관련 기능
	void createChatting(ChattingDto chattingDto);
	ArrayList<ChattingDto> getChattingList(Map<String, String> map);
}
