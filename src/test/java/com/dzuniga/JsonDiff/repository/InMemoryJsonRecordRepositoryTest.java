package com.dzuniga.JsonDiff.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

public class InMemoryJsonRecordRepositoryTest {

    private JsonRecordRepository jsonRecordRepository;

    @Before
    public void setUp() {
        jsonRecordRepository = new InMemoryJsonRecordRepository();
    }

    @Test
    public void shouldReturnEmptyOptionalWhenNoRecordWithIdsInTheRepo() {
        Optional<JsonRecord> actual = jsonRecordRepository.getById(0L);
        assertThat(actual.isEmpty()).isTrue();
    }

    @Test
    public void shouldReturnJsonRecordWhenRepoContainsTheId() {
        JsonRecord expected = JsonRecord.builder().id(1L).left("test").right("test1").build();
        jsonRecordRepository.save(expected);

        Optional<JsonRecord> actual = jsonRecordRepository.getById(1L);
        assertThat(actual).isNotEmpty();
        assertThat(actual).containsSame(expected);
    }
}