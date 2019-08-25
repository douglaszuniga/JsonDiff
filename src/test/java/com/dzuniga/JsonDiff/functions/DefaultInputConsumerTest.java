package com.dzuniga.JsonDiff.functions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dzuniga.JsonDiff.repository.JsonRecord;
import com.dzuniga.JsonDiff.repository.JsonRecordRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultInputConsumerTest {

    @Mock private InputDecoder inputDecoder;
    @Mock private JsonRecordRepository repository;
    private InputConsumer inputConsumer;

    @Before
    public void setUp() {
        inputConsumer = new DefaultInputConsumer(inputDecoder, repository);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenDecoderIsNull() {
        new DefaultInputConsumer(null, repository);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenRepoIsNull() {
        new DefaultInputConsumer(inputDecoder, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenIdIsNull() {
        inputConsumer.apply(null, "random", false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenIdIsNegative() {
        inputConsumer.apply(-1L, "random", false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenInputIsBlank() {
        inputConsumer.apply(1L, "", false);
    }

    @Test
    public void shouldConsumeLeftInputSuccessFullyWithNoPreviousRecord() {
        ArgumentCaptor<JsonRecord> captor = ArgumentCaptor.forClass(JsonRecord.class);
        when(repository.getById(anyLong())).thenReturn(Optional.empty());
        when(inputDecoder.decode(anyString())).thenReturn("aa");

        inputConsumer.apply(1L, "YWE=", true);

        verify(inputDecoder).decode(anyString());
        verify(repository).save(captor.capture());

        JsonRecord value = captor.getValue();

        assertThat(value.getId()).isEqualTo(1L);
        assertThat(value.getLeft()).isEqualTo("aa");
        assertThat(value.getRight()).isNull();
    }

    @Test
    public void shouldConsumeRightInputSuccessFullyWithPreviousRecord() {
        ArgumentCaptor<JsonRecord> captor = ArgumentCaptor.forClass(JsonRecord.class);
        when(repository.getById(anyLong())).thenReturn(Optional.of(JsonRecord.builder().id(1L).left("aa").build()));
        when(inputDecoder.decode(anyString())).thenReturn("bb");

        inputConsumer.apply(1L, "YmI=", false);

        verify(inputDecoder).decode(anyString());
        verify(repository).save(captor.capture());

        JsonRecord value = captor.getValue();

        assertThat(value.getId()).isEqualTo(1L);
        assertThat(value.getLeft()).isEqualTo("aa");
        assertThat(value.getRight()).isEqualTo("bb");
    }
}