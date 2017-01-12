ALTER TABLE study_subject ADD COLUMN site_disease_identifier bigint;

ALTER TABLE study_subject ADD CONSTRAINT fk_study_subject_site_disease 
  FOREIGN KEY (site_disease_identifier) REFERENCES accrual_disease (identifier)
  ON UPDATE NO ACTION ON DELETE RESTRICT;