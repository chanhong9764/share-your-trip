package edu.ssafy.enjoytrip.response.handler;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import edu.ssafy.enjoytrip.response.code.CommonResponseCode;
import edu.ssafy.enjoytrip.response.code.ResponseCode;
import edu.ssafy.enjoytrip.response.exception.RestApiException;
import edu.ssafy.enjoytrip.response.structure.ErrorResponse;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
	 * RestApiException 예외 처리
	 */
	@ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleRestApiException(final RestApiException e) {
        final ResponseCode responseCode = e.getResponseCode();
        return handleExceptionInternal(responseCode);
    }

    /**
     * 비즈니스 로직 수행 도중, 사용자의 요청 파라미터가 적절하지 않을 때 발생
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(final IllegalArgumentException e) {
        final ResponseCode responseCode = CommonResponseCode.INVALID_PARAMETER;
        return handleExceptionInternal(responseCode, e.getMessage());
    }

    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {
        final ResponseCode responseCode = CommonResponseCode.INVALID_PARAMETER;
        return handleExceptionInternal(e, responseCode);
    }

    /**
     * 선언한 예외 외의 모든 것을 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(final Exception e) {
        final ResponseCode responseCode = CommonResponseCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(responseCode);
    }

    private ResponseEntity<Object> handleExceptionInternal(final ResponseCode responseCode) {
        return ResponseEntity.status(responseCode.getHttpStatus())
                .body(makeErrorResponse(responseCode));
    }

    private ResponseEntity<Object> handleExceptionInternal(final ResponseCode responseCode, final String message) {
        return ResponseEntity.status(responseCode.getHttpStatus())
                .body(makeErrorResponse(responseCode, message));
    }

    private ResponseEntity<Object> handleExceptionInternal(final BindException e, final ResponseCode responseCode) {
        return ResponseEntity.status(responseCode.getHttpStatus())
                .body(makeErrorResponse(e, responseCode));
    }

    private ErrorResponse makeErrorResponse(final ResponseCode responseCode) {
        return ErrorResponse.builder()
                .code(responseCode.name())
                .message(responseCode.getMessage())
                .build();
    }

    private ErrorResponse makeErrorResponse(final ResponseCode responseCode, final String message) {
        return ErrorResponse.builder()
                .code(responseCode.name())
                .message(message)
                .build();
    }

    private ErrorResponse makeErrorResponse(final BindException e, final ResponseCode responseCode) {
        final List<ErrorResponse.ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .collect(Collectors.toList());

        return ErrorResponse.builder()
                .code(responseCode.name())
                .message(responseCode.getMessage())
                .errors(validationErrorList)
                .build();
    }
}
