package com.dzuniga.JsonDiff.functions;

import com.dzuniga.JsonDiff.exception.InputNonValidException;
import java.util.Base64;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultInputDecoder implements InputDecoder {

    @Override
    public String decode(String input) {
        Objects.requireNonNull(input, "The input  must not be null");
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(input);
            String decodedInput = new String(decodedBytes);
            log.trace("The decoded input is: [{}]", decodedInput);
            return decodedInput;
        } catch (IllegalArgumentException e) {
            throw new InputNonValidException(input);
        }
    }
}
