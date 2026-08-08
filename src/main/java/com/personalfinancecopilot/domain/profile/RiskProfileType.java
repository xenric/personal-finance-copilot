package com.personalfinancecopilot.domain.profile;

/**
 * 결정론적으로 산정한 MVP 투자 성향 결과다.
 */
public enum RiskProfileType {
    /**
     * 안정성과 유동성을 우선하는 투자 성향이다.
     */
    CONSERVATIVE,

    /**
     * 안정성과 장기 성장의 균형을 우선하는 투자 성향이다.
     */
    BALANCED,

    /**
     * 장기 성장과 변동성 감수를 우선하는 투자 성향이다.
     */
    GROWTH
}
