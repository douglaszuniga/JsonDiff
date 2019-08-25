package com.dzuniga.JsonDiff.functions;

import com.dzuniga.JsonDiff.exception.InputNonValidException;
import java.util.Base64;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class DefaultInputDecoder implements InputDecoder {

    @Override
    public String decode(String input) {
        Objects.requireNonNull(input, "The input  must not be null");
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(input);
            return new String(decodedBytes);
        } catch (IllegalArgumentException e) {
            throw new InputNonValidException(input);
        }
    }
}
