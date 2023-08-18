insert into certification (id, certification_code, certification_description, certification_name, created_at, updated_at)
values
    (1, 'FACE', '얼굴인증', '얼굴인증', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
    (2, 'COMPANY', '직장인증', '직장인증', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
    (3, 'COLLEGE', '학교인증', '학교인증', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
    (4, 'INBODY', '인바디인증', '인바디인증', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000),
;