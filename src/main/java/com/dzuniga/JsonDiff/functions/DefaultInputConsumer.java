package com.dzuniga.JsonDiff.functions;

import static com.google.common.base.Preconditions.checkArgument;

import com.dzuniga.JsonDiff.repository.JsonRecord;
import com.dzuniga.JsonDiff.repository.JsonRecord.JsonRecordBuilder;
import com.dzuniga.JsonDiff.repository.JsonRecordRepository;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
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
        checkArgument(id >= 0, "Id must not be negative");
        checkArgument(StringUtils.isNotBlank(input), "Input must not be blank");
        // validates and decodes the input string
        String decodedInput = inputDecoder.decode(input);
        JsonRecord jsonRecord = constructJsonRecord(id, isLeft, decodedInput);
        log.info("Saving JsonRecord with following info: id: [{}] isLeft: [{}] : [{}]", id, isLeft, jsonRecord);
        repository.save(jsonRecord);
    }

    private JsonRecord constructJsonRecord(Long id, boolean isLeft, String decodedInput) {
        JsonRecordBuilder builder = getJsonRecordBuilderFromRepo(id);
        if (isLeft) {
            builder.left(decodedInput);
        } else {
            builder.right(decodedInput);
        }
        return builder.build();
    }

    private JsonRecordBuilder getJsonRecordBuilderFromRepo(Long id) {
        Optional<JsonRecord> recordOptional = repository.getById(id);
        if (recordOptional.isPresent()) {
            log.trace("Found a JsonRecord with id: [{}], [{}]", id, recordOptional.get());
            return JsonRecord.from(recordOptional.get());
        }
        log.trace("Did not find a JsonRecord with id: [{}]", id);
        return JsonRecord.builder().id(id);
    }
}
