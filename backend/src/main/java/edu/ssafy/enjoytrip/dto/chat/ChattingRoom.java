package edu.ssafy.enjoytrip.dto.chat;

import lombok.*;
import org.apache.ibatis.type.Alias;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Alias("chattingRoom")
public class ChattingRoom {
    private int roomId;
    private String identifier;
    private String createdAt;
    private String content;
    private String roomName;
    @Builder
    public ChattingRoom(int roomId, String identifier, String createdAt, String content, String roomName) {
        this.roomId = roomId;
        this.identifier = identifier;
        this.createdAt = createdAt;
        this.content = content;
        this.roomName = roomName;
    }

    public ChattingRoomDto.ChattingRoomResponseDto tochattingRoomResponseDto() {
        return ChattingRoomDto.ChattingRoomResponseDto.builder()
                .roomId(roomId)
                .identifier(identifier)
                .content(content)
                .roomName(roomName)
                .createdAt(createdAt)
                .build();
    }
}
