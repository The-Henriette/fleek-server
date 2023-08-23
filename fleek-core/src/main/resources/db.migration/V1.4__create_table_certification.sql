-- Sequence
DROP SEQUENCE IF EXISTS certification_seq;
CREATE SEQUENCE certification_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE certification (
   certification_id bigint PRIMARY KEY,
   certification_code varchar(255),
   certification_name varchar(255),
   certification_description varchar(255),
   created_at bigint NOT NULL,
   updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX certification_idx01 ON certification (created_at);
CREATE INDEX certification_idx02 ON certification (updated_at);

-- Sequence
DROP SEQUENCE IF EXISTS user_certification_seq;
CREATE SEQUENCE user_certification_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE user_certification (
    user_certification_id bigint PRIMARY KEY,
    fleek_user_id bigint,
    certification_code varchar(255),
    certification_status varchar(50),
    certification_method varchar(50),
    active boolean,
    created_at bigint NOT NULL,
    updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX user_certification_idx01 ON user_certification (created_at);
CREATE INDEX user_certification_idx02 ON user_certification (updated_at);

-- Foreign Key (Index instead of constraint)
CREATE INDEX user_certification_fk01 ON user_certification (fleek_user_id);

-- Sequence
DROP SEQUENCE IF EXISTS certification_resource_seq;
CREATE SEQUENCE certification_resource_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE certification_resource (
    certification_resource_id bigint PRIMARY KEY,
    user_certification_id bigint,
    resource_url varchar(255),
    resource_context varchar(255),
    resource_code varchar(255),
    certification_status varchar(50),
    reject_reason varchar(255),
    reject_reason_detail varchar(255),
    created_at bigint NOT NULL,
    updated_at bigint NOT NULL
);

alter table certification_resource add constraint certification_resource_uk01 unique (resource_code);

-- Indexes
CREATE INDEX certification_resource_idx01 ON certification_resource (created_at);
CREATE INDEX certification_resource_idx02 ON certification_resource (updated_at);

-- Foreign Key (Index instead of constraint)
CREATE INDEX certification_resource_fk01 ON certification_resource (user_certification_id);

