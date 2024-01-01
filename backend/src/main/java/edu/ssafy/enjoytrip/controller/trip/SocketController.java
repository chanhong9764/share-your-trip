package edu.ssafy.enjoytrip.controller.trip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import edu.ssafy.enjoytrip.dto.chat.ChattingDto;
import edu.ssafy.enjoytrip.dto.chat.ChattingRoomDto;
import edu.ssafy.enjoytrip.dto.chat.EnterRoomDto;
import edu.ssafy.enjoytrip.dto.trip.TripDto;
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
    public void SocketHandler(ChattingDto chattingDto) throws Exception {
    	service.createChatting(chattingDto);
    	messagingTemplate.convertAndSend("/sub/chat/" + chattingDto.getRoomId(), chattingDto);
    }
    
    @MessageMapping("/createRoom")
    public void CreateRoom(ChattingRoomDto chattingRoom) throws Exception {
    	service.createChattingRoom(chattingRoom);
    	String[] users = chattingRoom.getIdentifier().split(",");
    	Map<String, Object> map = new HashMap<>();
    	map.put("roomId", chattingRoom.getRoomId());
    	for(int i = 1; i < users.length; i++) {
    		map.put("userId", users[i]);
    		messagingTemplate.convertAndSend("/sub/" + users[i], service.getInvitationById(map));
    	}
    	messagingTemplate.convertAndSend("/sub/update/" + users[0], "업데이트하세요");
    }
    
    @MessageMapping("/enterRoom")
    public void EnterRoom(EnterRoomDto roominfo) throws Exception {
    	messagingTemplate.convertAndSend("/sub/trip/" + roominfo.getRoomId(), roominfo);
    }
    
    @MessageMapping("/selectTrip")
    public void updateTrip(TripDto tripDto) throws Exception {
    	System.out.println(tripDto);
    	tripService.SelectTrip(tripDto);
    	messagingTemplate.convertAndSend("/sub/select/" + tripDto.getRoomId(), tripDto);
    }
    
    @MessageMapping("/deleteTrip")
    public void deleteTrip(TripDto tripDto) throws Exception {
    	tripService.deleteTrip(String.valueOf(tripDto.getTrip_info_id()));
    	messagingTemplate.convertAndSend("/sub/remove/" + tripDto.getRoomId(), tripDto);
    }
    
    @MessageMapping("/updateSequence")
    public void updateSequence(ArrayList<TripDto> selectedList) throws Exception {
    	tripService.updateSelectedList(selectedList);
    	messagingTemplate.convertAndSend("/sub/sequenceUpdate/" + selectedList.get(0).getRoomId(), selectedList);
    }
}
