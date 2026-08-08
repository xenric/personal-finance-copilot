package com.personalfinancecopilot.domain.goal;

import com.personalfinancecopilot.domain.asset.AssetType;
import com.personalfinancecopilot.domain.common.Money;
import com.personalfinancecopilot.domain.common.Percentage;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FinancialGoalTest {

    @Test
    void acceptsMoneyTargetForTargetAmountByDateGoal() {
        FinancialGoal goal = new FinancialGoal(
                "goal-1",
                FinancialGoalType.TARGET_AMOUNT_BY_DATE,
                new MoneyGoalTarget(Money.of("10000000", "KRW")),
                LocalDate.parse("2028-12-31"),
                FinancialGoalStatus.ACTIVE
        );

        assertThat(goal.id()).isEqualTo("goal-1");
    }

    @Test
    void acceptsAllocationTargetForAssetAllocationGoal() {
        FinancialGoal goal = new FinancialGoal(
                "goal-1",
                FinancialGoalType.ASSET_ALLOCATION,
                new AssetAllocationGoalTarget(Map.of(
                        AssetType.CASH, Percentage.ofDecimal("0.3"),
                        AssetType.ETF, Percentage.ofDecimal("0.7")
                )),
                LocalDate.parse("2028-12-31"),
                FinancialGoalStatus.ACTIVE
        );

        assertThat(goal.type()).isEqualTo(FinancialGoalType.ASSET_ALLOCATION);
    }

    @Test
    void rejectsUnsupportedTargetForGoalType() {
        assertThatThrownBy(() -> new FinancialGoal(
                "goal-1",
                FinancialGoalType.SAVINGS_RATE,
                new MoneyGoalTarget(Money.of("1000000", "KRW")),
                LocalDate.parse("2028-12-31"),
                FinancialGoalStatus.ACTIVE
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("targetValue does not support goal type");
    }
}
