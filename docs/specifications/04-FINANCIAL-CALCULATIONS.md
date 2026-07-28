# Financial Calculations Specification

## 1. 문서 목적

이 문서는 Personal Finance Copilot MVP에서 애플리케이션 코드가 결정론적으로 계산해야 하는 재무 지표와 계산 규칙을 정의한다.

AI는 이 문서의 계산 결과를 설명할 수 있지만, 수치를 직접 계산하거나 수정하지 않는다.

---

## 2. 공통 계산 원칙

- 모든 금액은 `Money` 값으로 처리한다.
- 서로 다른 통화는 환율 정보 없이 합산하지 않는다.
- 계산할 수 없는 값은 `0`으로 대체하지 않는다.
- 계산 결과에는 단위와 계산 상태를 함께 저장한다.
- 비율은 내부적으로 `0.15`처럼 소수값으로 통일하고, 화면 표시에서만 `15%`로 변환한다.
- 비율의 차이는 퍼센트포인트로 표현한다.
- 모든 계산 결과에는 `calculationVersion`을 기록한다.

---

## 3. MVP 계산 지표

| 지표 | 계산식 | 단위 | 계산 불가 조건 |
| ---- | ------ | ---- | -------------- |
| 총자산 | 모든 자산 평가금액 합계 | Money | 통화 변환 불가, 평가금액 누락 |
| 총부채 | 모든 부채 미상환 잔액 합계 | Money | 통화 변환 불가, 잔액 누락 |
| 순자산 | 총자산 - 총부채 | Money | 총자산 또는 총부채 계산 불가 |
| 월 저축액 | 월 소득 - 월 지출 - 월 부채 상환액 | Money | 소득 또는 지출 누락 |
| 월 저축률 | 월 저축액 / 월 소득 | Percentage | 월 소득이 0 이하 또는 누락 |
| 비상자금 충당 개월 수 | 현금성 자산 / 월 필수 지출 | Number | 현금성 자산 또는 필수 지출 누락 |
| 부채 비율 | 총부채 / 총자산 | Percentage | 총자산이 0 이하 또는 계산 불가 |
| 고금리 부채 비중 | 고금리 부채 잔액 / 총부채 | Percentage | 금리 누락 또는 총부채 계산 불가 |
| 자산배분 | 자산군별 평가금액 / 총자산 | Percentage map | 총자산 계산 불가 |
| 단일 상품 집중도 | 가장 큰 투자상품 평가금액 / 투자자산 합계 | Percentage | 투자자산 없음 또는 평가금액 누락 |
| 목표 진행률 | 현재 달성값 / 목표값 | Percentage | 목표값 또는 현재값 계산 불가 |

---

## 4. 금액 계산

### 총자산

포함 대상:

- 현금
- 예금 및 적금
- 주식, ETF, 채권, 금
- 부동산, 차량
- 기타 자산

규칙:

- 평가금액이 없는 자산은 합계에서 제외하지 않고 계산 불가 사유로 기록한다.
- 서로 다른 통화가 섞여 있고 환율이 없으면 총자산 전체를 계산 불가로 처리한다.
- 자산 평가 기준일은 결과 근거에 포함한다.

### 총부채

포함 대상:

- 주택담보대출
- 신용대출
- 자동차 대출
- 카드 부채
- 기타 부채

규칙:

- 잔액이 0인 부채는 총부채에는 포함하되 상환 완료 상태로 표시할 수 있다.
- 미상환 잔액이 누락된 부채가 있으면 총부채를 계산 불가로 처리한다.

### 순자산

순자산은 총자산과 총부채가 모두 계산 가능한 경우에만 계산한다.

```text
netWorth = totalAssets - totalLiabilities
```

---

## 5. 현금흐름 계산

### 월 저축액

```text
monthlySavings = monthlyIncome - monthlyFixedExpense - monthlyVariableExpense - monthlyDebtPayment
```

규칙:

- `monthlyDebtPayment`가 부채별 상환액 합계와 다르면 경고 근거로 사용할 수 있다.
- 월 저축액은 음수가 될 수 있으며, 음수인 경우 지출 초과 상태로 표시한다.

### 월 저축률

```text
savingsRate = monthlySavings / monthlyIncome
```

규칙:

- 월 소득이 0이면 계산하지 않는다.
- 음수 저축률은 허용하되 경고 엔진에서 평가한다.

---

## 6. 비상자금 계산

```text
emergencyFundMonths = liquidAssets / essentialMonthlyExpense
```

`liquidAssets`에는 현금, CMA, 요구불 예금 등 즉시 사용 가능한 자산만 포함한다.

`essentialMonthlyExpense`는 MVP에서 다음 값으로 계산한다.

```text
essentialMonthlyExpense = monthlyFixedExpense + monthlyDebtPayment
```

---

## 7. 목표 진행률 계산

목표 유형별 현재값은 다음 기준을 사용한다.

| 목표 유형 | 현재값 |
| --------- | ------ |
| EMERGENCY_FUND | 비상자금 충당 개월 수 또는 현금성 자산 |
| NET_WORTH | 순자산 |
| SAVINGS_RATE | 월 저축률 |
| ASSET_ALLOCATION | 목표 자산군별 현재 비중 |
| DEBT_REDUCTION | 기준 부채 잔액 대비 감소액 |

목표 진행률은 목표 유형에 따라 금액, 비율, 개월 수 단위를 구분한다.

---

## 8. 계산 결과 상태

계산 결과는 다음 상태 중 하나를 가진다.

- `CALCULATED`
- `INSUFFICIENT_DATA`
- `INVALID_INPUT`
- `NOT_APPLICABLE`

계산 불가 상태에는 사용자가 수정할 수 있는 이유를 함께 저장한다.

---

## 9. 버전

MVP의 초기 계산 규칙 버전은 다음 값을 사용한다.

```text
calculationVersion = "calc-v1"
```
