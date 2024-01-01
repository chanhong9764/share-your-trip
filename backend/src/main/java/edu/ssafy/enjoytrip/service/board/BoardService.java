package edu.ssafy.enjoytrip.service.board;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import edu.ssafy.enjoytrip.dto.board.BoardDto;
import edu.ssafy.enjoytrip.dto.board.BoardResponseDto;
import edu.ssafy.enjoytrip.dto.board.HashTagDto;
import edu.ssafy.enjoytrip.dto.comment.CommentDto;
import edu.ssafy.enjoytrip.util.PageNavigation;

public interface BoardService {

	void writeArticle(BoardDto boardDto, String[] hashtag) throws Exception;
	List<BoardResponseDto> listArticle(Map<String, String> map) throws Exception;
	BoardResponseDto getArticle(Map<String, Object> map) throws Exception;

	void modifyArticle(BoardDto boardDto, String[] addHashtag, String[] removeHashtag, String[] removeImages) throws Exception;
	void deleteArticle(int articleNo) throws Exception;
	
	int setRecommend(Map<String, Object> map) throws SQLException;
	int delRecommend(Map<String, Object> map) throws SQLException;
	int getRecommendUser(int articleNo, String userId) throws SQLException;
	
	boolean updateBoard() throws SQLException;
	
	List<HashTagDto> getHotHashTag() throws Exception;
}

