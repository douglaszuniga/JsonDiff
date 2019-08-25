package com.dzuniga.JsonDiff.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.dzuniga.JsonDiff.TestUtils;
import org.junit.Test;

public class JsonRecordTest {

    @Test
    public void testEquality() {
        final JsonRecord alpha = JsonRecord.builder().right("1").left("2").id(0L).build();
        final JsonRecord beta = JsonRecord.builder().right("1").left("2").id(0L).build();
        final JsonRecord gamma = JsonRecord.builder().right("1").left("2").id(0L).build();
        final JsonRecord delta = JsonRecord.builder().right("1").left("23").id(1L).build();

        TestUtils.assertEqualsContract(alpha, beta, gamma, delta);
    }

    @Test
    public void testToString() {
        //language=JSON
        final String expected = "{\"Id\":0,\"left\":\"2\",\"right\":\"1\"}";
        final JsonRecord sut = JsonRecord.builder().right("1").left("2").id(0L).build();
        assertThat(sut.toString()).isEqualTo(expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenIdIsNegative() {
        JsonRecord.builder().right("1").left("2").id(-1L).build();
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenIdIsNegative() {
        JsonRecord.builder().right("1").left("2").id(null).build();
    }

    @Test
    public void shouldReturnACopyWhenFactoryMethodIsUsed() {
        JsonRecord original = JsonRecord.builder().right("1").left("2").id(0L).build();
        JsonRecord copy = JsonRecord.from(original).build();

        assertThat(copy).isNotNull();
        assertThat(copy).isEqualTo(original);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenRecordIsNull() {
        JsonRecord.from(null).build();
    }
}