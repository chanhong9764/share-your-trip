package edu.ssafy.enjoytrip.dto.chat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Alias("chatting")
public class Chatting {
    private int chattingId;
    private String message;
    private String createdAt;
    private String userId;
    private int roomId;
    @Builder
    public Chatting(int chattingId, String message, String createdAt, String userId, int roomId) {
        this.chattingId = chattingId;
        this.message = message;
        this.createdAt = createdAt;
        this.userId = userId;
        this.roomId = roomId;
    }
}
