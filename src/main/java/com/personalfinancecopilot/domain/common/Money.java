package com.personalfinancecopilot.domain.common;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

/**
 * 금액과 통화를 항상 함께 보관하는 금액 값이다.
 */
public record Money(BigDecimal amount, Currency currency) {

    public Money {
        Objects.requireNonNull(amount, "amount must not be null");
        Objects.requireNonNull(currency, "currency must not be null");
    }

    public static Money of(String amount, String currencyCode) {
        return new Money(new BigDecimal(amount), Currency.getInstance(currencyCode));
    }

    public static Money zero(String currencyCode) {
        return of("0", currencyCode);
    }

    public Money requireNonNegative() {
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("amount must not be negative");
        }
        return this;
    }

    public Money add(Money other) {
        requireSameCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }

    public Money subtract(Money other) {
        requireSameCurrency(other);
        return new Money(amount.subtract(other.amount), currency);
    }

    private void requireSameCurrency(Money other) {
        Objects.requireNonNull(other, "other must not be null");
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("money currency must match");
        }
    }
}
