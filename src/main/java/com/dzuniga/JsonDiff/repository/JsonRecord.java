package com.dzuniga.JsonDiff.repository;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represent the JsonRecord entity used to store in the database
 */
@Data
public class JsonRecord {

    private final Long Id;
    private final String left;
    private final String right;

    @Builder
    public JsonRecord(Long id, String left, String right) {

        Objects.requireNonNull(id, "Id must not be null");
        checkArgument(id >= 0, "Id must be greater than or equal to zero");

        this.Id = id;
        this.left = left;
        this.right = right;
    }

    public static JsonRecord.JsonRecordBuilder from(JsonRecord record) {
        Objects.requireNonNull(record, "Id must not be null");
        return JsonRecord.builder().right(record.getRight()).left(record.getLeft()).id(record.getId());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("Id", Id)
                .append("left", left)
                .append("right", right)
                .toString();
    }
}
