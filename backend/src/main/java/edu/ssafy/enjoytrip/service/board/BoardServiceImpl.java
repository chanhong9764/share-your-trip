package edu.ssafy.enjoytrip.service.board;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ssafy.enjoytrip.dto.board.BoardDto;
import edu.ssafy.enjoytrip.dto.board.BoardImagesDto;
import edu.ssafy.enjoytrip.dto.board.BoardResponseDto;
import edu.ssafy.enjoytrip.dto.board.HashTagDto;
import edu.ssafy.enjoytrip.mapper.BoardMapper;
import edu.ssafy.enjoytrip.util.SizeConstant;

@Service("BoardServiceImpl")
public class BoardServiceImpl implements BoardService {
	private BoardMapper boardMapper;
	
	@Autowired
	private BoardServiceImpl(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}
	
	@Override
	public List<BoardResponseDto> listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		
//		if(map.get("start") != null) {
//		
//		}
		int pgno = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgno * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		
		if(map.get("key") != null && !map.get("key").isEmpty()){
			param.put("start", 0);
			param.put("listsize", 3);
			param.put("type", 1);
		} else {
			param.put("start", start);
			param.put("listsize", SizeConstant.LIST_SIZE);
			param.put("hashtag", map.get("hashtag"));
			param.put("type", map.get("type"));
		}
		List<BoardResponseDto> list = new ArrayList<BoardResponseDto>();
		
