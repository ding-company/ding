# ding



# 약어
exKey = UUID 기반의 고유 키이니다.
tx = transactions

# Ground Rule
### 1. 도메인 이벤트
도메인 이벤트는 도메인 모델에서 생성한다.
### 2. 도메인 서비스
도메인 서비스에서는 I/O를 제한한다. (DB저장, 카프카 메세지)
