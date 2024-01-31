package edu.ssafy.enjoytrip.service.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.ssafy.enjoytrip.dto.chat.*;
import edu.ssafy.enjoytrip.response.code.CustomResponseCode;
import edu.ssafy.enjoytrip.response.exception.RestApiException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ssafy.enjoytrip.dto.user.User;
import edu.ssafy.enjoytrip.mapper.ChatMapper;
import edu.ssafy.enjoytrip.util.SizeConstant;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
	private final ChatMapper chatMapper;

	@Override
	public List<ChattingRoomDto.ChattingRoomResponseDto> getChattingRoomList(String userId) {
		List<ChattingRoom> chattingRoomList = chatMapper.getChattingRoomList(userId);
		if(chattingRoomList == null || chattingRoomList.isEmpty()) {
			throw new RestApiException(CustomResponseCode.CHATTING_ROOM_NOT_FOUND);
		}
		List<ChattingRoomDto.ChattingRoomResponseDto> chattingRoomDtoList = chattingRoomList.stream()
				.map(ChattingRoom::tochattingRoomResponseDto)
				.collect(Collectors.toList());
		for(ChattingRoomDto.ChattingRoomResponseDto chatRoom : chattingRoomDtoList) {
			List<String> profiles = new ArrayList<>();
			List<User> participants = chatMapper.getParticipant(chatRoom.getRoomId());

			if(participants == null || participants.isEmpty()) {
				throw new RestApiException(CustomResponseCode.PARTICIPANT_NOT_FOUND);
			}
			for(User participant : participants) {
				profiles.add(participant.getProfile());
			}
			chatRoom.updateProfiles(profiles);
		}
		return chattingRoomDtoList;
	}
	
	@Override
	public void createChattingRoom(ChattingRoomDto.ChattingRoomCreateRequestDto requestDto) {
		try {
			chatMapper.createChattingRoom(requestDto);
		} catch (DataIntegrityViolationException e) {
			throw new RestApiException(CustomResponseCode.CHATTING_ROOM_NOT_CREATED);
		}
		String[] participants = requestDto.getIdentifier().split(",");
		ChattingParticipantDto.ChattingParticipantCreateDto participantDto = ChattingParticipantDto.ChattingParticipantCreateDto.builder()
				.roomId(requestDto.getRoomId())
				.build();

		for(String participant : participants) {
			participantDto.updateUserId(participant);
			try {
				chatMapper.createParticipantRoom(participantDto.toEntity());
			} catch (DataIntegrityViolationException e) {
				throw new RestApiException(CustomResponseCode.PARTICIPANT_NOT_ENTERED);
			}
		}

		participantDto.updateUserId(participants[0]);
		int cnt = chatMapper.updateParticipantRoom(participantDto.toEntity());
		if(cnt == 0) {
			throw new RestApiException(CustomResponseCode.PARTICIPANT_NOT_ENTERED);
		}
	}
	@Override
	public void deleteChattingRoom(ChattingDto.DeleteChattingRequest requestDto) {
		int cnt = chatMapper.deleteChattingRoom(requestDto);
		if(cnt == 0) {
			throw new RestApiException(CustomResponseCode.CHATTING_ROOM_NOT_FOUND);
		}
	}
	
	@Override
	public void createChatting(ChattingDto.CreateRequest requestDto){
		try {
			chatMapper.createChatting(requestDto.toEntity());
		} catch (DataIntegrityViolationException e) {
			throw new RestApiException(CustomResponseCode.CHATTING_NOT_CREATED);
		}
	}

	@Override
	public ArrayList<ChattingDto> getChattingList(ChattingDto.ChattingListRequest requestDto) {
		ArrayList<ChattingDto> chattingList = chatMapper.getChattingList(requestDto);

		if(chattingList == null || chattingList.isEmpty()) {
			throw new RestApiException(CustomResponseCode.CHATTING_LIST_NOT_FOUND);
		}

		return chattingList;
	}
	
	@Override
	public List<Invitation> getInvitation(String userId){
		List<Invitation> invitation = chatMapper.getInvitation(userId);
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
	public InvitationDto.InvitationResponseDto getInvitationById(ChattingParticipantDto.ChattingParticipantCreateDto participantCreateDto) {
		InvitationDto.InvitationResponseDto invitationResponseDto = chatMapper.getInvitationById(participantCreateDto.toEntity()).toInvitationResponse();
		if(invitationResponseDto == null) {
			throw new RestApiException(CustomResponseCode.ROOM_INVITATION_NOT_FOUND);
		}
		return invitationResponseDto;
	}
}
