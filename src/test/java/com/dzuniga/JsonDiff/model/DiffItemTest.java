package com.dzuniga.JsonDiff.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.dzuniga.JsonDiff.TestUtils;
import org.junit.Test;

public class DiffItemTest {

    @Test
    public void testEquality() {
        DiffItem alpha = DiffItem.builder().offset(1).length(20).build();
        DiffItem beta = DiffItem.builder().offset(1).length(20).build();
        DiffItem gamma = DiffItem.builder().offset(1).length(20).build();
        DiffItem delta = DiffItem.builder().offset(20).length(1).build();

        TestUtils.assertEqualsContract(alpha, beta, gamma, delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenOffsetIfNegative() {
        DiffItem.builder().offset(-1).length(20).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenLengthIfNegative() {
        DiffItem.builder().offset(1).length(-20).build();
    }

    @Test
    public void testToString() {
        //language=JSON
        final String expected = "{\"offset\":1,\"length\":20}";
        final DiffItem sut = DiffItem.builder().offset(1).length(20).build();
        assertThat(sut.toString()).isEqualTo(expected);
    }
}