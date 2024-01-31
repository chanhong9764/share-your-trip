package edu.ssafy.enjoytrip.dto.board;

import java.util.List;

import lombok.*;
import org.apache.ibatis.type.Alias;

public class BoardDto{
	@Getter
	@AllArgsConstructor
	public static class PostRequest {
		private int articleNo;
		private String userId;
	}

	@Getter
	@AllArgsConstructor
	public static class RecommendRequest {
		private int articleNo;
		private String userId;
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class WriteRequest {
		private int articleNo;
		private String userId;
		private String content;
		private String[] hashtag;
		private List<BoardImagesDto> images;
		@Builder
		public WriteRequest(int articleNo, String userId, String content, String[] hashtag, List<BoardImagesDto> images) {
			this.articleNo = articleNo;
			this.userId = userId;
			this.content = content;
			this.hashtag = hashtag;
			this.images = images;
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class ModifyRequest {
		private int articleNo;
		private String userId;
		private String content;
		private List<BoardImagesDto> images;
		private String[] addHashtag;
		private String[] removeHashtag;
		private String[] removeImages;
		@Builder
		public ModifyRequest(int articleNo, String userId, String content, List<BoardImagesDto> images, String[] addHashtag, String[] removeHashtag, String[] removeImages) {
			this.articleNo = articleNo;
			this.userId = userId;
			this.content = content;
			this.images = images;
			this.addHashtag = addHashtag;
			this.removeHashtag = removeHashtag;
			this.removeImages = removeImages;
		}
	}
	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class ListRequest {
		private int start;
		private int listSize;
		private String hashtag;
		private int type;
		private String userId;
		private String key;
		@Builder
		public ListRequest(int start, int listSize, String hashtag, int type, String userId, String key) {
			this.start = start;
			this.listSize = listSize;
			this.hashtag = hashtag;
			this.type = type;
			this.userId = userId;
			this.key = key;
		}

		public void updatePost() {
			this.start = 0;
			this.listSize = 3;
			this.type = 1;
		}
	}
}
