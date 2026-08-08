package com.personalfinancecopilot.domain.goal;

import com.personalfinancecopilot.domain.asset.AssetType;
import com.personalfinancecopilot.domain.common.Percentage;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

/**
 * 전체 합이 100%여야 하는 자산배분 목표값이다.
 */
public record AssetAllocationGoalTarget(Map<AssetType, Percentage> allocation) implements GoalTarget {

    public AssetAllocationGoalTarget {
        Objects.requireNonNull(allocation, "allocation must not be null");
        if (allocation.isEmpty()) {
            throw new IllegalArgumentException("allocation must not be empty");
        }
        allocation.forEach((assetType, percentage) -> {
            Objects.requireNonNull(assetType, "allocation assetType must not be null");
            Objects.requireNonNull(percentage, "allocation percentage must not be null").requireZeroToOne();
        });
        BigDecimal sum = allocation.values().stream()
                .map(Percentage::decimalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (sum.compareTo(BigDecimal.ONE) != 0) {
            throw new IllegalArgumentException("allocation total must be 100 percent");
        }
        allocation = Map.copyOf(allocation);
    }

    @Override
    public boolean supports(FinancialGoalType goalType) {
        return goalType == FinancialGoalType.ASSET_ALLOCATION;
    }
}
