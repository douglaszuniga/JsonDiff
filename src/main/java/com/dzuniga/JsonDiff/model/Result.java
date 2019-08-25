package com.dzuniga.JsonDiff.model;

/**
 * equal: both strings are exactly the same
 * not equal size: on is bigger
 * equal size: different strings but equal size
 */
public enum Result {
    EQUAL, NOT_EQUAL_SIZE, EQUAL_SIZE
}
