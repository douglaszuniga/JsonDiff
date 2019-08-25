package com.dzuniga.JsonDiff.exception;

/**
 * Exception used to indicate that there is no record in the database for the giving id
 */
public class JsonRecordNotFoundException extends RuntimeException {
    private final Long id;

    public JsonRecordNotFoundException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "No Json record with id: [" + id + "] was found.";
    }
}
