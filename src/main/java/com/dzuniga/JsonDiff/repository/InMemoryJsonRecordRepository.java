package com.dzuniga.JsonDiff.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryJsonRecordRepository implements JsonRecordRepository {

    private static final Map<Long, JsonRecord> database = new HashMap<>();

    public void save(JsonRecord record) {
        database.put(record.getId(), record);
    }

    public Optional<JsonRecord> getById(Long id) {
        return Optional.ofNullable(database.get(id));
    }
}
