package com.merkle.oss.magnolia.builder.annotation;

public enum TernaryBoolean {
    TRUE(true),
    FALSE(false),
    UNSPECIFIED(null);

    private final Boolean value;

    TernaryBoolean(final Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }
}