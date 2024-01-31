package edu.ssafy.enjoytrip.dto.board;

import java.util.List;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board{
    private int articleNo;
    private String userId;
    private String content;
    private String createdAt;
    @Builder
    public Board(int articleNo, String userId, String content, String createdAt) {
        this.articleNo = articleNo;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }
}
