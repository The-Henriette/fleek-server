-- Sequence
DROP SEQUENCE IF EXISTS user_platform_info_seq;
CREATE SEQUENCE user_platform_info_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE user_platform_info (
    user_platform_info_id bigint PRIMARY KEY,
    push_token varchar(255),
    platform_type varchar(30),
    fleek_user_id bigint,
    created_at bigint NOT NULL,
    updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX user_platform_info_idx01 ON user_platform_info (push_token);
CREATE INDEX user_platform_info_idx02 ON user_platform_info (created_at);
CREATE INDEX user_platform_info_idx03 ON user_platform_info (updated_at);

-- Foreign Key (Index instead of constraint)
CREATE INDEX user_platform_info_fk01 ON user_platform_info (fleek_user_id);
