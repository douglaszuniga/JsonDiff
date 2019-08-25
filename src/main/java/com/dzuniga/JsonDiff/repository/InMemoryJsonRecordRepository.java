package com.dzuniga.JsonDiff.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class InMemoryJsonRecordRepository implements JsonRecordRepository {

    private static final Map<Long, JsonRecord> database = new HashMap<>();

    public void save(JsonRecord record) {
        database.put(record.getId(), record);
        log.trace("Saved jsonRecord with id: [{}], the database contains [{}] entries.", record.getId(), database.size());
    }

    public Optional<JsonRecord> getById(Long id) {
        log.trace("Finding jsonRecord with id: [{}], the database contains [{}] entries.", id, database.size());
        return Optional.ofNullable(database.get(id));
    }
}
