-- Sequence
DROP SEQUENCE IF EXISTS user_notification_seq;
CREATE SEQUENCE user_notification_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE user_notification (
   user_notification_id bigint PRIMARY KEY,
   fleek_user_id bigint,
   title varchar(255),
   body text,
   notification_type varchar(50),
   notification_key varchar(50),
   created_at bigint NOT NULL,
   updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX user_notification_idx01 ON user_notification (created_at);
CREATE INDEX user_notification_idx02 ON user_notification (updated_at);

-- Foreign Key (Index instead of constraint)
CREATE INDEX user_notification_fk01 ON user_notification (fleek_user_id);

alter table user_notification add constraint user_notification_uk01 unique (fleek_user_id, notification_key, notification_type);