package edu.ssafy.enjoytrip.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.ssafy.enjoytrip.dto.board.BoardDto;
import edu.ssafy.enjoytrip.dto.board.BoardImagesDto;
import edu.ssafy.enjoytrip.dto.board.BoardResponseDto;
import edu.ssafy.enjoytrip.dto.board.HashTagDto;

@Mapper
public interface BoardMapper {
	void writeArticle(BoardDto boardDto) throws SQLException;
	List<BoardResponseDto> listArticle(Map<String, Object> map) throws SQLException;
	List<BoardResponseDto> listArticleByHashtag(Map<String, Object> map) throws SQLException;
	BoardResponseDto getArticle(int articleNo) throws SQLException;
	
	void modifyArticle(BoardDto BoardDto) throws SQLException;
	void deleteArticle(int articleNo) throws SQLException;
	
	// 게시글 추천
	int setRecommend(Map<String, Object> map) throws SQLException;
	int delRecommend(Map<String, Object> map) throws SQLException;
	int getRecommendUser(Map<String, Object> map) throws SQLException;
	
	// 인기 게시글 
	boolean deleteHotBoard() throws SQLException;
	
	// 이미지 처리
	void registerFile(BoardDto boardDto) throws Exception;
	List<BoardImagesDto> getBoardImagesByArticleNo(int articleNo) throws Exception;
	void removeImages(String image) throws Exception;
	
	// 해시태그
	Integer isExistHashTag(String tag) throws SQLException;
	int createHashTag(HashTagDto hashTagDto) throws SQLException;
	void createRelationHashTag(Map<String, Integer> map) throws SQLException;
	int getHashTagId(String hashtag) throws SQLException;
	List<HashTagDto> getHashTagByArticleNo(int articleNo) throws SQLException;
	void removeHashTag(String tag) throws Exception;
	List<HashTagDto> getHotHashTag() throws Exception;
}
