package com.dzuniga.JsonDiff.functions;

/**
 * Responsible of decoding the base64 string
 */
@FunctionalInterface
public interface InputDecoder {

    /**
     * decodes the base64 string, making sure that the input is a valid string
     *
     * Throws NullPointerException or IllegalArgumentException when the arguments don't pass the validation
     * Throws InputNonValidException when the input is not valid base64
     * @param input valid base64 string representing a JSON
     * @return the decoded string
     */
    String decode(String input);
}
