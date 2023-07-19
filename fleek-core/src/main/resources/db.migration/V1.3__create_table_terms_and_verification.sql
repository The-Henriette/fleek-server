-- Sequence
DROP SEQUENCE IF EXISTS user_verification_seq;
CREATE SEQUENCE user_verification_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE user_verification (
   user_verification_id bigint PRIMARY KEY,
   fleek_user_id bigint,
   verification_type varchar(50),
   verification_number integer,
   verified boolean,
   created_at bigint NOT NULL,
   updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX user_verification_idx01 ON user_verification (created_at);
CREATE INDEX user_verification_idx02 ON user_verification (updated_at);

-- Foreign Key (Index instead of constraint)
CREATE INDEX user_verification_fk01 ON user_verification (fleek_user_id);

-- Sequence
DROP SEQUENCE IF EXISTS term_seq;
CREATE SEQUENCE term_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE term (
  term_seq_id bigint PRIMARY KEY,
  mandatory boolean,
  term_contents_url varchar(255),
  term_code varchar(255),
  created_at bigint NOT NULL,
  updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX term_idx01 ON term (created_at);
CREATE INDEX term_idx02 ON term (updated_at);


