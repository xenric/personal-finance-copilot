# Personal Finance Copilot Flow Chart

## 1. 문서 목적

이 문서는 Personal Finance Copilot의 핵심 사용자 흐름을 Mermaid 형식으로 표현한다.

각 단계의 상세 입력, 처리, 출력과 예외 조건은 `01-USER-FLOW.md`에서 정의한다.

---

## 2. 전체 사용자 흐름

```mermaid
flowchart TD
    START([시작])

    REGISTER["1. 사용자 등록<br/>기본 정보 및 투자 목표 설정"]
    GOAL_VALIDATION{"투자 목표가<br/>유효한가?"}

    RISK_PROFILE["2. 투자 성향 체크"]
    RISK_CONFIRM{"투자 성향 결과를<br/>확인했는가?"}

    FINANCIAL_INPUT["3. 현재 재무 상태 입력<br/>소득·지출·자산·부채·투자자산"]
    DATA_VALIDATION{"분석에 필요한 데이터가<br/>충분하고 유효한가?"}

    ANALYSIS["4. 재무 상태 분석<br/>지표·목표 진행률·규칙 경고"]
    ANALYSIS_RESULT["분석 결과 확인"]

    RECOMMENDATION["5. 복수 추천안 생성"]
    OPTION_REVIEW["추천안 비교<br/>안정·균형·성장 등"]
    USER_DECISION{"사용자 결정"}

    ADD_CONDITION["추가 조건 입력"]
    CONDITION_VALIDATION{"추가 조건이<br/>유효한가?"}
    CONDITION_CONFLICT{"기존 조건 또는 목표와<br/>충돌하는가?"}
    RESOLVE_CONFLICT["조건 또는 목표 우선순위 수정"]
    REANALYSIS["조건 반영 재분석"]

    EDIT_DATA["현재 재무 데이터 수정"]
    SELECT_OPTION["추천안 선택"]
    ANALYSIS_ONLY["분석만 저장"]
    PENDING["추천 결정 보류"]
    CLOSE_NO_SELECTION["추천 없이 종료"]

    SNAPSHOT["6. 스냅샷 저장"]
    END([완료])

    START --> REGISTER
    REGISTER --> GOAL_VALIDATION

    GOAL_VALIDATION -- "아니오" --> REGISTER
    GOAL_VALIDATION -- "예" --> RISK_PROFILE

    RISK_PROFILE --> RISK_CONFIRM
    RISK_CONFIRM -- "아니오 또는 재검사" --> RISK_PROFILE
    RISK_CONFIRM -- "예" --> FINANCIAL_INPUT

    FINANCIAL_INPUT --> DATA_VALIDATION
    DATA_VALIDATION -- "아니오" --> FINANCIAL_INPUT
    DATA_VALIDATION -- "예" --> ANALYSIS

    ANALYSIS --> ANALYSIS_RESULT
    ANALYSIS_RESULT --> RECOMMENDATION
    RECOMMENDATION --> OPTION_REVIEW
    OPTION_REVIEW --> USER_DECISION

    USER_DECISION -- "추천안 선택" --> SELECT_OPTION
    USER_DECISION -- "추가 조건 입력" --> ADD_CONDITION
    USER_DECISION -- "재무 데이터 수정" --> EDIT_DATA
    USER_DECISION -- "분석만 저장" --> ANALYSIS_ONLY
    USER_DECISION -- "결정 보류" --> PENDING
    USER_DECISION -- "추천 없이 종료" --> CLOSE_NO_SELECTION

    EDIT_DATA --> FINANCIAL_INPUT

    ADD_CONDITION --> CONDITION_VALIDATION
    CONDITION_VALIDATION -- "아니오" --> ADD_CONDITION
    CONDITION_VALIDATION -- "예" --> CONDITION_CONFLICT

    CONDITION_CONFLICT -- "예" --> RESOLVE_CONFLICT
    RESOLVE_CONFLICT --> ADD_CONDITION

    CONDITION_CONFLICT -- "아니오" --> REANALYSIS
    REANALYSIS --> RECOMMENDATION

    SELECT_OPTION --> SNAPSHOT
    ANALYSIS_ONLY --> SNAPSHOT
    PENDING --> SNAPSHOT
    CLOSE_NO_SELECTION --> SNAPSHOT

    SNAPSHOT --> END
```

