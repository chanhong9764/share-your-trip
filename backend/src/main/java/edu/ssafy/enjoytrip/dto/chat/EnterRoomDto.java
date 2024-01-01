package edu.ssafy.enjoytrip.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EnterRoomDto {
	private String roomId;
	private String userId;
}
