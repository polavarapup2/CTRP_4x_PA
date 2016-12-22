DROP TABLE IF EXISTS STG_DW_STUDY_OVERALL_STATUS;
CREATE TABLE STG_DW_STUDY_OVERALL_STATUS ( 
    STATUS character varying(50),
    STATUS_DATE timestamp,
    DATE_CREATED timestamp,
    DATE_LAST_UPDATED timestamp,
    SYSTEM_CREATED boolean,
    WHY_STUDY_STOPPED character varying(2000),
    ADDL_COMMENTS character varying(2000),
    INTERNAL_SYSTEM_ID INTEGER,
    NCI_ID character varying(255),
    USER_CREATED character varying(500),
    USER_LAST_UPDATED character varying(500),
    PRIMARY KEY (INTERNAL_SYSTEM_ID)
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_STUDY_OVERALL_STATUS ( 
RUN_ID TIMESTAMP,
    STATUS character varying(50),
    STATUS_DATE timestamp,
    DATE_CREATED timestamp,
    DATE_LAST_UPDATED timestamp,
    SYSTEM_CREATED boolean,
    WHY_STUDY_STOPPED character varying(2000),
    ADDL_COMMENTS character varying(2000),
    INTERNAL_SYSTEM_ID INTEGER,
    NCI_ID character varying(255),
    USER_CREATED character varying(500),
    USER_LAST_UPDATED character varying(500)
    
);

