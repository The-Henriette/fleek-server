insert into email_domain_meta (email_domain_meta_id, email_domain, certification_code, target_name, created_at, updated_at) values
(nextval('email_domain_meta_seq'), 'snu.ac.kr', 'COLLEGE', '서울대학교', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000);