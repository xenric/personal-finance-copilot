package com.personalfinancecopilot.domain.state;

import com.personalfinancecopilot.domain.asset.Asset;
import com.personalfinancecopilot.domain.asset.AssetType;
import com.personalfinancecopilot.domain.common.Money;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CurrentFinancialStateTest {

    @Test
    void rejectsDuplicateAssetIds() {
        Asset cash = new Asset("asset-1", "Cash", AssetType.CASH, Money.of("1000", "KRW"), LocalDate.now(), null);
        MonthlyCashFlow cashFlow = new MonthlyCashFlow(
                Money.of("1000", "KRW"),
                Money.zero("KRW"),
                Money.zero("KRW"),
                Money.zero("KRW"),
                Money.zero("KRW")
        );

        assertThatThrownBy(() -> new CurrentFinancialState(
                LocalDate.now(),
                List.of(cash, cash),
                List.of(),
                cashFlow,
                List.of(),
                Instant.now()
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("asset id must be unique");
    }
}
