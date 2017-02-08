CREATE TABLE accrual_disease
(
  identifier integer NOT NULL,
  code_system text NOT NULL,
  disease_code character varying(10) NOT NULL,
  preferred_name character varying NOT NULL,
  display_name character varying NOT NULL,
  date_last_created timestamp without time zone,
  date_last_updated timestamp without time zone,
  user_last_created_id integer,
  user_last_updated_id integer,
  CONSTRAINT accrual_disease_pkey PRIMARY KEY (identifier),
  CONSTRAINT accrual_disease_disease_code_key UNIQUE (disease_code)
);

INSERT INTO accrual_disease (identifier, code_system, disease_code, preferred_name, display_name)
  SELECT sdc_disease.identifier, 'SDC' AS code_system, sdc_disease.disease_code, sdc_disease.ctep_term AS preferred_name, sdc_disease.ctep_short_name AS display_name
    FROM sdc_disease
  UNION 
  SELECT icd9_disease.identifier + 10000, 'ICD9' AS code_system, icd9_disease.disease_code, ((icd9_disease.name::text || ' ('::text) || icd9_disease.disease_code::text) || ')'::text AS preferred_name, ((icd9_disease.name::text || ' ('::text) || icd9_disease.disease_code::text) || ')'::text AS display_name
    FROM icd9_disease;

ALTER TABLE study_subject DROP CONSTRAINT fk_study_subject_icd9disease;
ALTER TABLE study_subject DROP CONSTRAINT fk_study_subject_disease;

UPDATE study_subject
  SET disease_identifier = icd9disease_identifier + 10000
  WHERE disease_identifier IS NULL;

ALTER TABLE study_subject ADD CONSTRAINT fk_study_subject_disease 
  FOREIGN KEY (disease_identifier) REFERENCES accrual_disease (identifier)
  ON UPDATE NO ACTION ON DELETE RESTRICT;

ALTER TABLE study_subject DROP COLUMN icd9disease_identifier;
