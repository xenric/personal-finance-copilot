package com.personalfinancecopilot.domain.asset;

import com.personalfinancecopilot.domain.common.Money;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 수량과 단위 가격을 가진 투자자산의 선택 시장 정보다.
 */
public record InvestmentDetails(
        String symbol,
        BigDecimal quantity,
        Money unitPrice,
        Money averagePurchasePrice,
        String market
) {

    public InvestmentDetails {
        symbol = requireText(symbol, "symbol");
        Objects.requireNonNull(quantity, "quantity must not be null");
        unitPrice = Objects.requireNonNull(unitPrice, "unitPrice must not be null").requireNonNegative();
        if (quantity.signum() < 0) {
            throw new IllegalArgumentException("quantity must not be negative");
        }
        if (averagePurchasePrice != null) {
            averagePurchasePrice = averagePurchasePrice.requireNonNegative();
        }
        if (market != null && market.isBlank()) {
            throw new IllegalArgumentException("market must not be blank when provided");
        }
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
