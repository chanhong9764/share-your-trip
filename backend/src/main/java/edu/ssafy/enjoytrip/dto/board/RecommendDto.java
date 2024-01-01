package edu.ssafy.enjoytrip.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecommendDto {
	private String recommendId;
	private String userId;
	private int articleNo;
	private String createdAt;
	
}
