package com.dzuniga.JsonDiff.functions;

import static com.google.common.base.Preconditions.checkArgument;

import com.dzuniga.JsonDiff.model.DiffResult;
import com.dzuniga.JsonDiff.model.Result;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class DefaultJsonDiffChecker implements JsonDiffChecker {

    private final InsightsFinder insightsFinder;

    public DefaultJsonDiffChecker(InsightsFinder insightsFinder) {
        Objects.requireNonNull(insightsFinder, "The Insights Finder must not be null");

        this.insightsFinder = insightsFinder;
    }

    @Override
    public DiffResult check(String left, String right) {
        checkArgument(StringUtils.isNotBlank(left), "Left String must not be blank");
        checkArgument(StringUtils.isNotBlank(right), "Right String must not be blank");

        DiffResult.DiffResultBuilder resultBuilder = DiffResult.builder();

        if (left.equals(right))  {
            return resultBuilder.result(Result.EQUAL).build();
        }

        if (left.length() != right.length()) {
            return resultBuilder.result(Result.NOT_EQUAL_SIZE).build();
        }

        return resultBuilder.result(Result.EQUAL_SIZE).insight(insightsFinder.getDiffInsights(left, right)).build();
    }
}
