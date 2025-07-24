INSERT INTO terms (ex_key, title, content, version, effective_from, effective_to, default_agreement_validity_period, created_at, updated_at, deleted)
VALUES
    ( UUID(), 'SERVICE_TERM', '서비스 이용약관 내용입니다.', 1, NOW(), NULL, NULL, NOW(), NOW(), false),
    ( UUID(), 'PRIVACY_POLICY', '개인정보 처리방침 내용입니다.', 1, NOW(), NULL, NULL, NOW(), NOW(), false),
    ( UUID(), 'MARKETING_TERM', '마케팅 정보 수신 동의 내용입니다.', 1, NOW(), NULL, INTERVAL 1 YEAR, NOW(), NOW(), false),
    ( UUID(), 'THIRD_PARTY_SHARE', '제3자 정보 제공 동의 내용입니다.', 1, NOW(), NULL, NULL, NOW(), NOW(), false),
    ( UUID(), 'SELLER_POLICY', '셀러 전용 정책 약관입니다.', 1, NOW(), NULL, NULL, NOW(), NOW(), false);

-- CUSTOMER (고객) 약관 조건
INSERT INTO term_conditions (term_id, user_type, app_type, country, is_required)
VALUES
  ( 1, 'CUSTOMER', 'ANDROID', 'KR', true),
  ( 1, 'CUSTOMER', 'IOS', 'KR', true),

  ( 2, 'CUSTOMER', 'ANDROID', 'KR', true),
  ( 2, 'CUSTOMER', 'IOS', 'KR', true),

  ( 3, 'CUSTOMER', 'ANDROID', 'KR', false),
  ( 3, 'CUSTOMER', 'IOS', 'KR', false),

  ( 4, 'CUSTOMER', 'ANDROID', 'KR', false),
  ( 4, 'CUSTOMER', 'IOS', 'KR', false);

-- SELLER (판매자) 약관 조건
INSERT INTO term_conditions (term_id, user_type, app_type, country, is_required)
VALUES
  ( 1, 'SELLER', 'ANDROID', 'KR', true),
  ( 1, 'SELLER', 'IOS', 'KR', true),

  ( 2, 'SELLER', 'ANDROID', 'KR', true),
  ( 2, 'SELLER', 'IOS', 'KR', true),

  ( 5, 'SELLER', 'ANDROID', 'KR', true),
  ( 5, 'SELLER', 'IOS', 'KR', true);
