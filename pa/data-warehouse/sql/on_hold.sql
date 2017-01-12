DROP TABLE IF EXISTS STG_DW_STUDY_ON_HOLD_STATUS;

CREATE TABLE STG_DW_STUDY_ON_HOLD_STATUS (
    REASON_DESCRIPTION character varying(2000),
	REASON character varying(200),
    ON_HOLD_DATE timestamp,
    OFF_HOLD_DATE timestamp,
    DATE_CREATED timestamp,
    DATE_LAST_UPDATED timestamp,
    INTERNAL_SYSTEM_ID INTEGER not null,
    NCI_ID character varying(255),
    USER_NAME_CREATED character varying(500),
    USER_NAME_LAST_UPDATED character varying(500),
    FIRST_NAME_CREATED character varying(500),
    LAST_NAME_CREATED character varying(500),
    FIRST_NAME_LAST_UPDATED character varying(500),
    LAST_NAME_LAST_UPDATED character varying(500),
    USER_LAST_CREATED_ID integer,
    USER_LAST_UPDATED_ID integer
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_STUDY_ON_HOLD_STATUS (
RUN_ID TIMESTAMP,
    REASON_DESCRIPTION character varying(2000),
    REASON character varying(200),
    ON_HOLD_DATE timestamp,
    OFF_HOLD_DATE timestamp,
    DATE_CREATED timestamp,
    DATE_LAST_UPDATED timestamp,
    INTERNAL_SYSTEM_ID INTEGER not null,
    NCI_ID character varying(255),
    USER_NAME_CREATED character varying(500),
    USER_NAME_LAST_UPDATED character varying(500),
    FIRST_NAME_CREATED character varying(500),
    LAST_NAME_CREATED character varying(500),
    FIRST_NAME_LAST_UPDATED character varying(500),
    LAST_NAME_LAST_UPDATED character varying(500),
    USER_LAST_CREATED_ID integer,
    USER_LAST_UPDATED_ID integer
);

