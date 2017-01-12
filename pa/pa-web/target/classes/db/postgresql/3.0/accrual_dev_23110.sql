ALTER TABLE study_subject RENAME user_last_create  TO user_last_created;
ALTER TABLE study_subject_audit RENAME user_last_create  TO user_last_created;
ALTER TABLE patient RENAME user_last_create  TO user_last_created;
ALTER TABLE patient_audit RENAME user_last_create  TO user_last_created;
ALTER TABLE study_subject ADD COLUMN HEALTHCARE_PROVIDER_IDENTIFIER BIGINT;
ALTER TABLE study_subject ADD COLUMN CLINICAL_RESEARCH_STAFF_IDENTIFIER BIGINT;

