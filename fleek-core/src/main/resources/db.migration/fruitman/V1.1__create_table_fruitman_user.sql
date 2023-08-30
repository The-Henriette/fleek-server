-- Sequence
DROP SEQUENCE IF EXISTS fruit_man_user_seq;
CREATE SEQUENCE fruit_man_user_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE fruit_man_user (
    fruit_man_user_id bigint PRIMARY KEY,
    provider_code varchar(255),
    provider_id varchar(255),
    nickname varchar(255),
    profile_url varchar(255),
    email varchar(255),
    created_at bigint NOT NULL,
    updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX fruit_man_user_idx01 ON fruit_man_user (created_at);
CREATE INDEX fruit_man_user_idx02 ON fruit_man_user (updated_at);
CREATE UNIQUE INDEX fruit_man_user_uk01 ON fruit_man_user (provider_code, provider_id);
