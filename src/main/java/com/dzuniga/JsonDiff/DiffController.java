package com.dzuniga.JsonDiff;

import com.dzuniga.JsonDiff.functions.InputConsumer;
import com.dzuniga.JsonDiff.functions.JsonDiffProcessor;
import com.dzuniga.JsonDiff.model.DiffResult;
import java.util.Objects;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PutMapping(path = "/{id}/left")
    public void handleLeft(@PathVariable Long id, @RequestBody String input) {
        inputConsumer.apply(id, input, true);
    }

    @PutMapping(path = "/{id}/right")
    public void handleRight(@PathVariable Long id, @RequestBody String input) {
        inputConsumer.apply(id, input, false);
    }

    @PutMapping(path = "/id")
    public DiffResult getDiff(@PathVariable Long id) {
        return diffProcessor.apply(id);
    }
}
