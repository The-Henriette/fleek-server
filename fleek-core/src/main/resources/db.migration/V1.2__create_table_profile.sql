-- Sequence
DROP SEQUENCE IF EXISTS profile_seq;
CREATE SEQUENCE profile_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE profile (
     profile_id bigint PRIMARY KEY,
     fleek_user_id bigint,
     profile_name varchar(255),
     chat_profile_key varchar(255),
     bio text,
     created_at bigint NOT NULL,
     updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX profile_idx01 ON profile (created_at);
CREATE INDEX profile_idx02 ON profile (updated_at);

alter table profile add constraint profile_uk01 unique (profile_name);

-- Foreign Key (Index instead of constraint)
CREATE INDEX profile_fk01 ON profile (fleek_user_id);

-- Sequence
DROP SEQUENCE IF EXISTS profile_info_seq;
CREATE SEQUENCE profile_info_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE profile_info (
  profile_info_id bigint PRIMARY KEY,
  profile_id bigint,
  profile_info_category varchar(50),
  type_code varchar(255),
  type_name varchar(255),
  type_option varchar(255),
  type_value varchar(255),
  created_at bigint NOT NULL,
  updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX profile_info_idx01 ON profile_info (created_at);
CREATE INDEX profile_info_idx02 ON profile_info (updated_at);

-- Foreign Key (Index instead of constraint)
CREATE INDEX profile_info_fk01 ON profile_info (profile_id);

-- Sequence
DROP SEQUENCE IF EXISTS profile_image_seq;
CREATE SEQUENCE profile_image_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE profile_image (
       profile_image_id bigint PRIMARY KEY,
       profile_id bigint,
       image_type varchar(50),
       image_url varchar(255),
       order_number integer,
       created_at bigint NOT NULL,
       updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX profile_image_idx01 ON profile_image (created_at);
CREATE INDEX profile_image_idx02 ON profile_image (updated_at);

alter table profile_image add constraint profile_image_uk01 unique (profile_id, image_type, order_number);

-- Sequence
DROP SEQUENCE IF EXISTS profile_info_type_seq;
CREATE SEQUENCE profile_info_type_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE profile_info_type (
   profile_info_type_id bigint PRIMARY KEY,
   profile_info_type_code varchar(255),
   profile_info_type_name varchar(255),
   description varchar(255),
   profile_info_category varchar(50),
   order_number integer,
   emoji varchar(10),
   input_type varchar(50),
   created_at bigint NOT NULL,
   updated_at bigint NOT NULL
);

-- Indexes
alter table profile_info_type add constraint profile_info_type_uk01 unique (profile_info_type_code);
alter table profile_info_type add constraint profile_info_type_uk02 unique (profile_info_category, order_number);

CREATE INDEX profile_info_type_idx01 ON profile_info_type (created_at);
CREATE INDEX profile_info_type_idx02 ON profile_info_type (updated_at);

-- Sequence
DROP SEQUENCE IF EXISTS profile_info_type_option_seq;
CREATE SEQUENCE profile_info_type_option_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE profile_info_type_option (
      profile_info_type_option_id bigint PRIMARY KEY,
      profile_info_type_code varchar(255),
      option_code varchar(255),
      option_name varchar(255),
      option_description varchar(255),
      order_number integer,
      emoji varchar(10),
      created_at bigint NOT NULL,
      updated_at bigint NOT NULL
);

-- Indexes
CREATE UNIQUE INDEX profile_info_type_option_uk1 ON profile_info_type_option (option_code);
CREATE UNIQUE INDEX profile_info_type_option_uk2 ON profile_info_type_option (profile_info_type_code, order_number);
alter table profile_info_type_option add constraint profile_info_type_option_uk01 unique (option_code);
alter table profile_info_type_option add constraint profile_info_type_option_uk02 unique (profile_info_type_code, order_number);

CREATE INDEX profile_info_type_option_idx01 ON profile_info_type_option (created_at);
CREATE INDEX profile_info_type_option_idx02 ON profile_info_type_option (updated_at);

-- Foreign Key (Index instead of constraint)
CREATE INDEX profile_info_type_option_fk01 ON profile_info_type_option (profile_info_type_id);

