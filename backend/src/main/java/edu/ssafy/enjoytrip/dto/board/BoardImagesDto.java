package edu.ssafy.enjoytrip.dto.board;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias("imageDto")
public class BoardImagesDto {
	private int fildId;
	private String originalName;
	private int articleNo;
	private String saveFolder;
	private String saveFile;
}
