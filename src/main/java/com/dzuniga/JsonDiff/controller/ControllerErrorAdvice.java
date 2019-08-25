package com.dzuniga.JsonDiff.controller;

import com.dzuniga.JsonDiff.exception.IncompleteJsonRecordException;
import com.dzuniga.JsonDiff.exception.InputNonValidException;
import com.dzuniga.JsonDiff.exception.JsonRecordNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerErrorAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InputNonValidException.class)
    public ResponseEntity<Object> handleInputNonValidException(InputNonValidException e, WebRequest request) {
        RestError restError = RestError.builder().message(e.getMessage()).status(HttpStatus.BAD_REQUEST).build();
        return handleExceptionInternal(e, restError, new HttpHeaders(), restError.getStatus(), request);
    }

    @ExceptionHandler(IncompleteJsonRecordException.class)
    public ResponseEntity<Object> handleIncompleteJsonRecordException(IncompleteJsonRecordException e, WebRequest request) {
        RestError restError = RestError.builder().message(e.getMessage()).status(HttpStatus.CONFLICT).build();
        return handleExceptionInternal(e, restError, new HttpHeaders(), restError.getStatus(), request);
    }

    @ExceptionHandler(JsonRecordNotFoundException.class)
    public ResponseEntity<Object> handleJsonRecordNotFoundException(JsonRecordNotFoundException e, WebRequest request) {
        RestError restError = RestError.builder().message(e.getMessage()).status(HttpStatus.NOT_FOUND).build();
        return handleExceptionInternal(e, restError, new HttpHeaders(), restError.getStatus(), request);
    }
}
