package edu.ssafy.enjoytrip.service.comment;

import java.util.ArrayList;
import java.util.List;

import edu.ssafy.enjoytrip.dto.comment.CommentDto;

public interface CommentService {
	List<CommentDto.CommentResponseDto> getComments(int articleNo);
	void createComment(CommentDto.CommentRequestDto commentDto) ;
	void deleteComment(int commentId) ;
}
