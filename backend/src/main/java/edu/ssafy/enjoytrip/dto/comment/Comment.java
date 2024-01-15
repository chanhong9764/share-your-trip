package edu.ssafy.enjoytrip.dto.comment;

import lombok.*;
import org.apache.ibatis.type.Alias;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Alias("comment")
public class Comment {
    private int commentId;
    private String userId;
    private int articleNo;
    private String content;
    private String createdAt;
    private String profile;
    @Builder
    public Comment(int commentId, String userId, int articleNo, String content, String createdAt, String profile) {
        this.commentId = commentId;
        this.userId = userId;
        this.articleNo = articleNo;
        this.content = content;
        this.createdAt = createdAt;
        this.profile = profile;
    }

    public CommentDto.CommentResponseDto toCommentResponseDto() {
        return CommentDto.CommentResponseDto.builder()
                .commentId(commentId)
                .userId(userId)
                .articleNo(articleNo)
                .content(content)
                .createdAt(createdAt)
                .profile(profile)
                .build();
    }
}
