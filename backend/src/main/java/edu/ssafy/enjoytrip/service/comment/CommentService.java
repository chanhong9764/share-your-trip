package edu.ssafy.enjoytrip.service.comment;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ssafy.enjoytrip.dto.comment.CommentDto;

public interface CommentService {
	ArrayList<CommentDto> getComments(int articleNo) throws SQLException;
	int createComment(CommentDto commentDto) throws SQLException;
	int reviseComment(CommentDto commentDto) throws SQLException;
	int deleteComment(int commentId) throws SQLException;
}
