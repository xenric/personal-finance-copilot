package com.personalfinancecopilot.domain.goal;

import com.personalfinancecopilot.domain.common.Percentage;

import java.util.Objects;

/**
 * 비율로 측정하는 목표의 목표값이다.
 */
public record PercentageGoalTarget(Percentage percentage) implements GoalTarget {

    public PercentageGoalTarget {
        percentage = Objects.requireNonNull(percentage, "percentage must not be null").requireZeroToOne();
    }

    @Override
    public boolean supports(FinancialGoalType goalType) {
        return goalType == FinancialGoalType.SAVINGS_RATE;
    }
}
