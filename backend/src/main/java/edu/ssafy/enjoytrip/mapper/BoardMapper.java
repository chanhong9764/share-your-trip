package edu.ssafy.enjoytrip.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import edu.ssafy.enjoytrip.dto.board.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
	void writeArticle(BoardDto.WriteRequest requestDto);
	List<BoardResponseDto> listArticle(BoardDto.ListRequest requestDto);
	List<BoardResponseDto> listArticleByHashtag(BoardDto.ListRequest requestDto);
	BoardResponseDto getArticle(int articleNo);
	
	int modifyArticle(BoardDto.ModifyRequest requestDto);
	int deleteArticle(int articleNo);
	
	// 게시글 추천
	void setRecommend(BoardDto.RecommendRequest requestDto);
	int delRecommend(BoardDto.RecommendRequest requestDto);
	int getRecommendUser(BoardDto.PostRequest requestDto);

	// 이미지 처리
	void registerFile(BoardDto.WriteRequest requestDto);
	List<BoardImagesDto> getBoardImagesByArticleNo(int articleNo);
	int removeImages(String image) ;
	
	// 해시태그
	Optional<Integer> isExistHashTag(String tag);
	void createHashTag(HashTagDto hashTagDto);
	void createRelationHashTag(BoardTagDto boardTagDto);
	List<HashTagDto> getHashTagByArticleNo(int articleNo);
	int removeHashTag(String tag);
	List<HashTagDto> getHotHashTag();
}
