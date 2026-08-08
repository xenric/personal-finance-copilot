package com.personalfinancecopilot.domain.liability;

/**
 * MVP 재무 상태에서 지원하는 부채 분류다.
 */
public enum LiabilityType {
    /**
     * 주택담보대출이다.
     */
    MORTGAGE,

    /**
     * 담보 없이 신용을 기반으로 한 대출이다.
     */
    CREDIT_LOAN,

    /**
     * 자동차 구입 또는 보유와 관련된 대출이다.
     */
    AUTO_LOAN,

    /**
     * 신용카드 사용으로 발생한 미상환 부채다.
     */
    CARD_DEBT,

    /**
     * 위 분류에 속하지 않는 기타 부채다.
     */
    OTHER
}
