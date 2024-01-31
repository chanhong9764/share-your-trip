package edu.ssafy.enjoytrip.dto.chat;

import lombok.*;
public class ChattingDto {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class CreateRequest {
		private String message;
		private String userId;
		private String profile;
		private int roomId;

		@Builder
		public CreateRequest(String message, String profile, String userId, int roomId) {
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
	@Getter
	@AllArgsConstructor
	public static class ChattingListRequest {
		private int start;
		private int listSize;
		private int roomId;
	}

	@Getter
	@AllArgsConstructor
	public static class DeleteChattingRequest {
		private String userId;
		private int roomId;
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ChattingListResponse {
		private int chattingId;
		private String message;
		private String createdAt;
		private String userId;
		private int roomId;
		private String profile;
		@Builder
		public ChattingListResponse(int chattingId, String message, String createdAt, String userId, int roomId, String profile) {
			this.chattingId = chattingId;
			this.message = message;
			this.createdAt = createdAt;
			this.userId = userId;
			this.roomId = roomId;
			this.profile = profile;
		}
	}
}
