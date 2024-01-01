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
	ArrayList<ChattingRoomDto> getChattingRoomList(String userId) throws Exception;
	ChattingRoomDto getChattingRoom(int roomId) throws Exception;
	void createChattingRoom(ChattingRoomDto chattingRoomDto) throws Exception;
	int deleteChattingRoom(Map<String, Object> map) throws Exception;
	
	// 채팅방 참여 기능
	void createParticipantRoom(ChattingParticipantDto chattingParticipantDto) throws Exception;
	int updateParticipantRoom(Map<String, Object> map) throws Exception;
	void updateParticipantRoomById(String participantId) throws Exception;
	ArrayList<UserDto> getParticipant(Map<String, Object> map) throws Exception;
	ArrayList<InvitationDto> getInvitation(String userId) throws Exception;
	void deleteChattingRoomById(String participantId) throws Exception;
	InvitationDto getInvitationById(Map<String, Object> map) throws Exception;
	
	// 채팅 관련 기능
	void createChatting(ChattingDto chattingDto) throws Exception;
	ArrayList<ChattingDto> getChattingList(Map<String, Object> map) throws Exception;
	ArrayList<ChattingDto> getChattingListRange(Map<String, Object> map) throws Exception;
	Integer getChattingId(Map<String, Object> map) throws Exception;
}
