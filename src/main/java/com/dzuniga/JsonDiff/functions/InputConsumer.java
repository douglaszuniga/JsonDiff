package com.dzuniga.JsonDiff.functions;

@FunctionalInterface
public interface InputConsumer {
    void apply(Long id, String input, boolean isLeft);
}
