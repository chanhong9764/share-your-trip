package edu.ssafy.enjoytrip.mapper;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import edu.ssafy.enjoytrip.dto.comment.CommentDto;

@Mapper
public interface CommentMapper {
	ArrayList<CommentDto> getComments(int articleNo) throws SQLException;
	int createComment(CommentDto commentDto) throws SQLException;
	int reviseComment(CommentDto commentDto) throws SQLException;
	int deleteComment(int commentId) throws SQLException;
}
