package com.personalfinancecopilot.domain.profile;

/**
 * 프로필과 위험 분석에 사용하는 사용자의 예상 투자 기간이다.
 */
public enum InvestmentHorizon {
    /**
     * 예상 투자 기간이 2년 미만이다.
     */
    LESS_THAN_TWO_YEARS,

    /**
     * 예상 투자 기간이 2년 이상 5년 미만이다.
     */
    TWO_TO_FIVE_YEARS,

    /**
     * 예상 투자 기간이 5년 이상이다.
     */
    FIVE_YEARS_OR_MORE
}
