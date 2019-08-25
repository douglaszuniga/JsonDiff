package com.dzuniga.JsonDiff.functions;

import com.dzuniga.JsonDiff.model.DiffResult;

@FunctionalInterface
public interface JsonDiffChecker {

    DiffResult check(String left, String right);
}
