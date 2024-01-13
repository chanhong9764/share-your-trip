package edu.ssafy.enjoytrip.service.board;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import edu.ssafy.enjoytrip.dto.board.BoardDto;
import edu.ssafy.enjoytrip.dto.board.BoardResponseDto;
import edu.ssafy.enjoytrip.dto.board.HashTagDto;

public interface BoardService {

	void writeArticle(BoardDto boardDto, String[] hashtag);
	List<BoardResponseDto> listArticle(Map<String, String> map);
	BoardResponseDto getArticle(BoardDto dto);

	void modifyArticle(BoardDto boardDto, String[] addHashtag, String[] removeHashtag, String[] removeImages);
	void deleteArticle(int articleNo) ;
	
	void setRecommend(BoardDto boardDto);
	void delRecommend(BoardDto boardDto);

	List<HashTagDto> getHotHashTag();
}

