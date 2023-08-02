insert into term (term_seq_id, mandatory, term_contents_url, term_code, term_name, term_appeal, created_at, updated_at) values
(nextval('term_seq'), true, 'https://www.naver.com', 'ALL', 'Fleek 전체약관', null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('term_seq'), true, 'https://www.naver.com', 'USAGE', 'Fleek 이용약관', null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('term_seq'), true, 'https://www.naver.com', 'PERSONAL_INFO', '개인정보 수집 및 이용 동의', null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('term_seq'), true, 'https://www.naver.com', 'LOCATION', '위치기반서비스 이용약관', null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('term_seq'), true, 'https://www.naver.com', 'SENSITIVE', '민감정보 처리 동의서', null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('term_seq'), false, 'https://www.naver.com', 'MARKETING', '마케팅 수신 동의', '이벤트 혜택 정보를 받을 수 있어요', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000)
;