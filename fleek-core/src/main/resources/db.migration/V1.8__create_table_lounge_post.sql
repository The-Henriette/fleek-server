-- Sequence
DROP SEQUENCE IF EXISTS lounge_post_seq;
CREATE SEQUENCE lounge_post_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE lounge_post (
     lounge_post_id bigint PRIMARY KEY,
     profile_id bigint,
     topic varchar(50),
     title varchar(255),
     content text,
     likes integer,
     comments integer,
     views integer,
     topic_attributes text,
     thumbnail varchar(255),
     created_at bigint NOT NULL,
     updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX lounge_post_idx01 ON lounge_post (created_at);
CREATE INDEX lounge_post_idx02 ON lounge_post (updated_at);
CREATE INDEX lounge_post_idx03 ON lounge_post (topic);

create index lounge_post_title_gin on lounge_post using gin(title gin_bigm_ops);
create index lounge_post_content_gin on lounge_post using gin(content gin_bigm_ops);

-- Foreign Key (Index instead of constraint)
CREATE INDEX lounge_post_fk01 ON lounge_post (profile_id);


-- Sequence
DROP SEQUENCE IF EXISTS post_image_seq;
CREATE SEQUENCE post_image_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE post_image (
    post_image_id bigint PRIMARY KEY,
    lounge_post_id bigint,
    image_url varchar(255),
    created_at bigint NOT NULL,
    updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX post_image_idx01 ON post_image (created_at);
CREATE INDEX post_image_idx02 ON post_image (updated_at);

-- Foreign Key (Index instead of constraint)
CREATE INDEX post_image_fk01 ON post_image (lounge_post_id);

-- Sequence
DROP SEQUENCE IF EXISTS post_like_seq;
CREATE SEQUENCE post_like_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE post_like (
   post_like_id bigint PRIMARY KEY,
   lounge_post_id bigint,
   profile_id bigint,
   created_at bigint NOT NULL,
   updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX post_like_idx01 ON post_like (created_at);
CREATE INDEX post_like_idx02 ON post_like (updated_at);

-- Foreign Keys (Indexes instead of constraints)
CREATE INDEX post_like_fk01 ON post_like (lounge_post_id);
CREATE INDEX post_like_fk02 ON post_like (profile_id);

-- Sequence
DROP SEQUENCE IF EXISTS post_comment_seq;
CREATE SEQUENCE post_comment_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE post_comment (
      post_comment_id bigint PRIMARY KEY,
      lounge_post_id bigint,
      profile_id bigint,
      parent_comment_id bigint,
      content text,
      likes integer,
      sub_comments integer,
      created_at bigint NOT NULL,
      updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX post_comment_idx01 ON post_comment (created_at);
CREATE INDEX post_comment_idx02 ON post_comment (updated_at);
CREATE INDEX post_comment_idx03 ON post_comment (parent_comment_id);

-- Foreign Keys (Indexes instead of constraints)
CREATE INDEX post_comment_fk01 ON post_comment (lounge_post_id);
CREATE INDEX post_comment_fk02 ON post_comment (profile_id);

-- Sequence
DROP SEQUENCE IF EXISTS comment_like_seq;
CREATE SEQUENCE comment_like_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Table
CREATE TABLE comment_like (
      comment_like_id bigint PRIMARY KEY,
      profile_id bigint,
      post_comment_id bigint,
      created_at bigint NOT NULL,
      updated_at bigint NOT NULL
);

-- Indexes
CREATE INDEX comment_like_idx01 ON comment_like (created_at);
CREATE INDEX comment_like_idx02 ON comment_like (updated_at);

-- Foreign Keys (Indexes instead of constraints)
CREATE INDEX comment_like_fk01 ON comment_like (profile_id);
CREATE INDEX comment_like_fk02 ON comment_like (post_comment_id);
