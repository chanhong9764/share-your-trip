package edu.ssafy.enjoytrip.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.ssafy.enjoytrip.dto.chat.*;
import org.apache.ibatis.annotations.Mapper;

import edu.ssafy.enjoytrip.dto.user.User;

@Mapper
public interface ChatMapper {
	// 채팅방 관련 기능들
	List<ChattingRoom> getChattingRoomList(String userId);
	void createChattingRoom(ChattingRoomDto.ChattingRoomCreateRequestDto chattingRoomDto);
	int deleteChattingRoom(ChattingDto.DeleteChattingRequest requestDto);
	
	// 채팅방 참여 기능
	void createParticipantRoom(ChattingParticipant chattingParticipant);
	int updateParticipantRoom(ChattingParticipant chattingParticipant);
	int updateParticipantRoomById(String participantId);
	List<User> getParticipant(int roomId);
	List<Invitation> getInvitation(String userId);
	int deleteChattingRoomById(String participantId);
	Invitation getInvitationById(ChattingParticipant chattingParticipant);
	
	// 채팅 관련 기능
	void createChatting(Chatting chatting);
	ArrayList<ChattingDto> getChattingList(ChattingDto.ChattingListRequest requestDto);
}
