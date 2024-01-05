package edu.ssafy.enjoytrip.response.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
	String name();
	HttpStatus getHttpStatus();
	String getMessage();
}
