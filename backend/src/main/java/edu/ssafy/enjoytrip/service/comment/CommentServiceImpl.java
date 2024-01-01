package edu.ssafy.enjoytrip.service.comment;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ssafy.enjoytrip.dto.comment.CommentDto;
import edu.ssafy.enjoytrip.mapper.CommentMapper;

@Service("CommentServiceImpl")
public class CommentServiceImpl implements CommentService {
	private CommentMapper commentMapper;
	
	@Autowired
	private CommentServiceImpl(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}

	@Override
	public ArrayList<CommentDto> getComments(int articleNo) throws SQLException {
		return commentMapper.getComments(articleNo);
	}
	@Override
	public int createComment(CommentDto commentDto) throws SQLException {
		return commentMapper.createComment(commentDto);
	}
	@Override
	public int deleteComment(int commentId) throws SQLException {
		return commentMapper.deleteComment(commentId);
	}
	@Override
	public int reviseComment(CommentDto commentDto) throws SQLException {
		return commentMapper.reviseComment(commentDto);
	}
}
