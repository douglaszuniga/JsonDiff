package com.dzuniga.JsonDiff.repository;

import java.util.Optional;

public interface JsonRecordRepository {
    void save(JsonRecord record);
    Optional<JsonRecord> getById(Long id);
}
