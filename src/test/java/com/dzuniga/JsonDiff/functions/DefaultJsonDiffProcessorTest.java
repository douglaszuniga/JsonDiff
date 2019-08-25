package com.dzuniga.JsonDiff.functions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.dzuniga.JsonDiff.exception.IncompleteJsonRecordException;
import com.dzuniga.JsonDiff.exception.JsonRecordNotFoundException;
import com.dzuniga.JsonDiff.model.DiffResult;
import com.dzuniga.JsonDiff.model.Result;
import com.dzuniga.JsonDiff.repository.JsonRecord;
import com.dzuniga.JsonDiff.repository.JsonRecordRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultJsonDiffProcessorTest {

    @Mock private JsonRecordRepository recordRepository;
    @Mock private JsonDiffChecker diffChecker;
    private JsonDiffProcessor processor;

    @Before
    public void setUp() {
        processor = new DefaultJsonDiffProcessor(recordRepository, diffChecker);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenRepoIsNull() {
        new DefaultJsonDiffProcessor(null, diffChecker);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenCheckerIsNull() {
        new DefaultJsonDiffProcessor(recordRepository, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenIdIsNull() {
        processor.apply(null);
    }

    @Test(expected = JsonRecordNotFoundException.class)
    public void shouldThrowJsonRecordNotFoundExceptionWhenRecordIsNotInDB() {
        when(recordRepository.getById(anyLong())).thenReturn(Optional.empty());
        processor.apply(1L);
    }

    @Test(expected = IncompleteJsonRecordException.class)
    public void shouldThrowIncompleteJsonRecordExceptionWhenEitherLeftOrRightIsNull() {
        JsonRecord record = JsonRecord.builder().id(1L).left("aa").build();
        when(recordRepository.getById(anyLong())).thenReturn(Optional.ofNullable(record));

        processor.apply(1L);
    }

    @Test
    public void shouldReturnDiffResultSuccessfully() {
        JsonRecord record = JsonRecord.builder().id(1L).left("aa").right("aa").build();
        when(recordRepository.getById(anyLong())).thenReturn(Optional.ofNullable(record));

        DiffResult expected = DiffResult.builder().result(Result.EQUAL).build();
        when(diffChecker.check(anyString(), anyString())).thenReturn(expected);

        DiffResult actual = processor.apply(1L);

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }
}