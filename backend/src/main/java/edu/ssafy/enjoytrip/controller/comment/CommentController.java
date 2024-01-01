package edu.ssafy.enjoytrip.controller.comment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ssafy.enjoytrip.dto.comment.CommentDto;
import edu.ssafy.enjoytrip.service.comment.CommentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Api(tags = { "comments 컨트롤러 API" })
public class CommentController {
	private final CommentService service;
	
	@GetMapping("/{articleNo}")
	public ResponseEntity<Map<String, Object>> getComments(@PathVariable("articleNo") int articleNo ) {
		Map<String, Object> result = new HashMap<>();
		try {
			ArrayList<CommentDto> comments = service.getComments(articleNo);
			result.put("msg", "댓글 불러오기 성공!");
			result.put("result", comments);
		} catch (SQLException e) {
			e.printStackTrace();
			result.put("msg", "댓글 불러오기 실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> setComment(@RequestBody CommentDto commentDto) {
		Map<String, Object> result = new HashMap<>();
		try {
			// 아이디 JWT에서 추출
			service.createComment(commentDto);
			ArrayList<CommentDto> comments = service.getComments(commentDto.getArticleNo());
			result.put("msg", "댓글 작성 성공!");
			result.put("result", comments);
		} catch (SQLException e) {
			e.printStackTrace();
			result.put("msg", "댓글 작성 실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}

	@DeleteMapping
	public ResponseEntity<Map<String, Object>> delComment(@RequestParam("articleNo") int articleNo, @RequestParam("commentId") int commentId) {
		Map<String, Object> result = new HashMap<>();
		try {
			service.deleteComment(commentId);
			ArrayList<CommentDto> comments = service.getComments(articleNo);
			result.put("msg", "댓글 삭제 성공!");
			result.put("result", comments);
		} catch (SQLException e) {
			e.printStackTrace();
			result.put("msg", "댓글 삭제 실패!");
			result.put("result", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(result, HttpStatus.OK);
		return res;
	}
}
