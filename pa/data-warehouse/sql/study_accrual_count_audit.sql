DROP TABLE IF EXISTS stg_dw_study_accrual_count_audit;

CREATE TABLE stg_dw_study_accrual_count_audit
(
  id serial,
  study_site_id bigint NOT NULL,
  accrual_count integer NOT NULL,
  entityid bigint,
  createddate timestamp without time zone NOT NULL,
  PRIMARY KEY (id)
);

--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_study_accrual_count_audit
(
 RUN_ID TIMESTAMP,
  id serial,
  study_site_id bigint NOT NULL,
  accrual_count integer NOT NULL,
  entityid bigint,
  createddate timestamp without time zone NOT NULL
 
);