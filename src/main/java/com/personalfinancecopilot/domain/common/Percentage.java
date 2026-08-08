package com.personalfinancecopilot.domain.common;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

/**
 * 15%를 0.15처럼 소수값으로 보관하는 비율 값이다.
 */
public record Percentage(BigDecimal decimalValue) {

    public static final Percentage ZERO = Percentage.ofDecimal("0");
    public static final Percentage ONE = Percentage.ofDecimal("1");

    public Percentage {
        Objects.requireNonNull(decimalValue, "decimalValue must not be null");
    }

    public static Percentage ofDecimal(String decimalValue) {
        return new Percentage(new BigDecimal(decimalValue));
    }

    public static Percentage ofPercent(String percentValue) {
        return new Percentage(new BigDecimal(percentValue).divide(BigDecimal.valueOf(100), MathContext.DECIMAL128));
    }

    public Percentage requireRange(BigDecimal minInclusive, BigDecimal maxInclusive) {
        Objects.requireNonNull(minInclusive, "minInclusive must not be null");
        Objects.requireNonNull(maxInclusive, "maxInclusive must not be null");
        if (decimalValue.compareTo(minInclusive) < 0 || decimalValue.compareTo(maxInclusive) > 0) {
            throw new IllegalArgumentException("percentage must be within the allowed range");
        }
        return this;
    }

    public Percentage requireZeroToOne() {
        return requireRange(BigDecimal.ZERO, BigDecimal.ONE);
    }

    public Percentage requireNonNegative() {
        if (decimalValue.signum() < 0) {
            throw new IllegalArgumentException("percentage must not be negative");
        }
        return this;
    }
}
