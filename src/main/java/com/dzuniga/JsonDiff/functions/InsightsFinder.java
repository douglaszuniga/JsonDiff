package com.dzuniga.JsonDiff.functions;

import com.dzuniga.JsonDiff.model.DiffItem;
import java.util.Collection;

@FunctionalInterface
public interface InsightsFinder {

    Collection<DiffItem> getDiffInsights(final String left, final String right);
}
