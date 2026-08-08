package com.personalfinancecopilot.domain.goal;

/**
 * 사용자가 관리하는 재무 목표의 생명주기 상태다.
 */
public enum FinancialGoalStatus {
    /**
     * 현재 진행 중인 목표다.
     */
    ACTIVE,

    /**
     * 달성된 목표다.
     */
    ACHIEVED,

    /**
     * 일시 중단된 목표다.
     */
    PAUSED,

    /**
     * 취소된 목표다.
     */
    CANCELLED
}
