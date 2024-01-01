package edu.ssafy.enjoytrip.service.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.ssafy.enjoytrip.dto.chat.ChattingDto;
import edu.ssafy.enjoytrip.dto.chat.ChattingParticipantDto;
import edu.ssafy.enjoytrip.dto.chat.ChattingRoomDto;
import edu.ssafy.enjoytrip.dto.chat.InvitationDto;
import edu.ssafy.enjoytrip.dto.user.UserDto;
import edu.ssafy.enjoytrip.mapper.ChatMapper;
import edu.ssafy.enjoytrip.mapper.UserMapper;
import edu.ssafy.enjoytrip.util.SizeConstant;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
	private final ChatMapper chatMapper;
	private final UserMapper userMapper;
	@Override
	public ArrayList<ChattingRoomDto> getChattingRoomList(String userId) throws Exception {
		ArrayList<ChattingRoomDto> chattingRoomList = chatMapper.getChattingRoomList(userId);
		Map<String, Object> map = new HashMap<>();
		for(ChattingRoomDto chatRoom : chattingRoomList) {
			map.put("roomId", chatRoom.getRoomId());
			List<String> profiles = new ArrayList<>();
			ArrayList<UserDto> participants = chatMapper.getParticipant(map);
			for(UserDto participant : participants) {
				profiles.add(participant.getProfile());
			}
			chatRoom.setProfiles(profiles);
		}
		return chattingRoomList;
	}
	
	@Override
	public void createChattingRoom(ChattingRoomDto chattingRoomDto) throws Exception {
		chatMapper.createChattingRoom(chattingRoomDto);
		String[] participants = chattingRoomDto.getIdentifier().split(",");
		ChattingParticipantDto participantDto = new ChattingParticipantDto(); 
		System.out.println(participants);
		participantDto.setIsAccepted(0);
		participantDto.setRoomId(chattingRoomDto.getRoomId());
		for(String participant : participants) {
			participantDto.setUserId(participant);
			chatMapper.createParticipantRoom(participantDto);
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("roomId", chattingRoomDto.getRoomId());
		map.put("userId", participants[0]);
		chatMapper.updateParticipantRoom(map);
	}
	@Override
	public int deleteChattingRoom(Map<String, Object> map) throws Exception {
		return chatMapper.deleteChattingRoom(map);
	}
	@Override
	public ChattingRoomDto getChattingRoom(int roomId) throws Exception {
		return chatMapper.getChattingRoom(roomId);
	}
	
	@Override
	public void createParticipantRoom(ChattingParticipantDto chattingParticipantDto) throws Exception {
		chatMapper.createParticipantRoom(chattingParticipantDto);
	}
	
	@Override
	public int updateParticipantRoom(Map<String, Object> map) throws Exception {
		return chatMapper.updateParticipantRoom(map);
	}
	
	@Override
	public void createChatting(ChattingDto chattingDto) throws Exception {
		chatMapper.createChatting(chattingDto);
	}

	@Override
	public ArrayList<ChattingDto> getChattingList(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		int pgno = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgno * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		param.put("roomId", map.get("roomId"));

		return chatMapper.getChattingList(param);
	}
	
	@Override
	public ArrayList<InvitationDto> getInvitation(String userId) throws Exception {
		return chatMapper.getInvitation(userId);
	}
	
	@Override
	public void updateParticipantRoomById(String participantId) throws Exception {
		chatMapper.updateParticipantRoomById(participantId);
	}
	
	@Override
	public void deleteChattingRoomById(String participantId) throws Exception {
		chatMapper.deleteChattingRoomById(participantId);
	}
	
	@Override
	public InvitationDto getInvitationById(Map<String, Object> map) throws Exception {
		return chatMapper.getInvitationById(map);
	}
	
	@Override
	public ArrayList<ChattingDto> getChattingListRange(Map<String, Object> map) throws Exception {
		ArrayList<ChattingDto> list = new ArrayList<ChattingDto>();
		Integer chattingId = chatMapper.getChattingId(map);
		if(chattingId != null) {
			int endId = (Math.round((chattingId / 10)) * 10);
			System.out.println(map.get("startId")+ " "+ endId);
			map.put("endId", endId);
			list.addAll(chatMapper.getChattingListRange(map));
		}
		return list;
	}
}
