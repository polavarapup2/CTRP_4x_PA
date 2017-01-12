CREATE TABLE study_processing_error
(
  identifier serial NOT NULL,
  study_protocol_identifier bigint NOT NULL,
  error_date timestamp without time zone,
  error_message character varying(5000),
  recurring_error boolean,
  comment character varying(5000),
  error_type character varying(50),
  cms_ticket_id character varying(50),
  action_taken character varying(5000),
  resolution_date timestamp without time zone,
  date_last_created timestamp without time zone,
  date_last_updated timestamp without time zone,
  user_last_created_id integer,
  user_last_updated_id integer,
  CONSTRAINT proc_err_key PRIMARY KEY (identifier),
  CONSTRAINT fk_study_id FOREIGN KEY (study_protocol_identifier)
      REFERENCES study_protocol (identifier) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_sperr_csm_user FOREIGN KEY (user_last_created_id)
      REFERENCES csm_user (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_sperr_updated_csm_user FOREIGN KEY (user_last_updated_id)
      REFERENCES csm_user (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
