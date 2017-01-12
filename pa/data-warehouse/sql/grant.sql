DROP TABLE IF EXISTS STG_DW_STUDY_GRANT;
CREATE TABLE STG_DW_STUDY_GRANT (
    ACTIVE_INDICATOR character varying(3),
    FUNDING_MECHANISM_CODE character varying(200),
    INTERNAL_SYSTEM_ID INTEGER not null,
    NCI_DIVISION_OR_PROGRAM character varying(200),
    NCI_ID character varying(255),
    NIH_INSTITUTION_CODE character varying(200),
    SERIAL_NUMBER character varying(200),
    DELETED_BY character varying(500),
    DELETION_DATE timestamp without time zone,
    REASON_FOR_DELETE character varying(200),
    USER_LAST_UPDATED_ID integer,
    PRIMARY KEY(INTERNAL_SYSTEM_ID)
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_STUDY_GRANT (
RUN_ID TIMESTAMP,
    ACTIVE_INDICATOR character varying(3),
    FUNDING_MECHANISM_CODE character varying(200),
    INTERNAL_SYSTEM_ID INTEGER not null,
    NCI_DIVISION_OR_PROGRAM character varying(200),
    NCI_ID character varying(255),
    NIH_INSTITUTION_CODE character varying(200),
    SERIAL_NUMBER character varying(200),
    DELETED_BY character varying(500),
    DELETION_DATE timestamp without time zone,
    REASON_FOR_DELETE character varying(200),
    USER_LAST_UPDATED_ID integer
);

