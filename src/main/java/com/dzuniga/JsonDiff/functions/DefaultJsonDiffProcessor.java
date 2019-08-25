package com.dzuniga.JsonDiff.functions;

import com.dzuniga.JsonDiff.exception.IncompleteJsonRecordException;
import com.dzuniga.JsonDiff.exception.JsonRecordNotFoundException;
import com.dzuniga.JsonDiff.model.DiffResult;
import com.dzuniga.JsonDiff.repository.JsonRecord;
import com.dzuniga.JsonDiff.repository.JsonRecordRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultJsonDiffProcessor implements JsonDiffProcessor {

    private final JsonRecordRepository recordRepository;
    private final JsonDiffChecker diffChecker;

    public DefaultJsonDiffProcessor(JsonRecordRepository recordRepository, JsonDiffChecker diffChecker) {
        Objects.requireNonNull(recordRepository, "Json Record Repository must not be null");
        Objects.requireNonNull(diffChecker, "Json Diff Checker must not be null");
        this.recordRepository = recordRepository;
        this.diffChecker = diffChecker;
    }

    @Override
    public DiffResult apply(Long id) {
        Objects.requireNonNull(id, "The record id must not be null");

        Optional<JsonRecord> optionalRecord = recordRepository.getById(id);

        return optionalRecord
                .map(record -> {
                    if (isRecordReadyToProcess(record)) {
                        return diffChecker.check(record.getLeft(), record.getRight());
                    }
                    throw new IncompleteJsonRecordException(record);
                })
                .orElseThrow(() -> new JsonRecordNotFoundException(id));
    }

    private boolean isRecordReadyToProcess(JsonRecord record) {
        if (record == null) {
            return false;
        }
        return record.getLeft() != null && record.getRight() != null;
    }
}
