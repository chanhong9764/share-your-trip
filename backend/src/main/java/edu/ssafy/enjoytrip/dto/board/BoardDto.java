package edu.ssafy.enjoytrip.dto.board;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias("boardDto")
public class BoardDto{
	private int articleNo;
	private String userId;
	private String content;
	private String createdAt;
	private List<BoardImagesDto> images;
}
