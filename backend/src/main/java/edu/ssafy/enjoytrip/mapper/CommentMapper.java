package edu.ssafy.enjoytrip.mapper;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import edu.ssafy.enjoytrip.dto.comment.CommentDto;

@Mapper
public interface CommentMapper {
	ArrayList<CommentDto> getComments(int articleNo);
	void createComment(CommentDto commentDto);
	int deleteComment(int commentId);
}
