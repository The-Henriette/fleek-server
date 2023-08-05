-- Sequence
DROP SEQUENCE IF EXISTS exchange_seq;
CREATE SEQUENCE exchange_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE exchange (
      exchange_id bigint PRIMARY KEY,
      chat_id bigint,
      request_message_id varchar(255),
      created_at bigint NOT NULL,
      updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX exchange_idx01 ON exchange (created_at);
CREATE INDEX exchange_idx02 ON exchange (updated_at);

-- Foreign Key (Index instead of constraint)
CREATE INDEX exchange_fk01 ON exchange (chat_id);

-- Sequence
DROP SEQUENCE IF EXISTS profile_exchange_seq;
CREATE SEQUENCE profile_exchange_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE profile_exchange (
  profile_exchange_id bigint PRIMARY KEY,
  profile_id bigint,
  exchange_id bigint,
  watched boolean,
  accepted boolean,
  rejected boolean,
  created_at bigint NOT NULL,
  updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX profile_exchange_idx01 ON profile_exchange (created_at);
CREATE INDEX profile_exchange_idx02 ON profile_exchange (updated_at);

-- Foreign Keys (Indexes instead of constraints)
CREATE INDEX profile_exchange_fk01 ON profile_exchange (profile_id);
CREATE INDEX profile_exchange_fk02 ON profile_exchange (exchange_id);
