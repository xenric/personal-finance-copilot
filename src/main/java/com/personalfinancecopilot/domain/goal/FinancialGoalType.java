package com.personalfinancecopilot.domain.goal;

/**
 * MVP 분석에서 지원하는 재무 목표 분류다.
 */
public enum FinancialGoalType {
    /**
     * 생활비를 감당할 비상자금을 확보하는 목표다.
     */
    EMERGENCY_FUND,

    /**
     * 목표 순자산을 달성하는 목표다.
     */
    NET_WORTH,

    /**
     * 목표 월 저축률을 유지하거나 달성하는 목표다.
     */
    SAVINGS_RATE,

    /**
     * 목표 자산배분 비중을 유지하는 목표다.
     */
    ASSET_ALLOCATION,

    /**
     * 목표 부채 수준까지 부채를 줄이는 목표다.
     */
    DEBT_REDUCTION,

    /**
     * 특정 날짜까지 지정한 금액을 마련하는 목적성 목표다.
     */
    TARGET_AMOUNT_BY_DATE
}
