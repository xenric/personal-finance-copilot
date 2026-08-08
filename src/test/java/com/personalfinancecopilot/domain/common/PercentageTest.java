package com.personalfinancecopilot.domain.common;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PercentageTest {

    @Test
    void createsPercentageFromPercentDisplayValue() {
        Percentage percentage = Percentage.ofPercent("15");

        assertThat(percentage.decimalValue()).isEqualByComparingTo("0.15");
    }

    @Test
    void validatesZeroToOneRange() {
        assertThat(Percentage.ofDecimal("1").requireZeroToOne()).isEqualTo(Percentage.ONE);

        assertThatThrownBy(() -> Percentage.ofDecimal("1.01").requireZeroToOne())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("percentage must be within the allowed range");
    }

    @Test
    void rejectsNegativePercentageWhenNonNegativeValueIsRequired() {
        assertThatThrownBy(() -> Percentage.ofDecimal("-0.01").requireNonNegative())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("percentage must not be negative");
    }

    @Test
    void supportsExplicitRangeValidation() {
        assertThat(Percentage.ofDecimal("0.4").requireRange(BigDecimal.ZERO, BigDecimal.ONE))
                .isEqualTo(Percentage.ofDecimal("0.4"));
    }
}
