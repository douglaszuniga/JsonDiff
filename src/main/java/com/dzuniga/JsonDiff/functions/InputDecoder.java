package com.dzuniga.JsonDiff.functions;

@FunctionalInterface
public interface InputDecoder {
    String decode(String input);
}
