insert into profile_info_type
(profile_info_type_id, profile_info_type_code, profile_info_type_name, description, profile_info_category, order_number, input_type, emoji, created_at, updated_at) values
(nextval('profile_info_type_seq'), 'EDUCATION', '최종학력', '최종학력', 'BASIC', 1, 'SINGLE', '🎓', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_seq'), 'COMPANY', '직장', '직장', 'BASIC', 2, 'CERTIFIED_VALUE', '🧑‍💻', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_seq'), 'OCCUPATION', '직업', '직업', 'BASIC', 3, 'CUSTOM', '🧑‍💼', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_seq'), 'HEIGHT', '키', '키', 'BASIC', 4, 'CUSTOM', '📏', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_seq'), 'BODY', '체형', '체형', 'BASIC', 5, 'SINGLE', '🧍', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_seq'), 'MBTI', 'MBTI', 'MBTI', 'BASIC', 6, 'SINGLE', '🧠', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_seq'), 'RELIGION', '종교', '종교', 'BASIC', 7, 'SINGLE', '🙏🏼', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_seq'), 'PET', '애완동물', '애완동물', 'LIFESTYLE', 1, 'MULTI_COMMA', '🐾', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_seq'), 'DRINKING', '음주', '음주', 'LIFESTYLE', 2, 'SINGLE', '🍺', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_seq'), 'SMOKING', '흡연', '흡연', 'LIFESTYLE', 3, 'SINGLE', '🚬', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_seq'), 'SPORTS', '스포츠', '스포츠', 'LIFESTYLE', 4, 'MULTI_COMMA', '🏋️', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_seq'), 'CHARACTERISTICS', '나만의 특징', '나만의 특징', 'CHARACTERISTICS', 1, 'MULTI_LIST', null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000)
;

insert into profile_info_type_option
(profile_info_type_option_id, profile_info_type_code, option_code, option_name, option_description, order_number, emoji, created_at, updated_at) values
(nextval('profile_info_type_option_seq'), 'EDUCATION', 'EDUCATION_COMMUNITY', '전문대졸', '전문대졸', 1, null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_option_seq'), 'BODY', 'BODY_SLIMFIT', '슬림탄탄', '슬림탄탄', 1, null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_option_seq'), 'MBTI', 'MBTI_ENFP', 'ENFP', 'ENFP', 1, null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_option_seq'), 'RELIGION', 'RELIGION_ATHEIST', '무교', '무교', 1, null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_option_seq'), 'PET', 'PET_DOG', '강아지', '강아지', 1, null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_option_seq'), 'PET', 'PET_CAT', '고양이', '고양이', 2, null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_option_seq'), 'DRINKING', 'DRINKING_SOMETIMES', '가끔 마셔요', '가끔 마셔요', 1, null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_option_seq'), 'SMOKING', 'SMOKING_NEVER', '안해요', '안해요', 1, null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_option_seq'), 'SPORTS', 'SPORTS_GYM', '헬스', '헬스', 1, null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_option_seq'), 'SPORTS', 'SPORTS_GOLF', '골프', '골프', 2, null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_option_seq'), 'CHARACTERISTICS', 'CHARACTERISTICS_1', '허세 없어요', '허세 없어요', 1, null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_option_seq'), 'CHARACTERISTICS', 'CHARACTERISTICS_2', '👶 동안이에요', '👶 동안이에요', 2, null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_type_option_seq'), 'CHARACTERISTICS', 'CHARACTERISTICS_3', '🙃 유머 감각이 있어요', '🙃 유머 감각이 있어요', 3, null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000);

insert into profile_info (profile_info_id, profile_id, profile_info_category, type_code, type_name, type_option, type_value, created_at, updated_at) values
(nextval('profile_info_seq'), 4, 'BASIC', 'EDUCATION', '최종학력', 'EDUCATION_COMMUNITY', null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_seq'), 4, 'BASIC', 'HEIGHT', '키', null, '180cm', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_seq'), 4, 'BASIC', 'MBTI', 'MBTI', 'MBTI_ENFP', null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_seq'), 4, 'BASIC', 'BODY', '체형', 'BODY_SLIMFIT', null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_seq'), 4, 'BASIC', 'RELIGION', '종교', 'RELIGION_ATHEIST', null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_seq'), 4, 'LIFESTYLE', 'PET', '애완동물', 'PET_DOG,PET_CAT', null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_seq'), 4, 'LIFESTYLE', 'DRINKING', '음주', 'DRINKING_SOMETIMES', null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_seq'), 4, 'LIFESTYLE', 'SMOKING', '흡연', 'SMOKING_NEVER', null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_seq'), 4, 'LIFESTYLE', 'SPORTS', '스포츠', 'SPORTS_GYM,SPORTS_GOLF', null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
(nextval('profile_info_seq'), 4, 'CHARACTERISTICS', 'CHARACTERISTICS', '나만의 특징', 'CHARACTERISTICS_1,CHARACTERISTICS_2,CHARACTERISTICS_3', null, EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000);


