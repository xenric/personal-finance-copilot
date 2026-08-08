package com.personalfinancecopilot.domain.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {

    @Test
    void addsAmountsWithSameCurrency() {
        Money result = Money.of("1000.50", "KRW").add(Money.of("200.25", "KRW"));

        assertThat(result).isEqualTo(Money.of("1200.75", "KRW"));
    }

    @Test
    void rejectsArithmeticWithDifferentCurrencies() {
        assertThatThrownBy(() -> Money.of("100", "KRW").add(Money.of("1", "USD")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("money currency must match");
    }

    @Test
    void rejectsNegativeAmountWhenNonNegativeValueIsRequired() {
        assertThatThrownBy(() -> Money.of("-1", "KRW").requireNonNegative())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("amount must not be negative");
    }
}
