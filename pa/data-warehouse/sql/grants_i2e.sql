DROP TABLE IF EXISTS stg_dw_grants_i2e;

CREATE TABLE stg_dw_grants_i2e
(

  appl_id bigint NOT NULL,
  grant_number varchar,
  grant_type_code varchar,
  impac_ii_activity_code varchar,
  primary_icd_code varchar,
  serial_number integer,
  support_year integer,
  suffix_code varchar,
  fy integer,
  budget_start_date timestamp without time zone,
  budget_end_date timestamp without time zone,
  project_title varchar,
  project_period_start_date timestamp without time zone,
  project_period_end_date timestamp without time zone,
  institution_name varchar,
  pi_name_prefix varchar,
  pi_first_name varchar,
  pi_mi_name varchar,
  pi_last_name varchar,
  pi_name_suffix varchar,
  pi_title varchar,
  state_name varchar,
  city_name varchar,
  zip_code varchar,
  country_name varchar,
  common_email_addr varchar,
  PRIMARY KEY (appl_id)
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_grants_i2e
(
RUN_ID TIMESTAMP,
  appl_id bigint NOT NULL,
  grant_number varchar,
  grant_type_code varchar,
  impac_ii_activity_code varchar,
  primary_icd_code varchar,
  serial_number integer,
  support_year integer,
  suffix_code varchar,
  fy integer,
  budget_start_date timestamp without time zone,
  budget_end_date timestamp without time zone,
  project_title varchar,
  project_period_start_date timestamp without time zone,
  project_period_end_date timestamp without time zone,
  institution_name varchar,
  pi_name_prefix varchar,
  pi_first_name varchar,
  pi_mi_name varchar,
  pi_last_name varchar,
  pi_name_suffix varchar,
  pi_title varchar,
  state_name varchar,
  city_name varchar,
  zip_code varchar,
  country_name varchar,
  common_email_addr varchar
);
