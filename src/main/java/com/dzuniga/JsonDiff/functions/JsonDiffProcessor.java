package com.dzuniga.JsonDiff.functions;

import com.dzuniga.JsonDiff.model.DiffResult;
import java.util.function.Function;

/**
 * Responsible of processing the diff between the left / right of the id provided
 * - get the record from the database
 * - check if the record is ready to start processing the diff
 * - get the DiffResult from the checker
 * Throws IncompleteJsonRecordException when the id record is missing either left or right
 * Throws JsonRecordNotFoundException when the no record was found in the database
 */
@FunctionalInterface
public interface JsonDiffProcessor extends Function<Long, DiffResult> {
}