		if(param.get("hashtag") != null && !((String)param.get("hashtag")).isEmpty()) {
			list.addAll(boardMapper.listArticleByHashtag(param));
		} else {
			list.addAll(boardMapper.listArticle(param));
		}
		for(BoardResponseDto post : list) {
			if(map.get("userId") != null || !map.get("userId").equals("")) {
				Map<String, Object> temp = new HashMap<>();
				temp.put("articleNo", post.getArticleNo());
				temp.put("userId", map.get("userId"));
				Integer recommendUser = boardMapper.getRecommendUser(temp);
				if(recommendUser != 0) {
					post.setRecommend(true);
				}
			}
			List<HashTagDto> hashTagList = boardMapper.getHashTagByArticleNo(post.getArticleNo());
			
			List<BoardImagesDto> boardImages = boardMapper.getBoardImagesByArticleNo(post.getArticleNo());
			if(!boardImages.isEmpty()) {
				post.setImageURL(new ArrayList<String>());
				for(BoardImagesDto imageDto : boardImages) {
					String[] split = imageDto.getSaveFolder().split("/");
					String date = split[split.length - 1];
					String filePath = "/" + date + "/" + imageDto.getSaveFile();
			
					post.getImageURL().add(filePath);
				}
			}
			post.setHashtag(hashTagList);
		}
		return list;
	}
	
	
	@Override
	public BoardResponseDto getArticle(Map<String, Object> map) throws Exception {
		BoardResponseDto article = boardMapper.getArticle((int) map.get("articleNo"));
		Integer recommendUser = boardMapper.getRecommendUser(map);
		
		List<HashTagDto> hashTagList = boardMapper.getHashTagByArticleNo((int)map.get("articleNo"));
		article.setHashtag(hashTagList);
		
		if(recommendUser != 0) {
			article.setRecommend(true);
		}
		List<BoardImagesDto> boardImages = boardMapper.getBoardImagesByArticleNo((int) map.get("articleNo"));
		if(!boardImages.isEmpty()) {
			article.setImageURL(new ArrayList<String>());
			for(BoardImagesDto imageDto : boardImages) {
				String[] split = imageDto.getSaveFolder().split("/");
				String date = split[split.length - 1];
				String filePath = "/" + date + "/" + imageDto.getSaveFile();
		
				article.getImageURL().add(filePath);
			}
		}
		
		return article;
	}
	
	@Override
	public void writeArticle(BoardDto boardDto, String[] hashtag) throws Exception {
		// 게시글 처리
		boardMapper.writeArticle(boardDto);
		List<BoardImagesDto> images = boardDto.getImages();
		
		if(images != null && !images.isEmpty()) {
			boardMapper.registerFile(boardDto);
		}
		
		// 해시태그 처리
		Map<String, Integer> map = new HashMap<>();
		map.put("articleNo", boardDto.getArticleNo());
		HashTagDto hashTagDto = new HashTagDto();
		
		for(String tag : hashtag) {
			Integer existHashTag = boardMapper.isExistHashTag(tag);
			if(existHashTag == null) {
				hashTagDto.setName(tag);
				boardMapper.createHashTag(hashTagDto);
				map.put("hashtagId", hashTagDto.getHashtagId());
			} else {
				map.put("hashtagId", existHashTag);
			}
			boardMapper.createRelationHashTag(map);
		}
	}

	@Override
	public void modifyArticle(BoardDto boardDto, String[] addHashtag, String[] removeHashtag, String[] removeImages) throws Exception {
		// 게시글 업데이트 완료
		boardMapper.modifyArticle(boardDto);
		// 사진 업데이트
		List<BoardImagesDto> images = boardDto.getImages();
		
		if(images != null && !images.isEmpty()) {
			boardMapper.registerFile(boardDto);
		}
		// 기존 사진 제거
		if(removeImages != null) {
			for(String image : removeImages) {
				boardMapper.removeImages(image);
			}
		}
		
		// 해시태그 업데이트
		Map<String, Integer> map = new HashMap<>();
		map.put("articleNo", boardDto.getArticleNo());
		System.out.println(boardDto);
		HashTagDto hashTagDto = new HashTagDto();
		
		// 해시태그 추가
		if(addHashtag != null) {
			for(String tag : addHashtag) {
				Integer existHashTag = boardMapper.isExistHashTag(tag);
				if(existHashTag == null) {
					hashTagDto.setName(tag);
					boardMapper.createHashTag(hashTagDto);
					map.put("hashtagId", hashTagDto.getHashtagId());
				} else {
					map.put("hashtagId", existHashTag);
				}
				boardMapper.createRelationHashTag(map);
			}
		}
		// 기존 해시태그 제거
		if(removeHashtag != null) {
			for(String tag : removeHashtag) {
				boardMapper.removeHashTag(tag);
			}
		
		}
	}

	@Override
	public void deleteArticle(int articleNo) throws Exception {
		boardMapper.deleteArticle(articleNo);
	}

	@Override
	public int delRecommend(Map<String, Object> map) throws SQLException {
		return boardMapper.delRecommend(map);
	}
	@Override
	public int getRecommendUser(int articleNo, String userId) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articleNo", articleNo);
		map.put("userId", userId);
		return boardMapper.getRecommendUser(map);
	}
	@Override
	public int setRecommend(Map<String, Object> map) throws SQLException {
		return boardMapper.setRecommend(map);
	}
	
	@Override
	public List<HashTagDto> getHotHashTag() throws Exception {
		return boardMapper.getHotHashTag();
	}
	
	@Override
	public boolean updateBoard() throws SQLException {
//		List<BoardDto> list = boardMapper.readAllPost();
//		List<BoardDto> HotList = pickHotPost(list);
//		boardMapper.deleteHotBoard();
//		try {
//			System.out.println("HIhi");
//			for(BoardDto b : HotList) {
//				System.out.println(b);
//				boardMapper.updateHotBoard(b);
//			}
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
		return false;
	}
	
//	private List<BoardDto> pickHotPost(List<BoardDto> list) {
//		List<BoardDto> hotList = new ArrayList<BoardDto>();
//		
//		Collections.sort(list);
//		
//		//인기를 정렬하는 알고리즘 구현
//		for (int i = 0; i < 3; i++) {
//			hotList.add(list.get(i));
//		}
//		System.out.println(hotList);
//		return hotList;
//	}
}
