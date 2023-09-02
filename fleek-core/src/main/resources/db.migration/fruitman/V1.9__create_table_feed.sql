-- Create sequence for FruitManFeed
DROP SEQUENCE IF EXISTS fruit_man_feed_seq;
CREATE SEQUENCE fruit_man_feed_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Create table for FruitManFeed
CREATE TABLE public.fruit_man_feed (
   fruit_man_feed_id BIGINT PRIMARY KEY,
   feed_url VARCHAR(255),
   feed_type VARCHAR(255),
   feed_source VARCHAR(255),
   thumbnail VARCHAR(255),
   title VARCHAR(255),
   created_at BIGINT NOT NULL,
   updated_at BIGINT NOT NULL
);

-- Create indexes on FruitManFeed table
CREATE INDEX fruit_man_feed_idx01 ON public.fruit_man_feed (created_at);
CREATE INDEX fruit_man_feed_idx02 ON public.fruit_man_feed (updated_at);
