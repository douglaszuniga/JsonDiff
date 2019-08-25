package com.dzuniga.JsonDiff.functions;

import static org.assertj.core.api.Assertions.assertThat;

import com.dzuniga.JsonDiff.exception.InputNonValidException;
import org.junit.Test;

public class DefaultInputDecoderTest {

    private InputDecoder decoder = new DefaultInputDecoder();

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenInputIsNull() {
        decoder.decode(null);
    }

    @Test(expected = InputNonValidException.class)
    public void shouldThrowInputNonValidExceptionWhenInputIsNotValidBase64String() {
        decoder.decode("sdasdjkkjs(()93");
    }

    @Test
    public void shouldDecodeSuccessfully() {
        String expected = "aa";
        String actual = decoder.decode("YWE=");

        assertThat(actual).isEqualTo(expected);
    }
}