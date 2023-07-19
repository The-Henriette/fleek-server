drop sequence if exists fleek_user_seq;
create sequence fleek_user_seq increment by 1 start with 1 no cycle;

create table fleek_user (
    fleek_user_id bigint primary key,
    phone_number varchar(50) not null,
    user_status varchar(50) not null,
    created_at bigint not null,
    updated_at bigint not null
);

alter table fleek_user add constraint fleek_user_uk01 unique (phone_number);

create index fleek_user_idx01 on fleek_user (created_at);
create index fleek_user_idx02 on fleek_user (updated_at);

-- Sequence
DROP SEQUENCE IF EXISTS fleek_user_detail_seq;
CREATE SEQUENCE fleek_user_detail_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE fleek_user_detail (
   fleek_user_detail_id bigint PRIMARY KEY,
   fleek_user_id bigint,
   name varchar(255),
   gender varchar(50),
   orientation varchar(50),
   birth_date bigint NOT NULL,
   created_at bigint NOT NULL,
   updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX fleek_user_detail_idx01 ON fleek_user_detail (created_at);
CREATE INDEX fleek_user_detail_idx02 ON fleek_user_detail (updated_at);
CREATE INDEX fleek_user_detail_idx03 ON fleek_user_detail (gender);
CREATE INDEX fleek_user_detail_idx04 ON fleek_user_detail (orientation);


-- Foreign Key (Index instead of constraint)
CREATE INDEX fleek_user_detail_fk01 ON fleek_user_detail (fleek_user_id);
