insert into certification (id, certification_code, certification_description, created_at, updated_at)
values (1, 'FACE', '얼굴인증', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000);