---

## 3. 사용자 등록 및 투자 목표 흐름

```mermaid
flowchart TD
    START([사용자 등록 시작])

    BASIC["기본 사용자 정보 입력<br/>이름·기준 통화 등"]
    GOALS["하나 이상의 투자 목표 입력"]
    GOAL_DETAIL["목표별 상세 정보 입력<br/>금액·기간·우선순위"]
    VALIDATE{"사용자 정보와 목표가<br/>유효한가?"}
    CONFLICT{"목표 사이에<br/>충돌이 있는가?"}
    ADJUST["목표 금액·기간·우선순위 수정"]
    SAVE["사용자 프로필과 투자 목표 저장"]
    END([투자 성향 체크로 이동])

    START --> BASIC
    BASIC --> GOALS
    GOALS --> GOAL_DETAIL
    GOAL_DETAIL --> VALIDATE

    VALIDATE -- "아니오" --> GOAL_DETAIL
    VALIDATE -- "예" --> CONFLICT

    CONFLICT -- "예" --> ADJUST
    ADJUST --> GOAL_DETAIL

    CONFLICT -- "아니오" --> SAVE
    SAVE --> END
```

---

## 4. 재무 분석 흐름

```mermaid
flowchart TD
    START([분석 시작])

    LOAD_PROFILE["사용자 프로필 및 투자 목표 조회"]
    LOAD_RISK["투자 성향 조회"]
    LOAD_FINANCE["현재 재무 데이터 조회"]

    VALIDATE{"필수 데이터가<br/>유효한가?"}
    INVALID["누락·오류 항목 반환"]

    CALCULATE["결정론적 재무 지표 계산"]
    GOAL_ANALYSIS["목표별 진행 상황 계산"]
    RULE_ENGINE["규칙 엔진 실행"]
    UNKNOWN["계산 불가 항목 식별"]

    RESULT["구조화된 분석 결과 생성"]
    READY([추천 생성 준비 완료])

    START --> LOAD_PROFILE
    LOAD_PROFILE --> LOAD_RISK
    LOAD_RISK --> LOAD_FINANCE
    LOAD_FINANCE --> VALIDATE

    VALIDATE -- "아니오" --> INVALID
    VALIDATE -- "예" --> CALCULATE

    CALCULATE --> GOAL_ANALYSIS
    GOAL_ANALYSIS --> RULE_ENGINE
    RULE_ENGINE --> UNKNOWN
    UNKNOWN --> RESULT
    RESULT --> READY
```

---

## 5. 추천 및 재분석 반복 흐름

```mermaid
flowchart TD
    START([분석 결과])

    GENERATE["2~3개 추천안 생성"]
    COMPARE["추천안 비교<br/>근거·효과·단점·위험"]
    DECISION{"마음에 드는<br/>추천안이 있는가?"}

    SELECT["추천안 선택"]
    OTHER{"다른 행동을<br/>선택하는가?"}

    CONDITION["추가 조건 입력"]
    VALIDATE{"조건이 유효한가?"}
    CONFLICT{"조건이 서로<br/>충돌하는가?"}
    RESOLVE["조건값 또는 우선순위 수정"]
    REANALYZE["조건 영향 범위 재분석"]

    ANALYSIS_ONLY["분석만 저장"]
    PENDING["추천 결정 보류"]
    EDIT["재무 데이터 수정"]
    EXIT["추천 없이 종료"]

    SNAPSHOT["스냅샷 저장"]

    START --> GENERATE
    GENERATE --> COMPARE
    COMPARE --> DECISION

    DECISION -- "예" --> SELECT
    SELECT --> SNAPSHOT

    DECISION -- "아니오" --> OTHER

    OTHER -- "추가 조건 입력" --> CONDITION
    OTHER -- "분석만 저장" --> ANALYSIS_ONLY
    OTHER -- "결정 보류" --> PENDING
    OTHER -- "재무 데이터 수정" --> EDIT
    OTHER -- "종료" --> EXIT

    CONDITION --> VALIDATE
    VALIDATE -- "아니오" --> CONDITION
    VALIDATE -- "예" --> CONFLICT

    CONFLICT -- "예" --> RESOLVE
    RESOLVE --> CONDITION

    CONFLICT -- "아니오" --> REANALYZE
    REANALYZE --> GENERATE

    ANALYSIS_ONLY --> SNAPSHOT
    PENDING --> SNAPSHOT
    EXIT --> SNAPSHOT
```

