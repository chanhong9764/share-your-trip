package edu.ssafy.enjoytrip.service.comment;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ssafy.enjoytrip.dto.comment.CommentDto;

public interface CommentService {
	ArrayList<CommentDto> getComments(int articleNo);
	void createComment(CommentDto commentDto) ;
	void deleteComment(int commentId) ;
}
