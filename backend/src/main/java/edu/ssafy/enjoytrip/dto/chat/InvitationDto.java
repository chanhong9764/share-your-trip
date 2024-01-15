package edu.ssafy.enjoytrip.dto.chat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class InvitationDto {
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class InvitationResponseDto {
        private String identifier;
        private int participantId;
        @Builder
        public InvitationResponseDto(String identifier, int participantId) {
            this.identifier = identifier;
            this.participantId = participantId;
        }
    }
}
