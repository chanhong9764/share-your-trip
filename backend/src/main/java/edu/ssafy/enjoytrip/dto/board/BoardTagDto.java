package edu.ssafy.enjoytrip.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardTagDto {
	private int boardTagId;
	private int articleNo;
	private int hashTagId;
}
