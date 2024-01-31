package edu.ssafy.enjoytrip.controller.trip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ssafy.enjoytrip.dto.chat.ChattingParticipantDto;
import edu.ssafy.enjoytrip.dto.chat.ChattingRoom;
import edu.ssafy.enjoytrip.dto.trip.TripDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import edu.ssafy.enjoytrip.dto.chat.ChattingDto;
import edu.ssafy.enjoytrip.dto.chat.ChattingRoomDto;
import edu.ssafy.enjoytrip.service.chat.ChatService;
import edu.ssafy.enjoytrip.service.trip.TripService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SocketController {
	private final ChatService service;
	private final TripService tripService;
	private final SimpMessagingTemplate messagingTemplate;
	
    @MessageMapping("/chat/message")
    public void CreateChatting(ChattingDto.CreateRequest requestDto) {
    	service.createChatting(requestDto);
    	messagingTemplate.convertAndSend("/sub/chat/" + requestDto.getRoomId(), requestDto);
    }
    
    @MessageMapping("/createRoom")
    public void CreateRoom(ChattingRoomDto.ChattingRoomCreateRequestDto requestDto) {
    	service.createChattingRoom(requestDto);

    	String[] users = requestDto.getIdentifier().split(",");

		ChattingParticipantDto.ChattingParticipantCreateDto participantDto = ChattingParticipantDto.ChattingParticipantCreateDto.builder()
				.roomId(requestDto.getRoomId())
				.build();

    	for(int i = 1; i < users.length; i++) {
			participantDto.updateUserId(users[i]);
    		messagingTemplate.convertAndSend("/sub/" + users[i], service.getInvitationById(participantDto));
    	}
    	messagingTemplate.convertAndSend("/sub/update/" + users[0], "업데이트하세요");
    }
    
    @MessageMapping("/enterRoom")
    public void EnterRoom(ChattingRoomDto.EnterRoomDto requestDto) {
    	messagingTemplate.convertAndSend("/sub/trip/" + requestDto.getRoomId(), requestDto);
    }
    
    @MessageMapping("/insertTrip")
    public void UpdateTrip(TripDto.TripInfoDTO requestDto) {
    	tripService.insertTrip(requestDto);
    	messagingTemplate.convertAndSend("/sub/select/" + requestDto.getRoomId(), requestDto);
    }
    
    @MessageMapping("/deleteTrip")
    public void DeleteTrip(TripDto.DeleteDto requestDto){
    	tripService.deleteTrip(requestDto);
    	messagingTemplate.convertAndSend("/sub/remove/" + requestDto.getRoomId(), requestDto);
    }
    
    @MessageMapping("/updateSequence")
    public void UpdateSequence(List<TripDto.TripInfoDTO> requestDtoList){
    	tripService.updateSelectedList(requestDtoList);
    	messagingTemplate.convertAndSend("/sub/sequenceUpdate/" + requestDtoList.get(0).getRoomId(), requestDtoList);
    }
}
