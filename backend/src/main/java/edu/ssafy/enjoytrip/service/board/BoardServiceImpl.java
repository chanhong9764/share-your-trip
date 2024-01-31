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
	public List<BoardResponseDto> listArticle(BoardDto.ListRequest requestDto) {
		// 메인 페이지 인기 게시글 리스트
		if (!requestDto.getKey().isEmpty()) {
			requestDto.updatePost();
		}
		List<BoardResponseDto> list = null;
		// 게시글 리스트 불러오는 부분
		if (!requestDto.getHashtag().isEmpty()) {
			list = boardMapper.listArticleByHashtag(requestDto);
		} else {
			list = boardMapper.listArticle(requestDto);
		}

		if (list == null || list.isEmpty()) {
			throw new RestApiException(CustomResponseCode.BOARD_LIST_NOT_FOUND);
		}

		for (BoardResponseDto post : list) {
			// 로그인 했을 경우
			if (!requestDto.getUserId().isEmpty()) {
				// 게시글마다 추천 여부 확인
				int cnt = boardMapper.getRecommendUser(new BoardDto.PostRequest(post.getArticleNo(), requestDto.getUserId()));
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
	public BoardResponseDto getArticle(BoardDto.PostRequest requestDto) {
		BoardResponseDto article = boardMapper.getArticle(requestDto.getArticleNo());
		// 게시글 추천 여부 확인
		if (!requestDto.getUserId().isEmpty() && boardMapper.getRecommendUser(requestDto) != 0) {
			article.setRecommend(true);
		}
		// 게시글의 해시태그
		List<HashTagDto> hashTagList = boardMapper.getHashTagByArticleNo(requestDto.getArticleNo());
		article.setHashtag(hashTagList);

		// 게시글의 이미지
		List<BoardImagesDto> boardImages = boardMapper.getBoardImagesByArticleNo(requestDto.getArticleNo());
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
	public void writeArticle(BoardDto.WriteRequest requestDto) {
		// 게시글 저장
		try {
			boardMapper.writeArticle(requestDto);
		} catch (DataIntegrityViolationException e) {
			throw new RestApiException(CustomResponseCode.POST_NOT_CREATED);
		}
		List<BoardImagesDto> images = requestDto.getImages();

		// 이미지 저장
		if (images != null && !images.isEmpty()) {
			try {
				boardMapper.registerFile(requestDto);
			} catch (DataIntegrityViolationException e) {
				throw new RestApiException(CustomResponseCode.IMAGE_NOT_CREATED);
			}
		}

		// 해시태그 처리
		BoardTagDto boardTagDto = new BoardTagDto();
		boardTagDto.setArticleNo(requestDto.getArticleNo());
		HashTagDto hashTagDto = new HashTagDto();

		for (String tag : requestDto.getHashtag()) {
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
	public void modifyArticle(BoardDto.ModifyRequest requestDto) {
		// 게시글 업데이트
		int cnt = boardMapper.modifyArticle(requestDto);
		if (cnt == 0) {
			throw new RestApiException(CustomResponseCode.POST_NOT_MODIFIED);
		}

		// 추가된 사진 삽입
		List<BoardImagesDto> images = requestDto.getImages();

		if (images != null && !images.isEmpty()) {
			try {
				boardMapper.registerFile(BoardDto.WriteRequest.builder()
								.articleNo(requestDto.getArticleNo())
								.images(requestDto.getImages()).build());
			} catch (DataIntegrityViolationException e) {
				throw new RestApiException(CustomResponseCode.IMAGE_NOT_CREATED);
			}
		}
		// 기존 사진 제거
		if (requestDto.getRemoveImages() != null) {
			for (String image : requestDto.getRemoveImages()) {
				int deleteCnt = boardMapper.removeImages(image);
				if (deleteCnt == 0) {
					throw new RestApiException(CustomResponseCode.IMAGE_NOT_DELETED);
				}
			}
		}

		// 해시태그 업데이트
		BoardTagDto boardTagDto = new BoardTagDto();
		boardTagDto.setArticleNo(requestDto.getArticleNo());
		HashTagDto hashTagDto = new HashTagDto();

		// 해시태그 추가
		if (requestDto.getAddHashtag() != null) {
			for (String tag : requestDto.getAddHashtag()) {
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
		if (requestDto.getRemoveHashtag() != null) {
			for (String tag : requestDto.getRemoveHashtag()) {
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
	public void delRecommend(BoardDto.RecommendRequest requestDto) {
		int cnt = boardMapper.delRecommend(requestDto);
		if (cnt == 0) {
			throw new RestApiException(CustomResponseCode.LIKE_NOT_DELETED);
		}
	}

	@Override
	public void setRecommend(BoardDto.RecommendRequest requestDto) {
		try {
			boardMapper.setRecommend(requestDto);
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