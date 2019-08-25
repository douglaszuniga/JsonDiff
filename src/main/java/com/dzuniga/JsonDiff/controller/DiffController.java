package com.dzuniga.JsonDiff.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.dzuniga.JsonDiff.exception.ValidationException;
import com.dzuniga.JsonDiff.functions.InputConsumer;
import com.dzuniga.JsonDiff.functions.JsonDiffProcessor;
import com.dzuniga.JsonDiff.model.DiffResult;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controls and handles the user requests to the system
 */
@RestController
@RequestMapping(path = "/v1/diff")
public class DiffController {

    private final InputConsumer inputConsumer;
    private final JsonDiffProcessor diffProcessor;

    public DiffController(InputConsumer inputConsumer, JsonDiffProcessor diffProcessor) {
        Objects.requireNonNull(inputConsumer, "Input Consumer must not be null");
        Objects.requireNonNull(diffProcessor, "Diff Processor must not be null");

        this.inputConsumer = inputConsumer;
        this.diffProcessor = diffProcessor;
    }

    /**
     * handles the left input saving the input in the database
     * Throws ValidationException when either the id is negative / null or the input is blank
     *
     * @param id non negative id provided by user
     * @param input valid base64 string representing a JSON
     */
    @PutMapping(path = "/{id}/left")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void handleLeft(@PathVariable Long id, @RequestBody String input) {
        validateId(id);
        validateInput(input);

        inputConsumer.apply(id, input, true);
    }

    /**
     * handles the right input saving the input in the database
     * Throws ValidationException when either the id is negative / null or the input is blank
     *
     * @param id non negative id provided by user
     * @param input valid base64 string representing a JSON
     */
    @PutMapping(path = "/{id}/right")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void handleRight(@PathVariable Long id, @RequestBody String input) {
        validateId(id);
        validateInput(input);

        inputConsumer.apply(id, input, false);
    }

    /**
     * handles the diff request between right and left string
     * Throws ValidationException when either the id is negative / null or the input is blank
     *
     * @param id non negative id provided by user
     * @return Json representation of the DiffResult, Equal, not equal size  and equal size with insights of the differences
     */
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public DiffResult getDiff(@PathVariable Long id) {
        validateId(id);

        return diffProcessor.apply(id);
    }

    private void validateId(@PathVariable Long id) {
        if (id == null || id < 0) {
            throw new ValidationException("Id must not be null or negative");
        }
    }

    private void validateInput(@RequestBody String input) {
        if (StringUtils.isBlank(input)) {
            throw new ValidationException("Input must not be blank");
        }
    }
}
