package com.personalfinancecopilot.domain.profile;

import com.personalfinancecopilot.domain.asset.AssetType;
import com.personalfinancecopilot.domain.common.Percentage;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.EnumMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FinancialProfileTest {

    @Test
    void createsProfileWhenTargetAllocationTotalsOneHundredPercent() {
        FinancialProfile profile = new FinancialProfile(
                Currency.getInstance("KRW"),
                InvestmentHorizon.FIVE_YEARS_OR_MORE,
                RiskProfileType.BALANCED,
                6,
                allocation(
                        Map.entry(AssetType.CASH, Percentage.ofDecimal("0.2")),
                        Map.entry(AssetType.ETF, Percentage.ofDecimal("0.8"))
                )
        );

        assertThat(profile.baseCurrency()).isEqualTo(Currency.getInstance("KRW"));
    }

    @Test
    void rejectsEmergencyFundTargetThatIsNotPositive() {
        assertThatThrownBy(() -> new FinancialProfile(
                Currency.getInstance("KRW"),
                InvestmentHorizon.FIVE_YEARS_OR_MORE,
                RiskProfileType.BALANCED,
                0,
                allocation(Map.entry(AssetType.CASH, Percentage.ONE))
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("emergencyFundTargetMonths must be greater than zero");
    }

    @Test
    void rejectsTargetAllocationThatDoesNotTotalOneHundredPercent() {
        assertThatThrownBy(() -> new FinancialProfile(
                Currency.getInstance("KRW"),
                InvestmentHorizon.FIVE_YEARS_OR_MORE,
                RiskProfileType.BALANCED,
                6,
                allocation(Map.entry(AssetType.CASH, Percentage.ofDecimal("0.9")))
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("targetAssetAllocation total must be 100 percent");
    }

    @SafeVarargs
    private static Map<AssetType, Percentage> allocation(Map.Entry<AssetType, Percentage>... entries) {
        EnumMap<AssetType, Percentage> allocation = new EnumMap<>(AssetType.class);
        for (Map.Entry<AssetType, Percentage> entry : entries) {
            allocation.put(entry.getKey(), entry.getValue());
        }
        return allocation;
    }
}
