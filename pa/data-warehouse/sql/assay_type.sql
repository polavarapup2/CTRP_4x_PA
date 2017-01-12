DROP TABLE IF EXISTS STG_DW_ASSAY_TYPE;
CREATE TABLE STG_DW_ASSAY_TYPE  (
  type_code character varying(200),
  description_text character varying(400),
  cadsr_id integer
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_ASSAY_TYPE  (
RUN_ID TIMESTAMP,
  type_code character varying(200),
  description_text character varying(400),
  cadsr_id integer
);
