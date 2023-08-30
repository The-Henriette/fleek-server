-- Create sequence for Deal
DROP SEQUENCE IF EXISTS deal_seq;
CREATE SEQUENCE deal_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Create table for Deal
CREATE TABLE public.deal (
     deal_id BIGINT PRIMARY KEY,
     deal_name VARCHAR(255),
     market_price INTEGER,
     sales_price INTEGER,
     delivery_price INTEGER,
     deal_thumbnail VARCHAR(255),
     deal_images text,
     delivery_methods VARCHAR(255),
     delivery_area_group_id BIGINT, -- Foreign key reference

     effected_at BIGINT NOT NULL,
     expired_at BIGINT NOT NULL,
     created_at BIGINT NOT NULL,
     updated_at BIGINT NOT NULL
);

-- Create indexes on Deal table
CREATE INDEX deal_idx01 ON public.deal (created_at);
CREATE INDEX deal_idx02 ON public.deal (updated_at);
CREATE INDEX deal_idx03 ON public.deal (effected_at);
CREATE INDEX deal_idx04 ON public.deal (expired_at);
CREATE INDEX deal_idx05 ON public.deal (delivery_area_group_id);

-- Create sequence for DealConstraint
DROP SEQUENCE IF EXISTS deal_constraint_seq;
CREATE SEQUENCE deal_constraint_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Create table for DealConstraint
CREATE TABLE public.deal_constraint (
    deal_constraint_id BIGINT PRIMARY KEY,
    deal_id BIGINT, -- Foreign key reference
    required_quantity INTEGER,
    current_quantity INTEGER,
    created_at BIGINT NOT NULL,
    updated_at BIGINT NOT NULL
);

-- Create index on DealConstraint table
CREATE INDEX deal_constraint_idx01 ON public.deal_constraint (created_at);
CREATE INDEX deal_constraint_idx02 ON public.deal_constraint (updated_at);
CREATE INDEX deal_constraint_idx03 ON public.deal_constraint (deal_id);

-- Create sequence for DealPurchaseOption
DROP SEQUENCE IF EXISTS deal_purchase_option_seq;
CREATE SEQUENCE deal_purchase_option_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Create table for DealPurchaseOption
CREATE TABLE public.deal_purchase_option (
 deal_purchase_option_id BIGINT PRIMARY KEY,
 deal_id BIGINT,
 purchase_option VARCHAR(255),
 price INTEGER,
 created_at BIGINT NOT NULL,
 updated_at BIGINT NOT NULL
);

-- Create indexes on DealPurchaseOption table
CREATE INDEX deal_purchase_option_idx01 ON public.deal_purchase_option (created_at);
CREATE INDEX deal_purchase_option_idx02 ON public.deal_purchase_option (updated_at);
CREATE INDEX deal_purchase_option_idx03 ON public.deal_purchase_option (deal_id);

-- Create sequence for DealSku
DROP SEQUENCE IF EXISTS deal_sku_seq;
CREATE SEQUENCE deal_sku_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Create table for DealSku
CREATE TABLE public.deal_sku (
 deal_sku_id BIGINT PRIMARY KEY,
 deal_id BIGINT,
 sku_id BIGINT,
 produced_location VARCHAR(255),
 produced_at BIGINT,
 produced_by VARCHAR(255),
 created_at BIGINT NOT NULL,
 updated_at BIGINT NOT NULL
);

-- Create indexes on DealSku table
CREATE INDEX deal_sku_idx01 ON public.deal_sku (created_at);
CREATE INDEX deal_sku_idx02 ON public.deal_sku (updated_at);
CREATE INDEX deal_sku_idx03 ON public.deal_sku (deal_id);
CREATE INDEX deal_sku_idx04 ON public.deal_sku (sku_id);
