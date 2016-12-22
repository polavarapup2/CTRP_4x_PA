ALTER TABLE study_subject_audit ADD COLUMN HEALTHCARE_PROVIDER_IDENTIFIER BIGINT;
ALTER TABLE study_subject_audit ADD COLUMN CLINICAL_RESEARCH_STAFF_IDENTIFIER BIGINT;
alter table patient add column country_identifier bigint;
alter table patient add column zip varchar(200);
alter table patient_audit add column country_identifier bigint;
alter table patient_audit add column zip varchar(200);
ALTER TABLE study_subject ALTER COLUMN payment_method_code DROP NOT NULL;
ALTER TABLE study_subject_audit ALTER COLUMN payment_method_code DROP NOT NULL;
