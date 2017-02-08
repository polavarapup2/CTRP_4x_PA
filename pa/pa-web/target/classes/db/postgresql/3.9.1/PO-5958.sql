ALTER TABLE study_subject DROP CONSTRAINT fk_study_subject_study_site;

ALTER TABLE study_subject ADD CONSTRAINT fk_study_subject_study_site
FOREIGN KEY (study_site_identifier) REFERENCES study_site (identifier)
ON DELETE CASCADE;

DELETE FROM study_site_subject_accrual_count where study_site_identifier is null OR (study_site_identifier IS NOT NULL AND study_site_identifier NOT IN (SELECT identifier FROM study_site where functional_code = 'TREATING_SITE'));

ALTER TABLE study_site_subject_accrual_count DROP CONSTRAINT study_site_subject_accrual_count_study_site_fk;

ALTER TABLE study_site_subject_accrual_count ADD CONSTRAINT study_site_subject_accrual_count_study_site_fk
FOREIGN KEY (study_site_identifier) REFERENCES study_site (identifier)
ON DELETE CASCADE;