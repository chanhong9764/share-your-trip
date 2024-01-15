package edu.ssafy.enjoytrip.dto.chat;

import java.util.List;

import lombok.*;
import org.apache.ibatis.type.Alias;

public class ChattingRoomDto {
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@Getter
	public static class ChattingRoomCreateRequestDto {
		private String identifier;
		private String content;
		private String roomName;
		private int roomId;
	}
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@Getter
	public static class ChattingRoomResponseDto {
		private int roomId;
		private String identifier;
		private String createdAt;
		private String content;
		private String roomName;
		private List<String> profiles;
		@Builder
		public ChattingRoomResponseDto(int roomId, String identifier, String createdAt, String content, String roomName, List<String> profiles) {
			this.roomId = roomId;
			this.identifier = identifier;
			this.createdAt = createdAt;
			this.content = content;
			this.roomName = roomName;
			this.profiles = profiles;
		}

		public void updateProfiles(List<String> profiles) {
			this.profiles = profiles;
		}

	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@Getter
	public static class EnterRoomDto {
		private String roomId;
		private String userId;
	}
}
