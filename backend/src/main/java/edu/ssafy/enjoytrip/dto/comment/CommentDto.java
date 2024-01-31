package edu.ssafy.enjoytrip.dto.comment;

import lombok.*;
import org.apache.ibatis.type.Alias;

public class CommentDto {
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@Getter
	public static class CommentResponseDto{
		private int commentId;
		private String userId;
		private int articleNo;
		private String content;
		private String createdAt;
		private String profile;
		@Builder
		public CommentResponseDto(int commentId, String userId, int articleNo, String content, String createdAt, String profile) {
			this.commentId = commentId;
			this.userId = userId;
			this.articleNo = articleNo;
			this.content = content;
			this.createdAt = createdAt;
			this.profile = profile;
		}
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@Getter
	public static class CommentRequestDto{
		private String userId;
		private int articleNo;
		private String content;

		public Comment toEntity() {
			return Comment.builder()
					.userId(userId)
					.articleNo(articleNo)
					.content(content)
					.build();
		}
	}
}
