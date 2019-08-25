package com.dzuniga.JsonDiff.exception;

public class InputNonValidException extends RuntimeException {
    private final String input;

    public InputNonValidException(String input) {
        this.input = input;
    }

    @Override
    public String getMessage() {
        return "The input: ["+ input +"] is not a valid Base64 string.";
    }
}
