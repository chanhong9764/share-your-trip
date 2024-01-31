package edu.ssafy.enjoytrip.service.board;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import edu.ssafy.enjoytrip.dto.board.BoardDto;
import edu.ssafy.enjoytrip.dto.board.BoardResponseDto;
import edu.ssafy.enjoytrip.dto.board.HashTagDto;

public interface BoardService {

	void writeArticle(BoardDto.WriteRequest requestDto);
	List<BoardResponseDto> listArticle(BoardDto.ListRequest requestDto);
	BoardResponseDto getArticle(BoardDto.PostRequest requestDto);

	void modifyArticle(BoardDto.ModifyRequest requestDto);
	void deleteArticle(int articleNo) ;
	
	void setRecommend(BoardDto.RecommendRequest requestDto);
	void delRecommend(BoardDto.RecommendRequest requestDto);

	List<HashTagDto> getHotHashTag();
}

