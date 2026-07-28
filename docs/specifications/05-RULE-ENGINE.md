# Rule Engine Specification

## 1. 문서 목적

이 문서는 Personal Finance Copilot MVP에서 규칙 기반으로 생성할 경고와 평가 기준을 정의한다.

AI는 경고를 확정적으로 생성하지 않는다. AI는 규칙 엔진이 생성한 경고를 사용자에게 이해하기 쉽게 설명하는 역할만 한다.

---

## 2. 공통 원칙

- 모든 경고는 명시적인 `ruleId`를 가진다.
- 경고에는 실제값, 기준값, 평가 사유를 포함한다.
- 데이터가 부족하면 `NOT_EVALUATED`로 처리한다.
- 경고는 투자 명령이 아니라 확인이 필요한 상태를 나타낸다.
- 임계값은 한 곳에서 관리하고 버전을 기록한다.

---

## 3. 평가 상태

- `TRIGGERED`: 경고 조건에 해당한다.
- `NOT_TRIGGERED`: 평가했지만 경고 조건에 해당하지 않는다.
- `NOT_EVALUATED`: 필요한 데이터가 부족해 평가하지 못했다.

---

## 4. 중요도

- `INFO`
- `LOW`
- `MEDIUM`
- `HIGH`

---

## 5. MVP 규칙 목록

| ruleId | 이름 | 기본 기준 | 중요도 |
| ------ | ---- | --------- | ------ |
| EMERGENCY_FUND_LOW | 비상자금 부족 | 목표 개월 수 미만 | HIGH |
| DEBT_RATIO_HIGH | 부채 비율 높음 | 총부채 / 총자산 >= 0.4 | MEDIUM |
| HIGH_INTEREST_DEBT | 고금리 부채 보유 | 연 이자율 >= 0.08 | HIGH |
| SAVINGS_RATE_LOW | 저축률 낮음 | 월 저축률 < 0.1 | MEDIUM |
| SINGLE_ASSET_CONCENTRATION | 단일 상품 집중 | 단일 투자상품 비중 >= 0.3 | MEDIUM |
| TARGET_ALLOCATION_DEVIATION | 목표 자산배분 이탈 | 목표 대비 10%p 이상 차이 | MEDIUM |
| RISK_PROFILE_MISMATCH | 투자 성향과 자산구성 불일치 | 성향별 허용 범위 이탈 | MEDIUM |
| SHORT_TERM_GOAL_RISK_EXPOSURE | 단기 목표자금 위험자산 노출 | 2년 이내 목표자금의 위험자산 비중 과다 | HIGH |
| MONTHLY_CAPACITY_SHORTFALL | 목표 대비 월 투자 가능 금액 부족 | 필요 적립액 > 월 투자 가능 금액 | HIGH |

---

## 6. 규칙 상세

### EMERGENCY_FUND_LOW

필요 데이터:

- 비상자금 충당 개월 수
- 목표 비상자금 개월 수

평가:

```text
triggered = emergencyFundMonths < emergencyFundTargetMonths
```

### DEBT_RATIO_HIGH

필요 데이터:

- 총자산
- 총부채

평가:

```text
triggered = debtRatio >= 0.4
```

### HIGH_INTEREST_DEBT

필요 데이터:

- 부채별 적용 금리
- 부채별 미상환 잔액

평가:

```text
triggered = any(liability.interestRate >= 0.08 and outstandingBalance > 0)
```

### SAVINGS_RATE_LOW

필요 데이터:

- 월 저축률

평가:

```text
triggered = savingsRate < 0.1
```

### SINGLE_ASSET_CONCENTRATION

필요 데이터:

- 투자자산별 평가금액
- 전체 투자자산 평가금액

평가:

```text
triggered = maxInvestmentAssetWeight >= 0.3
```

### TARGET_ALLOCATION_DEVIATION

필요 데이터:

- 목표 자산배분
- 현재 자산배분

평가:

```text
triggered = any(abs(currentWeight - targetWeight) >= 0.10)
```

### RISK_PROFILE_MISMATCH

필요 데이터:

- 투자 성향
- 위험자산 비중

MVP 기준:

| 투자 성향 | 위험자산 권장 상한 |
| --------- | ------------------ |
| CONSERVATIVE | 0.4 |
| BALANCED | 0.7 |
| GROWTH | 0.9 |

평가:

```text
triggered = riskyAssetWeight > riskProfileLimit
```

### SHORT_TERM_GOAL_RISK_EXPOSURE

필요 데이터:

- 목표일까지 남은 기간
- 목표와 연결된 자산 또는 자금 출처
- 위험자산 비중

MVP에서는 목표와 자산 연결 정보가 없으면 `NOT_EVALUATED`로 처리한다.

### MONTHLY_CAPACITY_SHORTFALL

필요 데이터:

- 목표별 월 필요 적립액
- 월 투자 가능 금액

평가:

```text
triggered = requiredMonthlyContribution > availableMonthlyInvestment
```

---

## 7. 버전

MVP의 초기 규칙 버전은 다음 값을 사용한다.

```text
ruleSetVersion = "rules-v1"
```
