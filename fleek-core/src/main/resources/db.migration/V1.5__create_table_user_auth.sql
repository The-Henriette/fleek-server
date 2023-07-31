-- Sequence
DROP SEQUENCE IF EXISTS user_auth_seq;
CREATE SEQUENCE user_auth_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE user_auth (
   user_auth_id bigint PRIMARY KEY,
   fleek_user_id bigint,
   access_token varchar(255),
   refresh_token varchar(255),
   expired_at bigint,
   created_at bigint NOT NULL,
   updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX user_auth_idx01 ON user_auth (created_at);
CREATE INDEX user_auth_idx02 ON user_auth (updated_at);

-- Foreign Key (Index instead of constraint)
CREATE INDEX user_auth_fk01 ON user_auth (fleek_user_id);
