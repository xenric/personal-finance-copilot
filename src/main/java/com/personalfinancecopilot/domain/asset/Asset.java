package com.personalfinancecopilot.domain.asset;

import com.personalfinancecopilot.domain.common.Money;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 현금, 예금, 주식, 부동산처럼 사용자가 현재 보유한 금전적 가치가 있는 항목이다.
 */
public record Asset(
        String id,
        String name,
        AssetType type,
        Money value,
        LocalDate valuationDate,
        InvestmentDetails investmentDetails
) {

    public Asset {
        id = requireText(id, "id");
        name = requireText(name, "name");
        Objects.requireNonNull(type, "type must not be null");
        value = Objects.requireNonNull(value, "value must not be null").requireNonNegative();
        Objects.requireNonNull(valuationDate, "valuationDate must not be null");
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
