DROP TABLE IF EXISTS STG_DW_SUMMARY_4_FUNDING CASCADE;
CREATE TABLE STG_DW_SUMMARY_4_FUNDING (
    INTERNAL_SYSTEM_ID INTEGER,
    NCI_ID character varying(255),
    SPONSOR character varying(200),
    FAMILY character varying(200),
    SPONSOR_ID bigint,
    FAMILY_ID bigint,
    PRIMARY KEY (INTERNAL_SYSTEM_ID)
); 
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_SUMMARY_4_FUNDING (
RUN_ID TIMESTAMP,
    INTERNAL_SYSTEM_ID INTEGER,
    NCI_ID character varying(255),
    SPONSOR character varying(200),
    FAMILY character varying(200),
    SPONSOR_ID bigint,
    FAMILY_ID bigint
); 
