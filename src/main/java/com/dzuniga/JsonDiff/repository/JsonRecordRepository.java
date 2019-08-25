package com.dzuniga.JsonDiff.repository;

import java.util.Optional;

/**
 * The database abstraction used to get and save json records
 */
public interface JsonRecordRepository {

    /**
     * insert and update the provided record
     * @param record JsonRecord that contains Id, left and right string
     */
    void save(JsonRecord record);

    /**
     * retrieves the JsonRecord by identifier
     * @param id JsonRecord identifier
     * @return Optional of JsonRecord that contains Id, left and right string
     */
    Optional<JsonRecord> getById(Long id);
}
