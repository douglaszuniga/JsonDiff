package com.dzuniga.JsonDiff.functions;

import com.dzuniga.JsonDiff.model.DiffItem;
import java.util.Collection;

/**
 * Responsible of find the hints of where the differences could be found
 */
@FunctionalInterface
public interface InsightsFinder {

    /**
     * Compares both left and right strings finding the places where the differences could be
     * Throws NullPointerException or IllegalArgumentException when the arguments don't pass the validation
     *
     * @param left decoded string
     * @param right decoded string
     * @return list of offsets and length indicating the possible differences
     */
    Collection<DiffItem> getDiffInsights(final String left, final String right);
}
