package edu.ssafy.enjoytrip.response.exception;

import edu.ssafy.enjoytrip.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {
	private final ResponseCode responseCode;
}
