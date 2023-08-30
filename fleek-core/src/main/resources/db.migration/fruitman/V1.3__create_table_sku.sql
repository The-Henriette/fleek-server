-- Create sequence for SKU
DROP SEQUENCE IF EXISTS sku_seq;
CREATE SEQUENCE sku_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Create table for SKU
CREATE TABLE public.sku (
    sku_id BIGINT PRIMARY KEY,
    sku_name VARCHAR(255) NOT NULL,
    sku_main_image VARCHAR(255),
    sku_images text,
    created_at BIGINT NOT NULL,
    updated_at BIGINT NOT NULL
);

-- Add unique constraint on SKU name
ALTER TABLE public.sku ADD CONSTRAINT sku_uk01 UNIQUE (sku_name);

-- Create indexes on SKU table
CREATE INDEX sku_idx01 ON public.sku (created_at);
CREATE INDEX sku_idx02 ON public.sku (updated_at);
