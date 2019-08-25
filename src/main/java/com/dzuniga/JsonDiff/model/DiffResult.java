package com.dzuniga.JsonDiff.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents the global diff result and the insights
 * {result,[{offset, length}]}
 */
@Data
public class DiffResult {

    private final Result result;
    private final Collection<DiffItem> insight;

    @Builder
    @JsonCreator
    public DiffResult(
            @JsonProperty("result") Result result,
            @JsonProperty("insight") Collection<DiffItem> insight
    ) {
        Objects.requireNonNull(result, "Result must not be null");

        this.result = result;
        this.insight = insight;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("result", result)
                .append("insight", insight)
                .toString();
    }
}
