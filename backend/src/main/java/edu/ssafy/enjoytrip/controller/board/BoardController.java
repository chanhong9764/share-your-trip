package edu.ssafy.enjoytrip.controller.board;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import edu.ssafy.enjoytrip.dto.board.*;
import edu.ssafy.enjoytrip.response.code.CustomResponseCode;
import edu.ssafy.enjoytrip.response.code.SuccessCode;
import edu.ssafy.enjoytrip.response.exception.RestApiException;
import edu.ssafy.enjoytrip.response.structure.SuccessResponse;
import edu.ssafy.enjoytrip.util.SizeConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.ssafy.enjoytrip.service.board.BoardService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
	@Value("${file.path}")
	private String uploadPath;
	private final BoardService service;

	@GetMapping
	public ResponseEntity<Object> GetBoardList(
			@RequestParam(value = "pgno", required = false, defaultValue = "1") int pgno,
			@RequestParam(value = "type", required = false, defaultValue = "0") int type,
			@RequestParam(value = "hashtag", required = false, defaultValue = "") String hashtag,
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "key", required = false, defaultValue = "") String key) {

		List<BoardResponseDto> postLists = service.listArticle(BoardDto.ListRequest.builder()
				.start(pgno * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE)
				.listSize(SizeConstant.LIST_SIZE)
				.hashtag(hashtag)
				.type(type)
				.key(key)
				.userId(userId).build());
		return SuccessResponse.createSuccess(SuccessCode.LOAD_LIST_BOARD_SUCCESS, postLists);
	}

	@GetMapping("/{articleNo}")
	public ResponseEntity<Object> view(@PathVariable("articleNo") int articleNo,
									   @RequestParam(value = "userId", required = false, defaultValue = "") String userId) {
		BoardResponseDto post = service.getArticle(new BoardDto.PostRequest(articleNo, userId));
		return SuccessResponse.createSuccess(SuccessCode.LOAD_POST_BOARD_SUCCESS, post);
	}

	@PostMapping
	public ResponseEntity<Object> write(
			@RequestParam("userId") String userId,
			@RequestParam(value = "content", required = false, defaultValue = "") String content,
			@RequestParam(value = "hashtag", required = false) String[] hashtag,
			@RequestParam(value = "images",required = false, defaultValue = "") MultipartFile[] images) {
		List<BoardImagesDto> imagesData = new ArrayList<>();
		
		if(!images[0].isEmpty()) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = uploadPath + today;
			File folder = new File(saveFolder);

			if(!folder.exists()) {
				folder.mkdirs();
			}

			for(MultipartFile image : images) {
				BoardImagesDto imageInfo = new BoardImagesDto();
				String originalFileName = image.getOriginalFilename();
				if(originalFileName != null && !originalFileName.isEmpty()) {
					String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
					imageInfo.setSaveFolder(saveFolder);
					imageInfo.setOriginalName(originalFileName);
					imageInfo.setSaveFile(saveFileName);
					try {
						image.transferTo(new File(folder, saveFileName));
					} catch (IOException e) {
						throw new RestApiException(CustomResponseCode.IMAGE_NOT_CREATED);
					}
				}
				imagesData.add(imageInfo);
			}
		}
		service.writeArticle(BoardDto.WriteRequest.builder()
				.userId(userId)
				.content(content)
				.hashtag(hashtag)
				.images(imagesData).build());

		return SuccessResponse.createSuccess(SuccessCode.CREATED_POST_BOARD_SUCCESS);
	}

	@PutMapping
	public ResponseEntity<Object> modify(
			@RequestParam("articleNo") int articleNo,
			@RequestParam("userId") String userId,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "addHashtag", required = false) String[] addHashtag,
			@RequestParam(value = "removeHashtag", required = false) String[] removeHashtag,
			@RequestParam(value = "addImages", required = false) MultipartFile[] addImages,
			@RequestParam(value = "removeImages",required = false) String[] removeImages) {
		List<BoardImagesDto> imagesData = new ArrayList<>();
		if(addImages != null) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = uploadPath + today;
			File folder = new File(saveFolder);
			
			if(!folder.exists()) {
				folder.mkdirs();
			}
			for(MultipartFile image : addImages) {
				BoardImagesDto imageInfo = new BoardImagesDto();
				String originalFileName = image.getOriginalFilename();
				if(!originalFileName.isEmpty()) {
					String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
					imageInfo.setSaveFolder(saveFolder);
					imageInfo.setOriginalName(originalFileName);
					imageInfo.setSaveFile(saveFileName);
					try {
						image.transferTo(new File(folder, saveFileName));
					} catch (IOException e) {
						throw new RestApiException(CustomResponseCode.IMAGE_NOT_CREATED);
					}
				}
				imagesData.add(imageInfo);
			}
		}
		service.modifyArticle(BoardDto.ModifyRequest.builder()
						.userId(userId)
						.articleNo(articleNo)
						.addHashtag(addHashtag)
						.removeHashtag(removeHashtag)
						.images(imagesData)
						.content(content)
						.removeImages(removeImages).build());

		return SuccessResponse.createSuccess(SuccessCode.MODIFY_POST_BOARD_SUCCESS);
	}

	@DeleteMapping("/{articleNo}")
	public ResponseEntity<Object> delete(@PathVariable("articleNo") int articleNo) {
		service.deleteArticle(articleNo);

		return SuccessResponse.createSuccess(SuccessCode.DELETE_POST_BOARD_SUCCESS);
	}
	
	@PostMapping("/recommends")
	public ResponseEntity<Object> setRecommend(@RequestBody BoardDto.RecommendRequest requestDto) {
		service.setRecommend(requestDto);

		return SuccessResponse.createSuccess(SuccessCode.CREATED_LIKE_BOARD_SUCCESS);
	}
	@DeleteMapping("/recommends")
	public ResponseEntity<Object> delRecommend(BoardDto.RecommendRequest requestDto) {
		service.delRecommend(requestDto);
		return SuccessResponse.createSuccess(SuccessCode.DELETE_LIKE_BOARD_SUCCESS);
	}
	
	@GetMapping("/hothashtags")
	public ResponseEntity<Object> getHotHashTag() {
		List<HashTagDto> hotHashTag = service.getHotHashTag();
		return SuccessResponse.createSuccess(SuccessCode.LOAD_HOT_HASHTAG_BOARD_SUCCESS, hotHashTag);
	}
}
