package com.personalfinancecopilot.domain.asset;

/**
 * MVP 재무 상태에서 지원하는 자산 분류다.
 */
public enum AssetType {
    /**
     * 즉시 사용할 수 있는 현금이다.
     */
    CASH,

    /**
     * 은행 예금처럼 원금 보관 목적의 자산이다.
     */
    DEPOSIT,

    /**
     * 정기 적금처럼 일정 기간 납입하는 저축성 자산이다.
     */
    SAVINGS,

    /**
     * 개별 주식 투자상품이다.
     */
    STOCK,

    /**
     * 상장지수펀드 투자상품이다.
     */
    ETF,

    /**
     * 채권 투자상품이다.
     */
    BOND,

    /**
     * 금 또는 금 관련 자산이다.
     */
    GOLD,

    /**
     * 주택, 토지 등 부동산 자산이다.
     */
    REAL_ESTATE,

    /**
     * 위 분류에 속하지 않는 기타 자산이다.
     */
    OTHER
}
