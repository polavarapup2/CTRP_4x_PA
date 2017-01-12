DROP TABLE IF EXISTS STG_DW_BIOMARKER_USE;
CREATE TABLE STG_DW_BIOMARKER_USE (
  type_code character varying(200),
  description_text character varying(400),
  cadsr_id integer
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_BIOMARKER_USE (
 RUN_ID TIMESTAMP,
  type_code character varying(200),
  description_text character varying(400),
  cadsr_id integer
);