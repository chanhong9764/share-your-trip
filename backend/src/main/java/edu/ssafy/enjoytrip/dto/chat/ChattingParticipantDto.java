package edu.ssafy.enjoytrip.dto.chat;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias("chattingParticipantDto")
public class ChattingParticipantDto {
	private int participantId;
	private int isAccepted;
	private int roomId;
	private String userId;
}
