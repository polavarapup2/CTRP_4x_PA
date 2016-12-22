DROP TABLE IF EXISTS STG_DW_STUDY_MILESTONE;

CREATE TABLE STG_DW_STUDY_MILESTONE (
    COMMENTS text,
    DATE timestamp,
    DATE_CREATED timestamp,
    DATE_LAST_UPDATED timestamp,
    INTERNAL_SYSTEM_ID INTEGER not null,
	NAME character varying (50),
    NCI_ID character varying(255),
    SUBMISSION_NUMBER integer,
    USER_NAME_CREATED character varying(500),
    USER_NAME_LAST_UPDATED character varying(500),
    FIRST_NAME_CREATED character varying(500),
    LAST_NAME_CREATED character varying(500),
    FIRST_NAME_LAST_UPDATED character varying(500),
    LAST_NAME_LAST_UPDATED character varying(500),
    user_last_created_id integer,
    user_last_updated_id integer
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_STUDY_MILESTONE (
RUN_ID TIMESTAMP,
    COMMENTS text,
    DATE timestamp,
    DATE_CREATED timestamp,
    DATE_LAST_UPDATED timestamp,
    INTERNAL_SYSTEM_ID INTEGER not null,
    NAME character varying (50),
    NCI_ID character varying(255),
    SUBMISSION_NUMBER integer,
    USER_NAME_CREATED character varying(500),
    USER_NAME_LAST_UPDATED character varying(500),
    FIRST_NAME_CREATED character varying(500),
    LAST_NAME_CREATED character varying(500),
    FIRST_NAME_LAST_UPDATED character varying(500),
    LAST_NAME_LAST_UPDATED character varying(500),
    user_last_created_id integer,
    user_last_updated_id integer
);

ALTER TABLE HIST_DW_STUDY_MILESTONE ALTER COLUMN COMMENTS SET DATA TYPE text;