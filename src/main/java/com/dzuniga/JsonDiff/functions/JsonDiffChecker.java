package com.dzuniga.JsonDiff.functions;

import com.dzuniga.JsonDiff.model.DiffResult;

/**
 *
 */
@FunctionalInterface
public interface JsonDiffChecker {

    /**
     * Compares both left and right indicating if left and right are equal, not equal size or equal size + insights
     * Throws NullPointerException or IllegalArgumentException when the arguments don't pass the validation
     * @param left decoded string
     * @param right decoded string
     * @return DiffResult object indicating if left and right are equal, not equal size or equal size + insights
     */
    DiffResult check(String left, String right);
}
