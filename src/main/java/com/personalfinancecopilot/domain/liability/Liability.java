package com.personalfinancecopilot.domain.liability;

import com.personalfinancecopilot.domain.common.Money;
import com.personalfinancecopilot.domain.common.Percentage;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 미상환 잔액과 선택 부채 조건을 가진 현재 상환 의무다.
 */
public record Liability(
        String id,
        String name,
        LiabilityType type,
        Money outstandingBalance,
        Percentage interestRate,
        LocalDate valuationDate,
        Money monthlyPayment,
        LocalDate maturityDate,
        String institution,
        String note
) {

    public Liability {
        id = requireText(id, "id");
        name = requireText(name, "name");
        Objects.requireNonNull(type, "type must not be null");
        outstandingBalance = Objects.requireNonNull(outstandingBalance, "outstandingBalance must not be null")
                .requireNonNegative();
        Objects.requireNonNull(valuationDate, "valuationDate must not be null");
        if (interestRate != null) {
            interestRate = interestRate.requireNonNegative();
        }
        if (monthlyPayment != null) {
            monthlyPayment = monthlyPayment.requireNonNegative();
        }
        if (institution != null && institution.isBlank()) {
            throw new IllegalArgumentException("institution must not be blank when provided");
        }
        if (note != null && note.isBlank()) {
            throw new IllegalArgumentException("note must not be blank when provided");
        }
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
