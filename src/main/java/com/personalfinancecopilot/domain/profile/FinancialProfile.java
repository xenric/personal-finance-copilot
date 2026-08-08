package com.personalfinancecopilot.domain.profile;

import com.personalfinancecopilot.domain.asset.AssetType;
import com.personalfinancecopilot.domain.common.Percentage;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.Objects;

/**
 * 현재 자산과 부채와 분리된 사용자의 재무 분석 기준 설정이다.
 */
public record FinancialProfile(
        Currency baseCurrency,
        InvestmentHorizon investmentHorizon,
        RiskProfileType riskTolerance,
        int emergencyFundTargetMonths,
        Map<AssetType, Percentage> targetAssetAllocation
) {

    public FinancialProfile {
        Objects.requireNonNull(baseCurrency, "baseCurrency must not be null");
        Objects.requireNonNull(investmentHorizon, "investmentHorizon must not be null");
        Objects.requireNonNull(riskTolerance, "riskTolerance must not be null");
        if (emergencyFundTargetMonths <= 0) {
            throw new IllegalArgumentException("emergencyFundTargetMonths must be greater than zero");
        }
        targetAssetAllocation = validateAllocation(targetAssetAllocation);
    }

    private static Map<AssetType, Percentage> validateAllocation(Map<AssetType, Percentage> allocation) {
        Objects.requireNonNull(allocation, "targetAssetAllocation must not be null");
        if (allocation.isEmpty()) {
            throw new IllegalArgumentException("targetAssetAllocation must not be empty");
        }
        allocation.forEach((assetType, percentage) -> {
            Objects.requireNonNull(assetType, "targetAssetAllocation assetType must not be null");
            Objects.requireNonNull(percentage, "targetAssetAllocation percentage must not be null").requireZeroToOne();
        });
        BigDecimal sum = allocation.values().stream()
                .map(Percentage::decimalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (sum.compareTo(BigDecimal.ONE) != 0) {
            throw new IllegalArgumentException("targetAssetAllocation total must be 100 percent");
        }
        return Map.copyOf(allocation);
    }
}
