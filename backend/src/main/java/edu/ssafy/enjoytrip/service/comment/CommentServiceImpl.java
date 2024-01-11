package edu.ssafy.enjoytrip.service.comment;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ssafy.enjoytrip.response.code.CustomResponseCode;
import edu.ssafy.enjoytrip.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ssafy.enjoytrip.dto.comment.CommentDto;
import edu.ssafy.enjoytrip.mapper.CommentMapper;
@RequiredArgsConstructor
@Service("CommentServiceImpl")
public class CommentServiceImpl implements CommentService {
	private final CommentMapper commentMapper;

	@Override
	public ArrayList<CommentDto> getComments(int articleNo){
		ArrayList<CommentDto> comments = commentMapper.getComments(articleNo);
		if(comments == null || comments.isEmpty()) {
			throw new RestApiException(CustomResponseCode.COMMENT_NOT_FOUND);
		}
		return comments;
	}
	@Override
	public void createComment(CommentDto commentDto){
		try {
			commentMapper.createComment(commentDto);
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
