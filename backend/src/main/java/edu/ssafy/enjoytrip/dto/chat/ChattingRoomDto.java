package edu.ssafy.enjoytrip.dto.chat;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias("chattingRoomDto")
public class ChattingRoomDto {
	private int roomId;
	private String identifier;
	private String createdAt;
	private String content;
	private String roomName;
	private List<String> profiles;
}
