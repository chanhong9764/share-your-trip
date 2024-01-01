package edu.ssafy.enjoytrip.dto.board;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias("hashTagDto")
public class HashTagDto {
	private int hashtagId;
	private String name;
}
