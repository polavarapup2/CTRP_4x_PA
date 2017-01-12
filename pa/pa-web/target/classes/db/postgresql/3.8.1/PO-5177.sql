CREATE TABLE accrual_collections
(
  identifier serial NOT NULL,
  study_protocol_identifier bigint NOT NULL,
  batch_file_identifier bigint NOT NULL,
  passed_validation boolean NOT NULL,
  change_code char(1),
  results character varying(1000),
  date_last_created timestamp without time zone,
  date_last_updated timestamp without time zone,
  user_last_created_id integer,
  user_last_updated_id integer,
  CONSTRAINT accrual_collections_pkey PRIMARY KEY (identifier),
  CONSTRAINT fk_accrual_collections_study_protocol FOREIGN KEY (study_protocol_identifier)
      REFERENCES study_protocol (identifier) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_accrual_collections_batch_file FOREIGN KEY (batch_file_identifier)
      REFERENCES batch_file (identifier) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_accrual_collections_created_csm_user FOREIGN KEY (user_last_created_id)
      REFERENCES csm_user (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_accrual_collections_updated_csm_user FOREIGN KEY (user_last_updated_id)
      REFERENCES csm_user (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

