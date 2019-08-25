package com.dzuniga.JsonDiff.model;

import static com.google.common.base.Preconditions.checkArgument;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
public class DiffItem {

    private final long offset;
    private final long length;

    @Builder
    @JsonCreator
    public DiffItem(
            @JsonProperty("offset") long offset,
            @JsonProperty("length") long length
    ) {
        checkArgument(offset >= 0, "The offset must be greater than or equals to zero");
        checkArgument(length >= 0, "The offset must be greater than or equals to zero");

        this.offset = offset;
        this.length = length;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("offset", offset)
                .append("length", length)
                .toString();
    }
}
