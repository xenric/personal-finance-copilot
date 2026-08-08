package com.personalfinancecopilot.domain.goal;

/**
 * 재무 목표의 단위가 있는 목표값이다.
 */
public sealed interface GoalTarget permits MoneyGoalTarget, PercentageGoalTarget, AssetAllocationGoalTarget {

    boolean supports(FinancialGoalType goalType);
}
