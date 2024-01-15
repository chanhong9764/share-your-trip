package edu.ssafy.enjoytrip.dto.chat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Alias("chattingParticipant")
public class ChattingParticipant {
    private int participantId;
    private int isAccepted;
    private int roomId;
    private String userId;
    @Builder
    public ChattingParticipant(int participantId, int isAccepted, int roomId, String userId) {
        this.participantId = participantId;
        this.isAccepted = isAccepted;
        this.roomId = roomId;
        this.userId = userId;
    }
}
