package edu.ssafy.enjoytrip.service.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.ssafy.enjoytrip.dto.comment.Comment;
import edu.ssafy.enjoytrip.response.code.CustomResponseCode;
import edu.ssafy.enjoytrip.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ssafy.enjoytrip.dto.comment.CommentDto;
import edu.ssafy.enjoytrip.mapper.CommentMapper;
@RequiredArgsConstructor
@Service("CommentServiceImpl")
public class CommentServiceImpl implements CommentService {
	private final CommentMapper commentMapper;

	@Override
	public List<CommentDto.CommentResponseDto> getComments(int articleNo){
		List<CommentDto.CommentResponseDto> comments = commentMapper.getComments(articleNo).stream()
				.map(Comment::toCommentResponseDto)
				.collect(Collectors.toList());
		if(comments.isEmpty()) {
			throw new RestApiException(CustomResponseCode.COMMENT_NOT_FOUND);
		}
		return comments;
	}
	@Override
	public void createComment(CommentDto.CommentRequestDto requestDto){
		try {
			commentMapper.createComment(requestDto.toEntity());
		} catch (DataIntegrityViolationException e) {
			throw new RestApiException(CustomResponseCode.INVALID_COMMENT_CONTENT);
		}
	}
	@Override
	public void deleteComment(int commentId){
		int cnt = commentMapper.deleteComment(commentId);
		if(cnt == 0) {
			throw new RestApiException(CustomResponseCode.COMMENT_NOT_FOUND);
		}
	}
}
