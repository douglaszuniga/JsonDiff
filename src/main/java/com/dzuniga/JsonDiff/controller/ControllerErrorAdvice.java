package com.dzuniga.JsonDiff.controller;

import com.dzuniga.JsonDiff.exception.IncompleteJsonRecordException;
import com.dzuniga.JsonDiff.exception.InputNonValidException;
import com.dzuniga.JsonDiff.exception.JsonRecordNotFoundException;
import com.dzuniga.JsonDiff.exception.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Maps app exceptions to proper Http status and responses
 */
@RestControllerAdvice
public class ControllerErrorAdvice extends ResponseEntityExceptionHandler {

    /**
     * handles the ValidationException converting the response to Http 400 bad request
     * @param e the ValidationException captured
     * @param request the request object
     * @return ResponseEntity with the {RestError} and Status
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException e, WebRequest request) {
        RestError restError = RestError.builder().message(e.getMessage()).status(HttpStatus.BAD_REQUEST).build();
        return handleExceptionInternal(e, restError, new HttpHeaders(), restError.getStatus(), request);
    }
    /**
     * handles the InputNonValidException converting the response to Http 400 bad request
     * @param e the InputNonValidException captured
     * @param request the request object
     * @return ResponseEntity with the {RestError} and Status
     */
    @ExceptionHandler(InputNonValidException.class)
    public ResponseEntity<Object> handleInputNonValidException(InputNonValidException e, WebRequest request) {
        RestError restError = RestError.builder().message(e.getMessage()).status(HttpStatus.BAD_REQUEST).build();
        return handleExceptionInternal(e, restError, new HttpHeaders(), restError.getStatus(), request);
    }
    /**
     * handles the IncompleteJsonRecordException converting the response to Http 409 conflict
     * @param e the IncompleteJsonRecordException captured
     * @param request the request object
     * @return ResponseEntity with the {RestError} and Status
     */
    @ExceptionHandler(IncompleteJsonRecordException.class)
    public ResponseEntity<Object> handleIncompleteJsonRecordException(IncompleteJsonRecordException e, WebRequest request) {
        RestError restError = RestError.builder().message(e.getMessage()).status(HttpStatus.CONFLICT).build();
        return handleExceptionInternal(e, restError, new HttpHeaders(), restError.getStatus(), request);
    }
    /**
     * handles the JsonRecordNotFoundException converting the response to Http 404 not found
     * @param e the JsonRecordNotFoundException captured
     * @param request the request object
     * @return ResponseEntity with the {RestError} and Status
     */
    @ExceptionHandler(JsonRecordNotFoundException.class)
    public ResponseEntity<Object> handleJsonRecordNotFoundException(JsonRecordNotFoundException e, WebRequest request) {
        RestError restError = RestError.builder().message(e.getMessage()).status(HttpStatus.NOT_FOUND).build();
        return handleExceptionInternal(e, restError, new HttpHeaders(), restError.getStatus(), request);
    }
}
