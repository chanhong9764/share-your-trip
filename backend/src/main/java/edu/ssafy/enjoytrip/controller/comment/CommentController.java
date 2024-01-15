package edu.ssafy.enjoytrip.controller.comment;

import java.util.ArrayList;
import java.util.List;

import edu.ssafy.enjoytrip.response.code.SuccessCode;
import edu.ssafy.enjoytrip.response.structure.SuccessResponse;
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
public class CommentController {
	private final CommentService service;
	
	@GetMapping("/{articleNo}")
	public ResponseEntity<Object> getComments(@PathVariable("articleNo") int articleNo ) {
		List<CommentDto.CommentResponseDto> comments = service.getComments(articleNo);

		return SuccessResponse.createSuccess(SuccessCode.LOAD_COMMENT_SUCCESS, comments);
	}
	
	@PostMapping
	public ResponseEntity<Object> setComment(@RequestBody CommentDto.CommentRequestDto requestDto) {
		service.createComment(requestDto);
		List<CommentDto.CommentResponseDto> responseDtoList = service.getComments(requestDto.getArticleNo());

		return SuccessResponse.createSuccess(SuccessCode.CREATED_COMMENT_SUCCESS, responseDtoList);
	}

	@DeleteMapping
	public ResponseEntity<Object> delComment(@RequestParam("articleNo") int articleNo, @RequestParam("commentId") int commentId) {
		service.deleteComment(commentId);
		List<CommentDto.CommentResponseDto> responseDtoList = service.getComments(articleNo);

		return SuccessResponse.createSuccess(SuccessCode.DELETE_COMMENT_SUCCESS, responseDtoList);
	}
}
