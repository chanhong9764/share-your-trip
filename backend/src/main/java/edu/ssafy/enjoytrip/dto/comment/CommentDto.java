package edu.ssafy.enjoytrip.dto.comment;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias("commentDto")
public class CommentDto {
	private int commentId;
	private String userId;
	private int articleNo;
	private String content;
	private String createdAt;
	private String profile;
}
