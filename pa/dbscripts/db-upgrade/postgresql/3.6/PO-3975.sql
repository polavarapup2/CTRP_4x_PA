ALTER TABLE performed_activity DROP CONSTRAINT fk_performed_activity_study_subject;

ALTER TABLE performed_activity ADD CONSTRAINT fk_performed_activity_study_subject
FOREIGN KEY (study_subject_identifier) REFERENCES study_subject(identifier)
ON DELETE CASCADE;