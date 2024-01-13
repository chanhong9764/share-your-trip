package edu.ssafy.enjoytrip.mapper;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import edu.ssafy.enjoytrip.dto.chat.ChattingDto;
import edu.ssafy.enjoytrip.dto.chat.ChattingParticipantDto;
import edu.ssafy.enjoytrip.dto.chat.ChattingRoomDto;
import edu.ssafy.enjoytrip.dto.chat.InvitationDto;
import edu.ssafy.enjoytrip.dto.user.UserDto;

@Mapper
public interface ChatMapper {
	// 채팅방 관련 기능들
	ArrayList<ChattingRoomDto> getChattingRoomList(String userId);
	void createChattingRoom(ChattingRoomDto chattingRoomDto);
	int deleteChattingRoom(Map<String, Object> map);
	
	// 채팅방 참여 기능
	void createParticipantRoom(ChattingParticipantDto chattingParticipantDto);
	int updateParticipantRoom(Map<String, Object> map);
	int updateParticipantRoomById(String participantId);
	ArrayList<UserDto> getParticipant(int roomId);
	ArrayList<InvitationDto> getInvitation(String userId);
	int deleteChattingRoomById(String participantId);
	InvitationDto getInvitationById(Map<String, Object> map);
	
	// 채팅 관련 기능
	void createChatting(ChattingDto chattingDto);
	ArrayList<ChattingDto> getChattingList(Map<String, Object> map);
}
