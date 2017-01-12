DROP TABLE IF EXISTS STG_DW_STUDY_OTHER_IDENTIFIER;

CREATE TABLE STG_DW_STUDY_OTHER_IDENTIFIER (
	NAME character varying(500),
    NCI_ID character varying(255),
    VALUE character varying(500)
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_STUDY_OTHER_IDENTIFIER (
RUN_ID TIMESTAMP,
    NAME character varying(500),
    NCI_ID character varying(255),
    VALUE character varying(500)
);

