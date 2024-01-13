package edu.ssafy.enjoytrip.service.board;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ssafy.enjoytrip.dto.board.*;
import edu.ssafy.enjoytrip.response.code.CustomResponseCode;
import edu.ssafy.enjoytrip.response.code.SuccessCode;
import edu.ssafy.enjoytrip.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ssafy.enjoytrip.mapper.BoardMapper;
import edu.ssafy.enjoytrip.util.SizeConstant;

@Service("BoardServiceImpl")
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	private final BoardMapper boardMapper;

	@Override
	public List<BoardResponseDto> listArticle(Map<String, String> map) {
		Map<String, Object> param = new HashMap<String, Object>();

		int pgno = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgno * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;

		// 메인 페이지 인기 게시글 리스트
		if (map.get("key") != null && !map.get("key").isEmpty()) {
			param.put("start", 0);
			param.put("listsize", 3);
			param.put("type", 1);
		} else {
			param.put("start", start);
			param.put("listsize", SizeConstant.LIST_SIZE);
			param.put("hashtag", map.get("hashtag"));
			param.put("type", map.get("type"));
		}
		List<BoardResponseDto> list;

		// 게시글 리스트 불러오는 부분
		if (param.get("hashtag") != null && !((String) param.get("hashtag")).isEmpty()) {
			list = boardMapper.listArticleByHashtag(param);
		} else {
			list = boardMapper.listArticle(param);
		}
		if (list == null || list.isEmpty()) {
			throw new RestApiException(CustomResponseCode.BOARD_LIST_NOT_FOUND);
		}

		for (BoardResponseDto post : list) {
			// 로그인 했을 경우
			if (map.get("userId") != null || !map.get("userId").equals("")) {
				BoardDto dto = new BoardDto();
				dto.setArticleNo(post.getArticleNo());
				dto.setUserId(map.get("userId"));
				// 게시글마다 추천 여부 확인
				int cnt = boardMapper.getRecommendUser(dto).orElse(0);
				if (cnt != 0) {
					post.setRecommend(true);
				}
			}
			// 게시글 마다의 해시태그 조회
			List<HashTagDto> hashTagList = boardMapper.getHashTagByArticleNo(post.getArticleNo());
			post.setHashtag(hashTagList);
			// 게시글 마다의 이미지 조회
			List<BoardImagesDto> boardImages = boardMapper.getBoardImagesByArticleNo(post.getArticleNo());

			if (boardImages != null && !boardImages.isEmpty()) {
				post.setImageURL(new ArrayList<>());
				for (BoardImagesDto imageDto : boardImages) {
					String[] split = imageDto.getSaveFolder().split("/");
					String date = split[split.length - 1];
					String filePath = "/" + date + "/" + imageDto.getSaveFile();

					post.getImageURL().add(filePath);
				}
			}
		}
		return list;
	}


	@Override
	public BoardResponseDto getArticle(BoardDto dto) {
		BoardResponseDto article = boardMapper.getArticle(dto.getArticleNo());
		// 게시글 추천 여부 확인
		int cnt = boardMapper.getRecommendUser(dto).orElse(0);
		if (cnt != 0) {
			article.setRecommend(true);
		}
		// 게시글의 해시태그
		List<HashTagDto> hashTagList = boardMapper.getHashTagByArticleNo(dto.getArticleNo());
		article.setHashtag(hashTagList);

		// 게시글의 이미지
		List<BoardImagesDto> boardImages = boardMapper.getBoardImagesByArticleNo(dto.getArticleNo());
		if (boardImages != null && !boardImages.isEmpty()) {
			article.setImageURL(new ArrayList<>());
			for (BoardImagesDto imageDto : boardImages) {
				String[] split = imageDto.getSaveFolder().split("/");
				String date = split[split.length - 1];
				String filePath = "/" + date + "/" + imageDto.getSaveFile();

				article.getImageURL().add(filePath);
			}
		}

		return article;
	}

	@Override
	public void writeArticle(BoardDto boardDto, String[] hashtag) {
		// 게시글 저장
		try {
			boardMapper.writeArticle(boardDto);
		} catch (DataIntegrityViolationException e) {
			throw new RestApiException(CustomResponseCode.POST_NOT_CREATED);
		}
		List<BoardImagesDto> images = boardDto.getImages();

		// 이미지 저장
		if (images != null && !images.isEmpty()) {
			try {
				boardMapper.registerFile(boardDto);
			} catch (DataIntegrityViolationException e) {
				throw new RestApiException(CustomResponseCode.IMAGE_NOT_CREATED);
			}
		}

		// 해시태그 처리
		BoardTagDto boardTagDto = new BoardTagDto();
		boardTagDto.setArticleNo(boardDto.getArticleNo());
		HashTagDto hashTagDto = new HashTagDto();

		for (String tag : hashtag) {
			int existHashTag = boardMapper.isExistHashTag(tag).orElse(0);
			if (existHashTag == 0) {
				hashTagDto.setName(tag);
				try {
					boardMapper.createHashTag(hashTagDto);
				} catch (DataIntegrityViolationException e) {
					throw new RestApiException(CustomResponseCode.HASHTAG_NOT_CREATED);
				}
				boardTagDto.setHashTagId(hashTagDto.getHashtagId());
			} else {
				boardTagDto.setHashTagId(existHashTag);
			}
			try {
				boardMapper.createRelationHashTag(boardTagDto);
			} catch (DataIntegrityViolationException e) {
				throw new RestApiException(CustomResponseCode.HASHTAG_POST_NOT_CREATED);
			}
		}
	}

	@Override
	public void modifyArticle(BoardDto boardDto, String[] addHashtag, String[] removeHashtag, String[] removeImages) {
		// 게시글 업데이트
		int cnt = boardMapper.modifyArticle(boardDto);
		if (cnt == 0) {
			throw new RestApiException(CustomResponseCode.POST_NOT_MODIFIED);
		}

		// 추가된 사진 삽입
		List<BoardImagesDto> images = boardDto.getImages();

		if (images != null && !images.isEmpty()) {
			try {
				boardMapper.registerFile(boardDto);
			} catch (DataIntegrityViolationException e) {
				throw new RestApiException(CustomResponseCode.IMAGE_NOT_CREATED);
			}
		}
		// 기존 사진 제거
		if (removeImages != null) {
			for (String image : removeImages) {
				int deleteCnt = boardMapper.removeImages(image);
				if (deleteCnt == 0) {
					throw new RestApiException(CustomResponseCode.IMAGE_NOT_DELETED);
				}
			}
		}

		// 해시태그 업데이트
		BoardTagDto boardTagDto = new BoardTagDto();
		boardTagDto.setArticleNo(boardDto.getArticleNo());
		HashTagDto hashTagDto = new HashTagDto();

		// 해시태그 추가
		if (addHashtag != null) {
			for (String tag : addHashtag) {
				int existHashTag = boardMapper.isExistHashTag(tag).orElse(0);
				if (existHashTag == 0) {
					hashTagDto.setName(tag);
					boardMapper.createHashTag(hashTagDto);
					boardTagDto.setHashTagId(hashTagDto.getHashtagId());
				} else {
					boardTagDto.setHashTagId(existHashTag);
				}
				try {
					boardMapper.createRelationHashTag(boardTagDto);
				} catch (DataIntegrityViolationException e) {
					throw new RestApiException(CustomResponseCode.HASHTAG_POST_NOT_CREATED);
				}
			}
		}
		// 기존 해시태그 제거
		if (removeHashtag != null) {
			for (String tag : removeHashtag) {
				int hashTagCnt = boardMapper.removeHashTag(tag);
				if (hashTagCnt == 0) {
					throw new RestApiException(CustomResponseCode.HASHTAG_NOT_DELETED);
				}
			}
		}
	}

	@Override
	public void deleteArticle(int articleNo) {
		int cnt = boardMapper.deleteArticle(articleNo);
		if (cnt == 0) {
			throw new RestApiException(CustomResponseCode.POST_NOT_DELETED);
		}
	}

	@Override
	public void delRecommend(BoardDto boardDto) {
		int cnt = boardMapper.delRecommend(boardDto);
		if (cnt == 0) {
			throw new RestApiException(CustomResponseCode.LIKE_NOT_DELETED);
		}
	}

	@Override
	public void setRecommend(BoardDto boardDto) {
		try {
			boardMapper.setRecommend(boardDto);
		} catch (DataIntegrityViolationException e) {
			throw new RestApiException(CustomResponseCode.LIKE_NOT_CREATED);
		}
	}

	@Override
	public List<HashTagDto> getHotHashTag() {
		List<HashTagDto> hotHashTag = boardMapper.getHotHashTag();
		if (hotHashTag == null || hotHashTag.isEmpty()) {
			throw new RestApiException(CustomResponseCode.HOT_HASHTAG_NOT_FOUND);
		}
		return hotHashTag;
	}
}