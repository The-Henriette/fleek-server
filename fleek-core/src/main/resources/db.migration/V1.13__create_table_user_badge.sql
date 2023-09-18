-- Create sequence for UserBadge
DROP SEQUENCE IF EXISTS user_badge_seq;
CREATE SEQUENCE user_badge_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Create table for UserBadge
CREATE TABLE public.user_badge (
   user_badge_id BIGINT PRIMARY KEY,
   fleek_user_id BIGINT,
   certification_code VARCHAR(255),
   created_at BIGINT NOT NULL,
   updated_at BIGINT NOT NULL
);

-- Create indexes on UserBadge table
CREATE INDEX idx_user_badge_fleek_user_id ON public.user_badge (fleek_user_id);
CREATE INDEX user_badge_idx01 ON public.user_badge (created_at);
CREATE INDEX user_badge_idx02 ON public.user_badge (updated_at);
