CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS invoice_headers (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  submission_uuid VARCHAR(100) NOT NULL,
  tax_registration_number VARCHAR(20) NOT NULL,
  submission_timestamp TIMESTAMP
);

CREATE TABLE IF NOT EXISTS software_info (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  product_id VARCHAR(100),
  product_version VARCHAR(50),
  software_validation_number VARCHAR(100),
  jws_software_signature TEXT,
  invoice_header_id UUID UNIQUE REFERENCES invoice_headers(id)
);

CREATE TABLE IF NOT EXISTS customers (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  tax_id VARCHAR(20),
  country VARCHAR(5),
  company_name VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS payment_receipts (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  receipt_no VARCHAR(100),
  receipt_date DATE,
  amount NUMERIC(18, 2)
);

CREATE TABLE IF NOT EXISTS source_documents (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  originating_on VARCHAR(100),
  invoice_no VARCHAR(100),
  invoice_date DATE
);

CREATE TABLE IF NOT EXISTS documents (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  invoice_header_id UUID REFERENCES invoice_headers(id),
  customer_id UUID REFERENCES customers(id),
  payment_receipt_id UUID REFERENCES payment_receipts(id),
  source_document_id UUID REFERENCES source_documents(id),
  document_no VARCHAR(100),
  document_status VARCHAR(10),
  document_type VARCHAR(5),
  document_date DATE,
  system_entry_date TIMESTAMP,
  eac_code VARCHAR(20),
  jws_document_signature TEXT,
  tax_payable NUMERIC(18, 2),
  net_total NUMERIC(18, 2),
  gross_total NUMERIC(18, 2)
);

CREATE TABLE IF NOT EXISTS document_lines (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  document_id UUID REFERENCES documents(id),
  line_number INTEGER,
  product_code VARCHAR(100),
  product_description VARCHAR(255),
  quantity NUMERIC(18, 4),
  unit_of_measure VARCHAR(10),
  unit_price NUMERIC(18, 4),
  debit_amount NUMERIC(18, 2),
  credit_amount NUMERIC(18, 2),
  settlement_amount NUMERIC(18, 2),
  reference VARCHAR(100),
  reason VARCHAR(255),
  reference_item_line_no INTEGER
);

CREATE TABLE IF NOT EXISTS taxes (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  document_line_id UUID REFERENCES document_lines(id),
  tax_type VARCHAR(10),
  tax_country_region VARCHAR(10),
  tax_code VARCHAR(10),
  percentage NUMERIC(10, 4),
  amount NUMERIC(18, 2)
);

CREATE TABLE IF NOT EXISTS withholding_taxes (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  document_id UUID REFERENCES documents(id),
  type VARCHAR(10),
  description VARCHAR(255),
  amount NUMERIC(18, 2)
);