---

## 6. 스냅샷 저장 흐름

```mermaid
flowchart TD
    START([스냅샷 저장 요청])

    COLLECT["저장 대상 데이터 수집"]
    CHECK{"필수 분석 데이터가<br/>존재하는가?"}
    INVALID["저장 불가 사유 반환"]

    BUILD["불변 스냅샷 생성"]
    EXISTS{"같은 기간의 스냅샷이<br/>이미 존재하는가?"}

    POLICY{"사용자 선택"}
    CANCEL["저장 취소"]
    NEW_VERSION["새 버전 또는 별도 식별자로 저장"]
    REPLACE["기존 파일 보존 후 명시적 교체"]

    ATOMIC_WRITE["임시 파일 작성 및 검증"]
    WRITE_OK{"파일 저장에<br/>성공했는가?"}
    PRESERVE["기존 파일 보존 및 오류 반환"]
    COMPLETE([저장 완료])

    START --> COLLECT
    COLLECT --> CHECK

    CHECK -- "아니오" --> INVALID
    CHECK -- "예" --> BUILD

    BUILD --> EXISTS

    EXISTS -- "아니오" --> ATOMIC_WRITE
    EXISTS -- "예" --> POLICY

    POLICY -- "취소" --> CANCEL
    POLICY -- "새 버전" --> NEW_VERSION
    POLICY -- "교체" --> REPLACE

    NEW_VERSION --> ATOMIC_WRITE
    REPLACE --> ATOMIC_WRITE

    ATOMIC_WRITE --> WRITE_OK
    WRITE_OK -- "아니오" --> PRESERVE
    WRITE_OK -- "예" --> COMPLETE
```

---

## 7. 사용자 상태 다이어그램

```mermaid
stateDiagram-v2
    [*] --> RegistrationStarted

    RegistrationStarted --> GoalsConfigured
    GoalsConfigured --> RiskProfileCompleted
    RiskProfileCompleted --> FinancialDataEntered

    FinancialDataEntered --> ValidationFailed
    ValidationFailed --> FinancialDataEntered

    FinancialDataEntered --> AnalysisCompleted
    AnalysisCompleted --> RecommendationGenerated

    RecommendationGenerated --> ConditionsAdded
    ConditionsAdded --> ConditionConflict
    ConditionConflict --> ConditionsAdded

    ConditionsAdded --> ReanalysisCompleted
    ReanalysisCompleted --> RecommendationGenerated

    RecommendationGenerated --> RecommendationSelected
    RecommendationGenerated --> AnalysisOnly
    RecommendationGenerated --> RecommendationPending
    RecommendationGenerated --> ClosedWithoutSelection

    RecommendationSelected --> SnapshotSaved
    AnalysisOnly --> SnapshotSaved
    RecommendationPending --> SnapshotSaved
    ClosedWithoutSelection --> SnapshotSaved

    SnapshotSaved --> [*]
```

---

## 8. 주요 데이터 흐름

```mermaid
flowchart LR
    USER["사용자 입력"]

    PROFILE["사용자 프로필"]
    GOALS["투자 목표"]
    RISK["투자 성향"]
    FINANCE["현재 재무 데이터"]
    CONDITIONS["추가 조건"]

    ENGINE["분석 엔진"]
    RULES["규칙 엔진"]
    RECOMMENDER["추천 생성기"]
    SNAPSHOT["스냅샷"]

    USER --> PROFILE
    USER --> GOALS
    USER --> RISK
    USER --> FINANCE
    USER --> CONDITIONS

    PROFILE --> ENGINE
    GOALS --> ENGINE
    RISK --> ENGINE
    FINANCE --> ENGINE

    ENGINE --> RULES
    ENGINE --> RECOMMENDER
    RULES --> RECOMMENDER
    CONDITIONS --> RECOMMENDER

    PROFILE --> SNAPSHOT
    GOALS --> SNAPSHOT
    RISK --> SNAPSHOT
    FINANCE --> SNAPSHOT
    ENGINE --> SNAPSHOT
    RULES --> SNAPSHOT
    RECOMMENDER --> SNAPSHOT
```
