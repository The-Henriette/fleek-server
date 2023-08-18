-- Sequence
DROP SEQUENCE IF EXISTS user_wallet_seq;
CREATE SEQUENCE user_wallet_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE user_wallet (
     user_wallet_id bigint PRIMARY KEY,
     fleek_user_id bigint,
     amount bigint,
     created_at bigint NOT NULL,
     updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX user_wallet_idx01 ON user_wallet (created_at);
CREATE INDEX user_wallet_idx02 ON user_wallet (updated_at);

-- Foreign Key (Index instead of constraint)
CREATE INDEX user_wallet_fk01 ON user_wallet (fleek_user_id);