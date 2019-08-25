package com.dzuniga.JsonDiff.functions;

import static com.google.common.base.Preconditions.checkArgument;

import com.dzuniga.JsonDiff.model.DiffItem;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class DefaultInsightsFinder implements InsightsFinder {

    @Override
    public Collection<DiffItem> getDiffInsights(String left, String right) {

        checkArgument(StringUtils.isNotBlank(left), "Left String must not be blank");
        checkArgument(StringUtils.isNotBlank(right), "Right String must not be blank");

        Collection<DiffItem> insights = new ArrayList<>();

        long offset = 0;
        long length = 0;
        boolean inCaptureMode = false;
        // both strings are the same size, we can safely moving using left as the base
        for (int i = 0; i < left.length(); i++) {
            if (left.charAt(i) != right.charAt(i)) {
                if (!inCaptureMode) {
                    offset = i;
                    inCaptureMode = true;
                }
                length++;
            } else {
                if (inCaptureMode) {
                    // add the recorded diff into the insights collection
                    insights.add(DiffItem.builder().length(length).offset(offset).build());
                    // reset the help variables
                    inCaptureMode = false;
                    offset = 0;
                    length = 0;
                }
            }
        }
        return insights;
    }
}
