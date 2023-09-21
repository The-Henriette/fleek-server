-- Create sequence for UserDeal
DROP SEQUENCE IF EXISTS user_deal_seq;
CREATE SEQUENCE user_deal_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Create table for UserDeal
CREATE TABLE public.user_deal (
      user_deal_id BIGINT PRIMARY KEY,
      fruit_man_user_id BIGINT,
      deal_id BIGINT,
      order_id VARCHAR(255),
      ordered_at BIGINT,
      pdd BIGINT,
      paid_at BIGINT,
      tracking_status VARCHAR(255),
      purchase_option VARCHAR(255),
      created_at BIGINT NOT NULL,
      updated_at BIGINT NOT NULL
);

-- Create indexes on UserDeal table
CREATE INDEX idx_user_deal_fruit_man_user_id ON public.user_deal (fruit_man_user_id);
CREATE INDEX idx_user_deal_deal_id ON public.user_deal (deal_id);
CREATE INDEX user_deal_idx01 ON public.user_deal (created_at);
CREATE INDEX user_deal_idx02 ON public.user_deal (updated_at);
CREATE INDEX user_deal_idx03 ON public.user_deal (ordered_at);
CREATE INDEX user_deal_idx04 ON public.user_deal (order_id);

-- Create sequence for UserDealTracking
DROP SEQUENCE IF EXISTS user_deal_tracking_seq;
CREATE SEQUENCE user_deal_tracking_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Create table for UserDealTracking
CREATE TABLE public.user_deal_tracking (
   user_deal_tracking_id BIGINT PRIMARY KEY,
   user_deal_id BIGINT,
   tracking_status VARCHAR(255),
   tracking_at BIGINT,
   shipping_label_number VARCHAR(255),
   created_at BIGINT NOT NULL,
   updated_at BIGINT NOT NULL
);

-- Create indexes on UserDealTracking table
CREATE INDEX idx_user_deal_tracking_user_deal_id ON public.user_deal_tracking (user_deal_id);
CREATE INDEX user_deal_tracking_idx01 ON public.user_deal_tracking (created_at);
CREATE INDEX user_deal_tracking_idx02 ON public.user_deal_tracking (updated_at);

-- Create sequence for UserDeliveryDetail
DROP SEQUENCE IF EXISTS user_delivery_detail_seq;
CREATE SEQUENCE user_delivery_detail_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Create table for UserDeliveryDetail
CREATE TABLE public.user_delivery_detail (
     user_delivery_detail_id BIGINT PRIMARY KEY,
     user_deal_id BIGINT,
     recipient_name VARCHAR(255),
     recipient_phone_number VARCHAR(255),
     postal_code VARCHAR(255),
     main_address VARCHAR(255),
     sub_address VARCHAR(255),
     created_at BIGINT NOT NULL,
     updated_at BIGINT NOT NULL
);

-- Create indexes on UserDeliveryDetail table
CREATE INDEX idx_user_delivery_detail_user_deal_id ON public.user_delivery_detail (user_deal_id);
CREATE INDEX user_delivery_detail_idx01 ON public.user_delivery_detail (created_at);
CREATE INDEX user_delivery_detail_idx02 ON public.user_delivery_detail (updated_at);

alter table user_delivery_detail add column delivery_memo varchar(255);

-- Create sequence for UserPayment
DROP SEQUENCE IF EXISTS user_payment_seq;
CREATE SEQUENCE user_payment_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Create table for UserPayment
CREATE TABLE public.user_payment (
     user_payment_id BIGINT PRIMARY KEY,
     user_deal_id BIGINT,
     payment_method VARCHAR(255),
     amount INTEGER,
     created_at BIGINT NOT NULL,
     updated_at BIGINT NOT NULL
);

-- Create indexes on UserPayment table
CREATE INDEX idx_user_payment_user_deal_id ON public.user_payment (user_deal_id);
CREATE INDEX user_payment_idx01 ON public.user_payment (created_at);
CREATE INDEX user_payment_idx02 ON public.user_payment (updated_at);

alter table user_payment add column toss_payment_key text;

-- Create sequence for UserPaymentReceipt
DROP SEQUENCE IF EXISTS user_payment_receipt_seq;
CREATE SEQUENCE user_payment_receipt_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Create table for UserPaymentReceipt
CREATE TABLE public.user_payment_receipt (
     user_payment_receipt_id BIGINT PRIMARY KEY,
     user_payment_id BIGINT,
     receipt_purpose VARCHAR(255),
     receipt_target_type VARCHAR(255),
     receipt_target VARCHAR(255),
     created_at BIGINT NOT NULL,
     updated_at BIGINT NOT NULL
);

-- Create indexes on UserPaymentReceipt table
CREATE INDEX idx_user_payment_receipt_user_payment_id ON public.user_payment_receipt (user_payment_id);
CREATE INDEX user_payment_receipt_idx01 ON public.user_payment_receipt (created_at);
CREATE INDEX user_payment_receipt_idx02 ON public.user_payment_receipt (updated_at);

alter table user_payment add column payment_due bigint;
alter table user_payment add column refund_due bigint;
alter table user_payment add column refund_amount bigint;