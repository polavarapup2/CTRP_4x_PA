UPDATE study_subject SET submission_type = 'UNKNOWN';
ALTER TABLE study_subject ALTER COLUMN submission_type SET NOT NULL;
ALTER TABLE batch_file ADD COLUMN submission_type CHARACTER VARYING(200);
UPDATE batch_file SET submission_type = 'UNKNOWN';
ALTER TABLE batch_file ALTER COLUMN submission_type SET NOT NULL;
