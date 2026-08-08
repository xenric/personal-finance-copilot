package com.personalfinancecopilot.domain.goal;

import com.personalfinancecopilot.domain.common.Money;

import java.util.Objects;

/**
 * 금액으로 측정하는 목표의 목표값이다.
 */
public record MoneyGoalTarget(Money amount) implements GoalTarget {

    public MoneyGoalTarget {
        amount = Objects.requireNonNull(amount, "amount must not be null").requireNonNegative();
    }

    @Override
    public boolean supports(FinancialGoalType goalType) {
        return goalType == FinancialGoalType.EMERGENCY_FUND
                || goalType == FinancialGoalType.NET_WORTH
                || goalType == FinancialGoalType.DEBT_REDUCTION
                || goalType == FinancialGoalType.TARGET_AMOUNT_BY_DATE;
    }
}
