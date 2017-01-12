DROP TABLE IF EXISTS STG_DW_STUDY_PROCESSING_STATUS;

CREATE TABLE STG_DW_STUDY_PROCESSING_STATUS (
    COMMENTS character varying(2000),
    DATE timestamp,
    DATE_CREATED timestamp,
    DATE_LAST_UPDATED timestamp,
    INTERNAL_SYSTEM_ID INTEGER not null,
    NCI_ID character varying(255),
    STATUS character varying(50),
    SUBMISSION_NUMBER integer,
    USER_NAME_CREATED character varying(500),
    USER_NAME_LAST_UPDATED character varying(500),
    FIRST_NAME_CREATED character varying(500),
    LAST_NAME_CREATED character varying(500),
    FIRST_NAME_LAST_UPDATED character varying(500),
    LAST_NAME_LAST_UPDATED character varying(500)
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_STUDY_PROCESSING_STATUS (
RUN_ID TIMESTAMP,
    COMMENTS character varying(2000),
    DATE timestamp,
    DATE_CREATED timestamp,
    DATE_LAST_UPDATED timestamp,
    INTERNAL_SYSTEM_ID INTEGER not null,
    NCI_ID character varying(255),
    STATUS character varying(50),
    SUBMISSION_NUMBER integer,
    USER_NAME_CREATED character varying(500),
    USER_NAME_LAST_UPDATED character varying(500),
    FIRST_NAME_CREATED character varying(500),
    LAST_NAME_CREATED character varying(500),
    FIRST_NAME_LAST_UPDATED character varying(500),
    LAST_NAME_LAST_UPDATED character varying(500)
);

