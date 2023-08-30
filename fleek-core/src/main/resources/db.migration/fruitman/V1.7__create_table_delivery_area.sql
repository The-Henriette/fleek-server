-- Create sequence for DeliveryAreaGroup
DROP SEQUENCE IF EXISTS delivery_area_group_seq;
CREATE SEQUENCE delivery_area_group_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Create table for DeliveryAreaGroup
CREATE TABLE public.delivery_area_group (
    delivery_area_group_id BIGINT PRIMARY KEY,
    delivery_area_group_name VARCHAR(50) NOT NULL,
    created_at BIGINT NOT NULL,
    updated_at BIGINT NOT NULL
);

-- Create indexes on DeliveryAreaGroup table
CREATE INDEX delivery_area_group_idx01 ON public.delivery_area_group (created_at);
CREATE INDEX delivery_area_group_idx02 ON public.delivery_area_group (updated_at);

-- Create sequence for DeliveryArea
DROP SEQUENCE IF EXISTS delivery_area_seq;
CREATE SEQUENCE delivery_area_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Create table for DeliveryArea
CREATE TABLE public.delivery_area (
      delivery_area_id BIGINT PRIMARY KEY,
      delivery_area_group_id BIGINT,
      postal_code VARCHAR(7),
      created_at BIGINT NOT NULL,
      updated_at BIGINT NOT NULL
);

-- Create indexes on DeliveryArea table
CREATE INDEX idx_delivery_area_delivery_area_group_id ON public.delivery_area (delivery_area_group_id);
CREATE INDEX delivery_area_idx01 ON public.delivery_area (created_at);
CREATE INDEX delivery_area_idx02 ON public.delivery_area (updated_at);
CREATE INDEX delivery_area_idx03 ON public.delivery_area (postal_code);


