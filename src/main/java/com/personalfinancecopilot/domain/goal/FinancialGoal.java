package com.personalfinancecopilot.domain.goal;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 분석과 진행률 계산에서 평가할 수 있는 사용자 정의 재무 목표다.
 */
public record FinancialGoal(
        String id,
        FinancialGoalType type,
        GoalTarget targetValue,
        LocalDate targetDate,
        FinancialGoalStatus status
) {

    public FinancialGoal {
        id = requireText(id, "id");
        Objects.requireNonNull(type, "type must not be null");
        Objects.requireNonNull(targetValue, "targetValue must not be null");
        Objects.requireNonNull(targetDate, "targetDate must not be null");
        Objects.requireNonNull(status, "status must not be null");
        if (!targetValue.supports(type)) {
            throw new IllegalArgumentException("targetValue does not support goal type");
        }
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
