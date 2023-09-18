-- Create sequence for EmailDomainMeta
DROP SEQUENCE IF EXISTS email_domain_meta_seq;
CREATE SEQUENCE email_domain_meta_seq INCREMENT BY 1 START WITH 1 NO CYCLE;

-- Create table for EmailDomainMeta
CREATE TABLE public.email_domain_meta (
  email_domain_meta_id BIGINT PRIMARY KEY,
  email_domain VARCHAR(255) NOT NULL,
  certification_code VARCHAR(255),
  target_name VARCHAR(255),
  created_at BIGINT NOT NULL,
  updated_at BIGINT NOT NULL
);

-- Create indexes on EmailDomainMeta table
CREATE INDEX email_domain_meta_idx01 ON public.email_domain_meta (email_domain);
CREATE INDEX email_domain_meta_idx02 ON public.email_domain_meta (created_at);
CREATE INDEX email_domain_meta_idx03 ON public.email_domain_meta (updated_at);
