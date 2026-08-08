package com.personalfinancecopilot.domain.state;

import com.personalfinancecopilot.domain.common.Money;

import java.util.Objects;

/**
 * 월 소득, 지출, 상환액, 투자 가능 금액 입력값이다.
 */
public record MonthlyCashFlow(
        Money monthlyIncome,
        Money monthlyFixedExpense,
        Money monthlyVariableExpense,
        Money monthlyDebtPayment,
        Money availableMonthlyInvestment
) {

    public MonthlyCashFlow {
        monthlyIncome = Objects.requireNonNull(monthlyIncome, "monthlyIncome must not be null").requireNonNegative();
        monthlyFixedExpense = Objects.requireNonNull(monthlyFixedExpense, "monthlyFixedExpense must not be null")
                .requireNonNegative();
        monthlyVariableExpense = Objects.requireNonNull(monthlyVariableExpense, "monthlyVariableExpense must not be null")
                .requireNonNegative();
        monthlyDebtPayment = Objects.requireNonNull(monthlyDebtPayment, "monthlyDebtPayment must not be null")
                .requireNonNegative();
        availableMonthlyInvestment = Objects.requireNonNull(availableMonthlyInvestment,
                "availableMonthlyInvestment must not be null").requireNonNegative();
    }
}
