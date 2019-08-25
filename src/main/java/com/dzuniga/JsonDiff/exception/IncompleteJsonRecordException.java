package com.dzuniga.JsonDiff.exception;

import com.dzuniga.JsonDiff.repository.JsonRecord;

public class IncompleteJsonRecordException extends RuntimeException {

    private final JsonRecord record;

    public IncompleteJsonRecordException(JsonRecord record) {
        this.record = record;
    }

    @Override
    public String getMessage() {
        return "The Json Record with id: [" + record.getId() + "] is incomplete, either left or right json is missing";
    }
}
