-- Sequence
DROP SEQUENCE IF EXISTS profile_chat_seq;
CREATE SEQUENCE profile_chat_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE profile_chat (
  profile_chat_id bigint PRIMARY KEY,
  profile_id bigint,
  chat_id bigint,
  profile_chat_code varchar(255),
  anonymous_chat_key varchar(255),
  created_at bigint NOT NULL,
  updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX profile_chat_idx01 ON profile_chat (created_at);
CREATE INDEX profile_chat_idx02 ON profile_chat (updated_at);

-- Foreign Key (Index instead of constraint)
CREATE INDEX profile_chat_fk01 ON profile_chat (profile_id);
CREATE INDEX profile_chat_fk02 ON profile_chat (chat_id);


-- Sequence
DROP SEQUENCE IF EXISTS chat_seq;
CREATE SEQUENCE chat_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE chat (
  chat_id bigint PRIMARY KEY,
  chat_uri varchar(255),
  created_at bigint NOT NULL,
  updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX chat_idx01 ON chat (created_at);
CREATE INDEX chat_idx02 ON chat (updated_at);
