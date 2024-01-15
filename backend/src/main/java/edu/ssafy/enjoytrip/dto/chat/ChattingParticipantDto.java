package edu.ssafy.enjoytrip.dto.chat;

import lombok.*;
import org.apache.ibatis.type.Alias;

public class ChattingParticipantDto {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@Getter
	public static class ChattingParticipantCreateDto {
		private int roomId;
		private String userId;
		@Builder
		public ChattingParticipantCreateDto(int roomId, String userId) {
			this.roomId = roomId;
			this.userId = userId;
		}

		public void updateUserId(String userId) {
			this.userId = userId;
		}

		public ChattingParticipant toEntity() {
			return ChattingParticipant.builder()
					.userId(userId)
					.roomId(roomId)
					.build();
		}
	}
}
