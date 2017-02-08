ALTER TABLE study_site_subject_accrual_count ADD COLUMN submission_type CHARACTER VARYING(200);
UPDATE study_site_subject_accrual_count SET submission_type = 'UNKNOWN';
ALTER TABLE study_site_subject_accrual_count ALTER COLUMN submission_type SET NOT NULL;
