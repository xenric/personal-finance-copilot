package com.personalfinancecopilot.domain.state;

import com.personalfinancecopilot.domain.asset.Asset;
import com.personalfinancecopilot.domain.goal.FinancialGoal;
import com.personalfinancecopilot.domain.liability.Liability;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * 지표 계산과 불변 스냅샷 생성을 위해 사용하는 수정 가능한 현재 재무 입력값이다.
 */
public record CurrentFinancialState(
        LocalDate asOfDate,
        List<Asset> assets,
        List<Liability> liabilities,
        MonthlyCashFlow cashFlow,
        List<FinancialGoal> goals,
        Instant updatedAt
) {

    public CurrentFinancialState {
        Objects.requireNonNull(asOfDate, "asOfDate must not be null");
        assets = List.copyOf(Objects.requireNonNull(assets, "assets must not be null"));
        liabilities = List.copyOf(Objects.requireNonNull(liabilities, "liabilities must not be null"));
        cashFlow = Objects.requireNonNull(cashFlow, "cashFlow must not be null");
        goals = List.copyOf(Objects.requireNonNull(goals, "goals must not be null"));
        Objects.requireNonNull(updatedAt, "updatedAt must not be null");
        requireUniqueAssetIds(assets);
        requireUniqueLiabilityIds(liabilities);
        requireUniqueGoalIds(goals);
    }

    private static void requireUniqueAssetIds(List<Asset> assets) {
        HashSet<String> ids = new HashSet<>();
        for (Asset asset : assets) {
            Objects.requireNonNull(asset, "asset must not be null");
            if (!ids.add(asset.id())) {
                throw new IllegalArgumentException("asset id must be unique");
            }
        }
    }

    private static void requireUniqueLiabilityIds(List<Liability> liabilities) {
        HashSet<String> ids = new HashSet<>();
        for (Liability liability : liabilities) {
            Objects.requireNonNull(liability, "liability must not be null");
            if (!ids.add(liability.id())) {
                throw new IllegalArgumentException("liability id must be unique");
            }
        }
    }

    private static void requireUniqueGoalIds(List<FinancialGoal> goals) {
        HashSet<String> ids = new HashSet<>();
        for (FinancialGoal goal : goals) {
            Objects.requireNonNull(goal, "goal must not be null");
            if (!ids.add(goal.id())) {
                throw new IllegalArgumentException("goal id must be unique");
            }
        }
    }
}
