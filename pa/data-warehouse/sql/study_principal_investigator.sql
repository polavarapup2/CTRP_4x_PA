DROP TABLE IF EXISTS STG_DW_STUDY_PRINCIPAL_INVESTIGATOR CASCADE;
CREATE TABLE STG_DW_STUDY_PRINCIPAL_INVESTIGATOR (
    INTERNAL_SYSTEM_ID INTEGER,
    NCI_ID character varying(255),
    PI_FIRST_NAME character varying(200),
    PI_LAST_NAME character varying(200),
    PI_ROLE character varying(200),
    PERSON_PO_ID character varying(200)
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_STUDY_PRINCIPAL_INVESTIGATOR (
RUN_ID TIMESTAMP,
    INTERNAL_SYSTEM_ID INTEGER,
    NCI_ID character varying(255),
    PI_FIRST_NAME character varying(200),
    PI_LAST_NAME character varying(200),
    PI_ROLE character varying(200),
    PERSON_PO_ID character varying(200)
);

