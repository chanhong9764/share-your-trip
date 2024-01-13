package edu.ssafy.enjoytrip.service.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ssafy.enjoytrip.response.code.CustomResponseCode;
import edu.ssafy.enjoytrip.response.exception.RestApiException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ssafy.enjoytrip.dto.chat.ChattingDto;
import edu.ssafy.enjoytrip.dto.chat.ChattingParticipantDto;
import edu.ssafy.enjoytrip.dto.chat.ChattingRoomDto;
import edu.ssafy.enjoytrip.dto.chat.InvitationDto;
import edu.ssafy.enjoytrip.dto.user.UserDto;
import edu.ssafy.enjoytrip.mapper.ChatMapper;
import edu.ssafy.enjoytrip.util.SizeConstant;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
	private final ChatMapper chatMapper;

	@Override
	public ArrayList<ChattingRoomDto> getChattingRoomList(String userId) {
		ArrayList<ChattingRoomDto> chattingRoomList = chatMapper.getChattingRoomList(userId);
		if(chattingRoomList == null || chattingRoomList.isEmpty()) {
			throw new RestApiException(CustomResponseCode.CHATTING_ROOM_NOT_FOUND);
		}
		for(ChattingRoomDto chatRoom : chattingRoomList) {
			List<String> profiles = new ArrayList<>();
			ArrayList<UserDto> participants = chatMapper.getParticipant(chatRoom.getRoomId());
			if(participants == null || participants.isEmpty()) {
				throw new RestApiException(CustomResponseCode.PARTICIPANT_NOT_FOUND);
			}
			for(UserDto participant : participants) {
				profiles.add(participant.getProfile());
			}
			chatRoom.setProfiles(profiles);
		}
		return chattingRoomList;
	}
	
	@Override
	public void createChattingRoom(ChattingRoomDto chattingRoomDto) {
		try {
			chatMapper.createChattingRoom(chattingRoomDto);
		} catch (DataIntegrityViolationException e) {
			throw new RestApiException(CustomResponseCode.CHATTING_ROOM_NOT_CREATED);
		}
		String[] participants = chattingRoomDto.getIdentifier().split(",");
		ChattingParticipantDto participantDto = new ChattingParticipantDto();

		participantDto.setIsAccepted(0);
		participantDto.setRoomId(chattingRoomDto.getRoomId());

		for(String participant : participants) {
			participantDto.setUserId(participant);
			try {
				chatMapper.createParticipantRoom(participantDto);
			} catch (DataIntegrityViolationException e) {
				throw new RestApiException(CustomResponseCode.PARTICIPANT_NOT_ENTERED);
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("roomId", chattingRoomDto.getRoomId());
		map.put("userId", participants[0]);

		int cnt = chatMapper.updateParticipantRoom(map);
		if(cnt == 0) {
			throw new RestApiException(CustomResponseCode.PARTICIPANT_NOT_ENTERED);
		}
	}
	@Override
	public void deleteChattingRoom(Map<String, Object> map) {
		int cnt = chatMapper.deleteChattingRoom(map);
		if(cnt == 0) {
			throw new RestApiException(CustomResponseCode.CHATTING_ROOM_NOT_FOUND);
		}
	}
	
	@Override
	public void createChatting(ChattingDto chattingDto){
		try {
			chatMapper.createChatting(chattingDto);
		} catch (DataIntegrityViolationException e) {
			throw new RestApiException(CustomResponseCode.CHATTING_NOT_CREATED);
		}
	}

	@Override
	public ArrayList<ChattingDto> getChattingList(Map<String, String> map) {
		Map<String, Object> param = new HashMap<>();
		int pgno = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgno * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		param.put("roomId", map.get("roomId"));
		ArrayList<ChattingDto> chattingList = chatMapper.getChattingList(param);

		if(chattingList == null || chattingList.isEmpty()) {
			throw new RestApiException(CustomResponseCode.CHATTING_LIST_NOT_FOUND);
		}

		return chattingList;
	}
	
	@Override
	public ArrayList<InvitationDto> getInvitation(String userId){
		ArrayList<InvitationDto> invitation = chatMapper.getInvitation(userId);
		if(invitation == null || invitation.isEmpty()) {
			throw new RestApiException(CustomResponseCode.ROOM_INVITATION_NOT_FOUND);
		}
		return invitation;
	}
	
	@Override
	public void updateParticipantRoomById(String participantId){
		int cnt = chatMapper.updateParticipantRoomById(participantId);
		if(cnt == 0) {
			throw new RestApiException(CustomResponseCode.PARTICIPANT_NOT_FOUND);
		}
	}
	
	@Override
	public void deleteChattingRoomById(String participantId) {
		int cnt = chatMapper.deleteChattingRoomById(participantId);
		if(cnt == 0) {
			throw new RestApiException(CustomResponseCode.PARTICIPANT_NOT_FOUND);
		}
	}
	
	@Override
	public InvitationDto getInvitationById(Map<String, Object> map) {
		InvitationDto invitation = chatMapper.getInvitationById(map);
		if(invitation == null) {
			throw new RestApiException(CustomResponseCode.ROOM_INVITATION_NOT_FOUND);
		}
		return invitation;
	}
}
