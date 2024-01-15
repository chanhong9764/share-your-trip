package edu.ssafy.enjoytrip.dto.chat;

import edu.ssafy.enjoytrip.dto.trip.Trip;
import lombok.*;
import org.apache.ibatis.type.Alias;

public class ChattingDto {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class ChattingRequestDto {
		private String message;
		private String userId;
		private String profile;
		private int roomId;

		@Builder
		public ChattingRequestDto(String message, String profile, String userId, int roomId) {
			this.message = message;
			this.profile = profile;
			this.userId = userId;
			this.roomId = roomId;
		}
		public Chatting toEntity() {
			return Chatting.builder()
					.message(message)
					.userId(userId)
					.roomId(roomId)
					.build();
		}
	}
}
