package edu.ssafy.enjoytrip.dto.board;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias("boardResponseDto")
public class BoardResponseDto {
	private int articleNo;
	private String userId;
	private String profile;
	private String content;
	private int recommendCnt;
	private String createdAt;
	private List<HashTagDto> hashtag;
	private List<String> imageURL;
	private boolean isRecommend;
}
