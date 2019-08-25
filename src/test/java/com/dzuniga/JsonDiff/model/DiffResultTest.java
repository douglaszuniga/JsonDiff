package com.dzuniga.JsonDiff.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.dzuniga.JsonDiff.TestUtils;
import java.util.List;
import org.junit.Test;

public class DiffResultTest {
    @Test
    public void testEquality() {
        DiffItem item = DiffItem.builder().offset(1).length(20).build();
        DiffResult alpha = DiffResult.builder().insight(List.of(item)).result(Result.EQUAL).build();
        DiffResult beta = DiffResult.builder().insight(List.of(item)).result(Result.EQUAL).build();
        DiffResult gamma = DiffResult.builder().insight(List.of(item)).result(Result.EQUAL).build();
        DiffResult delta = DiffResult.builder().result(Result.NOT_EQUAL_SIZE).build();

        TestUtils.assertEqualsContract(alpha, beta, gamma, delta);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenResultIsNull() {
        DiffResult.builder().build();
    }

    @Test
    public void testToString() {
        DiffItem item = DiffItem.builder().offset(1).length(20).build();
        //language=JSON
        final String expected = "{\"result\":\"EQUAL\",\"insight\":[{\"offset\":1,\"length\":20}]}";
        final DiffResult sut = DiffResult.builder().insight(List.of(item)).result(Result.EQUAL).build();
        assertThat(sut.toString()).isEqualTo(expected);
    }
}