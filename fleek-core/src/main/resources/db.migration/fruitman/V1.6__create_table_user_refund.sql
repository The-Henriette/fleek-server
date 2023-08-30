-- Create sequence for UserRefundInfo
DROP SEQUENCE IF EXISTS user_refund_info_seq;
CREATE SEQUENCE user_refund_info_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Create table for UserRefundInfo
CREATE TABLE public.user_refund_info (
 user_refund_info_id BIGINT PRIMARY KEY,
 fruit_man_user_id BIGINT,
 refund_account_name VARCHAR(255),
 refund_bank_name VARCHAR(255),
 refund_account_number VARCHAR(255),
 created_at BIGINT NOT NULL,
 updated_at BIGINT NOT NULL
);

-- Create indexes on UserRefundInfo table
CREATE INDEX idx_user_refund_info_fruit_man_user_id ON public.user_refund_info (fruit_man_user_id);
CREATE INDEX user_refund_info_idx01 ON public.user_refund_info (created_at);
CREATE INDEX user_refund_info_idx02 ON public.user_refund_info (updated_at);
