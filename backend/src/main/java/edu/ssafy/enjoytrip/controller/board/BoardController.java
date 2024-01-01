package edu.ssafy.enjoytrip.controller.board;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import edu.ssafy.enjoytrip.dto.board.BoardDto;
import edu.ssafy.enjoytrip.dto.board.BoardImagesDto;
import edu.ssafy.enjoytrip.dto.board.BoardResponseDto;
import edu.ssafy.enjoytrip.dto.board.HashTagDto;
import edu.ssafy.enjoytrip.service.board.BoardService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@Api(tags = { "Board 컨트롤러 API" })
public class BoardController {
	@Value("${file.path}")
	private String uploadPath;
	private final BoardService service;

	@GetMapping
	public ResponseEntity<Map<String, Object>> GetBoardList(@RequestParam Map<String, String> map) {
		Map<String, Object> result = new HashMap<>();
		try {
			System.out.println(map);
			// JWT에서 유저아이디 불러와야함
			List<BoardResponseDto> list = service.listArticle(map);
			result.put("msg", "게시글 리스트 불러오기 성공!");
			result.put("result", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "게시글 리스트 불러오기 실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}

	@GetMapping("/{articleNo}")
	public ResponseEntity<Map<String, Object>> view(@PathVariable("articleNo") int articleNo, @RequestParam("userId") String userId) {
		Map<String, Object> result = new HashMap<>();
		try {
			Map<String, Object> temp = new HashMap<>();
			System.out.println(userId);
			// JWT에서 유저아이디 불러와야함
			temp.put("userId", userId);
			temp.put("articleNo", articleNo);
			BoardResponseDto data = service.getArticle(temp);
			
			result.put("msg", "게시글을 불러오기 성공!");
			result.put("result", data);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "게시글을 불러오기 실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> write(
			@RequestParam("userId") String userId,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "hashtag", required = false) String[] hashtag,
			@RequestParam(value = "images",required = false) MultipartFile[] images) throws Exception {
		Map<String, Object> result = new HashMap<>();
		BoardDto boardDto = new BoardDto();
		boardDto.setUserId(userId);
		boardDto.setContent(content);
		
		if(!images[0].isEmpty()) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = uploadPath + today;
			File folder = new File(saveFolder);
			
			if(!folder.exists()) {
				folder.mkdirs();
			}
			List<BoardImagesDto> imagesData = new ArrayList<>();
			for(MultipartFile image : images) {
				BoardImagesDto imageInfo = new BoardImagesDto();
				String originalFileName = image.getOriginalFilename();
				if(!originalFileName.isEmpty()) {
					String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
					imageInfo.setSaveFolder(saveFolder);
					imageInfo.setOriginalName(originalFileName);
					imageInfo.setSaveFile(saveFileName);
					image.transferTo(new File(folder, saveFileName));
				}
				imagesData.add(imageInfo);
			}
			boardDto.setImages(imagesData);
		}
		try {
			service.writeArticle(boardDto, hashtag);
			result.put("msg", "게시글 작성 성공!");
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "게시글 작성 실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}

	@PutMapping
	public ResponseEntity<Map<String, Object>> modify(
			@RequestParam("articleNo") int articleNo,
			@RequestParam("userId") String userId,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "addHashtag", required = false) String[] addHashtag,
			@RequestParam(value = "removeHashtag", required = false) String[] removeHashtag,
			@RequestParam(value = "addImages", required = false) MultipartFile[] addImages,
			@RequestParam(value = "removeImages",required = false) String[] removeImages) throws Exception {
		Map<String, Object> result = new HashMap<>();
		BoardDto boardDto = new BoardDto();
		boardDto.setUserId(userId);
		boardDto.setContent(content);
		boardDto.setArticleNo(articleNo);
		
		if(addImages != null) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = uploadPath + today;
			File folder = new File(saveFolder);
			
			if(!folder.exists()) {
				folder.mkdirs();
			}
			List<BoardImagesDto> imagesData = new ArrayList<>();
			for(MultipartFile image : addImages) {
				BoardImagesDto imageInfo = new BoardImagesDto();
				String originalFileName = image.getOriginalFilename();
				if(!originalFileName.isEmpty()) {
					String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
					imageInfo.setSaveFolder(saveFolder);
					imageInfo.setOriginalName(originalFileName);
					imageInfo.setSaveFile(saveFileName);
					image.transferTo(new File(folder, saveFileName));
				}
				imagesData.add(imageInfo);
			}
			boardDto.setImages(imagesData);
		}
		try {
			service.modifyArticle(boardDto, addHashtag, removeHashtag, removeImages);
			result.put("msg", "게시글 수정 성공!");
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "게시글 수정 실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}

	@DeleteMapping("/{articleNo}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable("articleNo") int articleNo) {
		Map<String, Object> result = new HashMap<>();
		try {
			service.deleteArticle(articleNo);
			result.put("msg", "게시글 삭제 성공!");
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "게시글 삭제 실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}
	
	@PostMapping("/recommends")
	public ResponseEntity<Map<String, Object>> setRecommend(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		try {
			service.setRecommend(map);
			result.put("msg", "추천 생성 성공!");
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "추천 생성 실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}
	@DeleteMapping("/recommends")
	public ResponseEntity<Map<String, Object>> delRecommend(@RequestParam("articleNo") String articleNo, @RequestParam("userId") String userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("articleNo", articleNo);
		map.put("userId", userId);
		System.out.println(map);
		Map<String, Object> result = new HashMap<>();
		try {
			service.delRecommend(map);
			result.put("msg", "추천 삭제 성공!");
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "추천 삭제 실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}
	
	@GetMapping("/hothashtags")
	public ResponseEntity<Map<String, Object>> getHotHashTag() {
		Map<String, Object> result = new HashMap<>();
		try {
			List<HashTagDto> hotHashTag = service.getHotHashTag();
			System.out.println(hotHashTag);
			result.put("msg", "뜨건 해시태그 가져오기 성공!");
			result.put("result", hotHashTag);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "해시태그 가져오기 실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}
}
