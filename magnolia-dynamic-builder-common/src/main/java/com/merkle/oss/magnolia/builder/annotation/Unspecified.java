package com.merkle.oss.magnolia.builder.annotation;

import java.util.Optional;
import java.util.function.Predicate;

public abstract class Unspecified {
    public static final String STRING = "<dynamicBuilder-common-undefined>";
    public static final int INT = -1;

    public static Optional<Integer> getValue(final int value) {
        return Optional.of(value).filter(v -> Unspecified.INT != v);
    }
    public static Optional<String> getValue(final String value) {
        return Optional.of(value).filter(Predicate.not(Unspecified.STRING::equals));
    }
}
