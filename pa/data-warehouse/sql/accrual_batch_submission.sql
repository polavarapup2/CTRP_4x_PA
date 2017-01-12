DROP TABLE IF EXISTS STG_DW_ACCRUAL_BATCH_SUBMISSION;

CREATE TABLE STG_DW_ACCRUAL_BATCH_SUBMISSION (
    BATCH_FILE_IDENTIFIER bigint NOT NULL,
    CHANGE_CODE character varying(200),
    CORRESPONDING_NCI_ID character varying(255),
    DATE_LAST_CREATED timestamp without time zone,
    DATE_LAST_UPDATED timestamp without time zone,
    PASSED_VALIDATION boolean NOT NULL,
    SUCCESSFUL_IMPORTS integer,
    RESULT_COMMENTS character varying(1000),
    STUDY_ID_SUBMITTED character varying(255),
    USER_NAME_LAST_CREATED character varying(500),
    USER_NAME_LAST_UPDATED character varying(500),
    USER_LAST_CREATED_ID integer,
    USER_LAST_UPDATED_ID integer
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_ACCRUAL_BATCH_SUBMISSION (
RUN_ID TIMESTAMP,
    BATCH_FILE_IDENTIFIER bigint NOT NULL,
    CHANGE_CODE character varying(200),
    CORRESPONDING_NCI_ID character varying(255),
    DATE_LAST_CREATED timestamp without time zone,
    DATE_LAST_UPDATED timestamp without time zone,
    PASSED_VALIDATION boolean NOT NULL,
    SUCCESSFUL_IMPORTS integer,
    RESULT_COMMENTS character varying(1000),
    STUDY_ID_SUBMITTED character varying(255),
    USER_NAME_LAST_CREATED character varying(500),
    USER_NAME_LAST_UPDATED character varying(500),
    USER_LAST_CREATED_ID integer,
    USER_LAST_UPDATED_ID integer
);