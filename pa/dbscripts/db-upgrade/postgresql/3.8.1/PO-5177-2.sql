ALTER TABLE accrual_collections DROP COLUMN study_protocol_identifier;
ALTER TABLE accrual_collections ADD COLUMN nci_number CHARACTER VARYING(255);
ALTER TABLE accrual_collections ADD COLUMN total_imports INTEGER;
ALTER TABLE accrual_collections ALTER COLUMN change_code TYPE CHARACTER VARYING(200);