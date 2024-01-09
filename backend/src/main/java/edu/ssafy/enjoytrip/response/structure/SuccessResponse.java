package edu.ssafy.enjoytrip.response.structure;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.ssafy.enjoytrip.response.code.SuccessCode;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@RequiredArgsConstructor
public class SuccessResponse<T> {
    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final T data;

    public static <T> ResponseEntity<Object> createSuccess(final SuccessCode successCode, final T data) {
        return ResponseEntity.status(successCode.getHttpStatus())
                .body(SuccessResponse.builder()
                        .code(successCode.name())
                        .message(successCode.getMessage())
                        .data(data)
                        .build()
                );
    }

    public static <T> ResponseEntity<Object> createSuccess(final SuccessCode successCode) {
        return ResponseEntity.status(successCode.getHttpStatus())
                .body(SuccessResponse.builder()
                        .code(successCode.name())
                        .message(successCode.getMessage())
                        .build()
                );
    }
}
