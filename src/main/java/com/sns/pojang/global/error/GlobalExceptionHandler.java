package com.sns.pojang.global.error;

import com.sns.pojang.global.error.exception.EntityNotFoundException;
import com.sns.pojang.global.error.exception.InvalidValueException;
import com.sns.pojang.global.error.exception.PojangException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String LOG_FORMAT = "Class: {}, Status: {}, Message: {}";

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("UnExpected Exception", e);
        ErrorResponse response = ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.getMessage());
        ErrorResponse response = ErrorResponse.from(ErrorCode.ACCESS_DENIED);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    // @Valid, @RequestBody를 선언한 Method에서 Binding Error 발생
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException", e);
        BindingResult bindingResult = e.getBindingResult();
        ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, bindingResult);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode().getStatus(), e.getErrorCode().getMessage());
        ErrorResponse response = ErrorResponse.from(e.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidValueException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidValueException(InvalidValueException e) {
        log.error(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode().getStatus(), e.getErrorCode().getMessage());
        ErrorResponse response = ErrorResponse.from(e.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PojangException.class)
    protected ResponseEntity<ErrorResponse> handlePojangException(PojangException e) {
        log.error(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode(), e.getErrorCode().getMessage());
        ErrorResponse response = ErrorResponse.from(e.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
