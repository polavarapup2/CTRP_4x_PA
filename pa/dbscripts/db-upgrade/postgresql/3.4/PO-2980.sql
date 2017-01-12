CREATE TABLE "study_document_stage"
(
  identifier serial NOT NULL,
  type_code character varying(200),
  file_name character varying(200),
  study_protocol_stage_identifier bigint NOT NULL,
  date_last_created timestamp without time zone,
  date_last_updated timestamp without time zone,
  user_last_created_id integer,
  user_last_updated_id integer,
  CONSTRAINT study_document_stage_pkey PRIMARY KEY (identifier),
  CONSTRAINT fk_study_document_stage_created_csm_user FOREIGN KEY (user_last_created_id)
      REFERENCES csm_user (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_study_document_stage_study_protocol FOREIGN KEY (study_protocol_stage_identifier)
      REFERENCES study_protocol_stage (identifier) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_study_document_stage_updated_csm_user FOREIGN KEY (user_last_updated_id)
      REFERENCES csm_user (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)