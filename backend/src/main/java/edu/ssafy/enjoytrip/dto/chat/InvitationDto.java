package edu.ssafy.enjoytrip.dto.chat;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias("invitationDto")
public class InvitationDto {
	private String identifier;
	private int participantId;
}
