CREATE TABLE patient_stage
(
  identifier serial NOT NULL,
  study_identifier character varying(200),
  study_protocol_identifier bigint,
  assigned_identifier character varying(200),
  race_code character varying(200),
  sex_code character varying(200),
  ethnic_code character varying(200),
  birth_date character varying(200),
  country_code character varying(200),
  zip character varying(200),
  payment_method_code character varying(200),
  study_site character varying(200),
  disease_code character varying(200),
  registration_date character varying(200),
  registration_group_id character varying(200),
  accrual_count integer,
  file_name character varying(1000),
  submission_status character varying(200),
  date_last_created timestamp,
  date_last_updated timestamp,
  user_last_created_id integer,
  user_last_updated_id integer,
  CONSTRAINT patient_stage_pkey PRIMARY KEY (identifier),
  CONSTRAINT fk_patient_stage_created_csm_user FOREIGN KEY (user_last_created_id)
      REFERENCES csm_user (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_patient_stage_updated_csm_user FOREIGN KEY (user_last_updated_id)
      REFERENCES csm_user (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);