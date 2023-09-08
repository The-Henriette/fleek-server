-- Create sequence for Cart
DROP SEQUENCE IF EXISTS cart_seq;
CREATE SEQUENCE cart_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Create table for Cart
CREATE TABLE public.cart (
     cart_id BIGINT PRIMARY KEY,
     cart_type VARCHAR(255),
     purchase_option VARCHAR(255),
     fruit_man_user_id BIGINT NOT NULL,
     order_id VARCHAR(255),
     created_at BIGINT NOT NULL,
     updated_at BIGINT NOT NULL
);

-- Create indexes on Cart table
CREATE INDEX idx_cart_fruit_man_user_id ON public.cart (fruit_man_user_id);
CREATE INDEX cart_idx01 ON public.cart (created_at);
CREATE INDEX cart_idx02 ON public.cart (updated_at);

alter table user_deal add column cart_id bigint;
create index user_deal_idx_cart_id on user_deal (cart_id);

alter table cart add column payment_detail text;

alter table cart add column purchase_price integer;
alter table cart add column delivery_price integer;

