package com.dzuniga.JsonDiff.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.dzuniga.JsonDiff.TestUtils;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class RestErrorTest {

    @Test
    public void testEquality() {
        RestError alpha = RestError.builder().message("message").status(HttpStatus.ACCEPTED).build();
        RestError beta = RestError.builder().message("message").status(HttpStatus.ACCEPTED).build();
        RestError gamma = RestError.builder().message("message").status(HttpStatus.ACCEPTED).build();
        RestError delta = RestError.builder().message("other message").status(HttpStatus.NOT_FOUND).build();

        TestUtils.assertEqualsContract(alpha, beta, gamma, delta);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenStatusIsNull() {
        RestError.builder().message("random").build();
    }

    @Test
    public void testToString() {
        //language=JSON
        final String expected = "{\"status\":\"202 ACCEPTED\",\"message\":\"message\"}";
        final RestError sut = RestError.builder().message("message").status(HttpStatus.ACCEPTED).build();
        assertThat(sut.toString()).isEqualTo(expected);
    }
}