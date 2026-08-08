package com.personalfinancecopilot.domain.common;

import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Objects;

/**
 * 스냅샷, 보고서, 파일명에 사용하는 월 단위 분석 기간이다.
 */
public record Period(YearMonth yearMonth) {

    public static final ZoneId DEFAULT_ZONE = ZoneId.of("Asia/Seoul");

    public Period {
        Objects.requireNonNull(yearMonth, "yearMonth must not be null");
    }

    public static Period parse(String value) {
        return new Period(YearMonth.parse(value));
    }

    @Override
    public String toString() {
        return yearMonth.toString();
    }
}
