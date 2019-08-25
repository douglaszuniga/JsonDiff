package com.dzuniga.JsonDiff.functions;

/**
 * Responsible of processing the input with the provided id
 */
@FunctionalInterface
public interface InputConsumer {

    /**
     * Processes the input string following this order:
     * - tries to decode the input
     * - saves or updates the id + input (left or  right) in the database
     *
     * Throws NullPointerException or IllegalArgumentException when the arguments don't pass the validation
     * Throws InputNonValidException when the input is not valid base64
     *
     * @param id non negative id provided by user
     * @param input valid base64 string representing a JSON
     * @param isLeft true => is left, false => is right
     */
    void apply(Long id, String input, boolean isLeft);
}
