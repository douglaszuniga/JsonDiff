package com.dzuniga.JsonDiff.functions;

import com.dzuniga.JsonDiff.repository.JsonRecord;
import com.dzuniga.JsonDiff.repository.JsonRecord.JsonRecordBuilder;
import com.dzuniga.JsonDiff.repository.JsonRecordRepository;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class DefaultInputConsumer implements InputConsumer {

    private final InputDecoder inputDecoder;
    private final JsonRecordRepository repository;

    public DefaultInputConsumer(InputDecoder inputDecoder, JsonRecordRepository repository) {
        Objects.requireNonNull(inputDecoder, "Input decoder must not be null");
        Objects.requireNonNull(repository, "Json Record repository must not be null");

        this.inputDecoder = inputDecoder;
        this.repository = repository;
    }

    @Override
    public void apply(Long id, String input, boolean isLeft) {
        Objects.requireNonNull(id, "Id must not be null");
        Objects.requireNonNull(input, "Input must not be null");

        String decodedInput = inputDecoder.decode(input);
        JsonRecordBuilder builder = JsonRecord.builder().id(id);
        if (isLeft) {
            builder.left(decodedInput);
        } else {
            builder.right(decodedInput);
        }
        repository.save(builder.build());
    }
}
