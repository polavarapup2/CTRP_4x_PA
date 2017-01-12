DROP TABLE IF EXISTS stg_dw_study_association;

CREATE TABLE stg_dw_study_association (
  study_a character varying(255),
  study_b character varying(255),
  identifier_type character varying(32),
  study_protocol_type character varying(32),
  study_subtype_code character varying(64),
  official_title character varying(4000)
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_study_association (
 RUN_ID TIMESTAMP,
  study_a character varying(255),
  study_b character varying(255),
  identifier_type character varying(32),
  study_protocol_type character varying(32),
  study_subtype_code character varying(64),
  official_title character varying(4000)
);
