package edu.ssafy.enjoytrip.dto.chat;

import edu.ssafy.enjoytrip.dto.user.UserDto;
import lombok.*;
import org.apache.ibatis.type.Alias;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Alias("invitation")
public class Invitation {
	private String identifier;
	private int participantId;
	@Builder
	public Invitation(String identifier, int participantId) {
		this.identifier = identifier;
		this.participantId = participantId;
	}

	public InvitationDto.InvitationResponseDto toInvitationResponse() {
		return InvitationDto.InvitationResponseDto.builder()
				.identifier(identifier)
				.participantId(participantId)
				.build();
	}
}
