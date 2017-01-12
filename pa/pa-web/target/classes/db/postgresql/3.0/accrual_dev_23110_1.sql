alter table performed_activity add column study_subject_identifier bigint NOT NULL;
alter table performed_activity add column registration_date timestamp;

ALTER TABLE performed_activity ADD CONSTRAINT fk_performed_activity_study_subject
FOREIGN KEY (study_subject_identifier) REFERENCES study_subject(identifier)
ON DELETE SET NULL;

