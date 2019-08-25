package com.dzuniga.JsonDiff.functions;

import com.dzuniga.JsonDiff.model.DiffResult;
import java.util.function.Function;

@FunctionalInterface
public interface JsonDiffProcessor extends Function<Long, DiffResult> {
}
