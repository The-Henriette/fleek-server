-- Sequence
DROP SEQUENCE IF EXISTS report_seq;
CREATE SEQUENCE report_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE report (
    report_id bigint PRIMARY KEY,
    report_user_id bigint,
    target_user_id bigint,
    report_type varchar(255),
    report_target_id bigint,
    report_reason text,
    confirmed boolean,
    created_at bigint NOT NULL,
    updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX report_idx01 ON report (created_at);
CREATE INDEX report_idx02 ON report (updated_at);

alter table report add constraint report_uk01 unique (report_user_id, report_type, report_target_id);
