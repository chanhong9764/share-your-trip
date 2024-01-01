package edu.ssafy.enjoytrip.dto.chat;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias("chattingDto")
public class ChattingDto {
	private int chattingId;
	private String message;
	private String createdAt;
	private String profile;
	private String userId;
	private int roomId;
}
