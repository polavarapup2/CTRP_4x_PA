DROP TABLE IF EXISTS STG_DW_STUDY_RECORD_OWNER;

CREATE TABLE STG_DW_STUDY_RECORD_OWNER (
    USER_ID bigint,
    CSM_USER_ID bigint,
    ADDRESS_CITY character varying(200),
    ADDRESS_LINE character varying(2000),
    ADDRESS_STATE character varying(100),
    ADDRESS_ZIP_CODE character varying(100),
	EMAIL character varying(200),
	NAME character varying(500),
    NCI_ID character varying(255),
    PHONE_NUMBER character varying(500)
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_STUDY_RECORD_OWNER (
RUN_ID TIMESTAMP,
    USER_ID bigint,
    CSM_USER_ID bigint,
    ADDRESS_CITY character varying(200),
    ADDRESS_LINE character varying(2000),
    ADDRESS_STATE character varying(100),
    ADDRESS_ZIP_CODE character varying(100),
    EMAIL character varying(200),
    NAME character varying(500),
    NCI_ID character varying(255),
    PHONE_NUMBER character varying(500)
);


