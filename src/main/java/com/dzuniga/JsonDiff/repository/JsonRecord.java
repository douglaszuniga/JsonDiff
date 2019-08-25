package com.dzuniga.JsonDiff.repository;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
public class JsonRecord {

    private final Long Id;
    private final String left;
    private final String right;

    @Builder
    public JsonRecord(Long id, String left, String right) {

        Objects.requireNonNull(id, "Id must not be null");
        Objects.requireNonNull(left, "Left must not be null");
        Objects.requireNonNull(right, "right must not be null");

        checkArgument(id >= 0, "Id must be greater than or equal to zero");
        checkArgument(StringUtils.isNotBlank(left), "Left must not be blank");
        checkArgument(StringUtils.isNotBlank(right), "Right must not be blank");

        this.Id = id;
        this.left = left;
        this.right = right;
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
