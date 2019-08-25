package com.dzuniga.JsonDiff.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

/**
 * Represents a Error body, the idea is to provide a custom message when something wrong happens
 */
@Data
public class RestError {
    private final HttpStatus status;
    private final String message;

    @Builder
    @JsonCreator
    public RestError(
            @JsonProperty("status") HttpStatus status,
            @JsonProperty("message") String message
    ) {
        Objects.requireNonNull(status, "Status must not be null");

        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("status", status)
                .append("message", message)
                .toString();
    }
}
