package edu.ssafy.enjoytrip.mapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ssafy.enjoytrip.dto.comment.Comment;
import org.apache.ibatis.annotations.Mapper;

import edu.ssafy.enjoytrip.dto.comment.CommentDto;

@Mapper
public interface CommentMapper {
	List<Comment> getComments(int articleNo);
	void createComment(Comment comment);
	int deleteComment(int commentId);
}
