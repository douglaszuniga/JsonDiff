package com.dzuniga.JsonDiff.exception;

/**
 * Exception use when the user input is invalid
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
