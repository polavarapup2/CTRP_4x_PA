DROP TABLE IF EXISTS STG_DW_STUDY_IND_IDE;
CREATE TABLE STG_DW_STUDY_IND_IDE (
    DATE_LAST_CREATED timestamp,
    DATE_LAST_UPDATED timestamp,
    EXEMPT_INDICATOR boolean,
    EXPANDED_ACCESS_INDICATOR character varying(3),
    EXPANDED_ACCESS_STATUS_CODE character varying(200),
    GRANTOR_CODE character varying(200),
    HOLDER_TYPE_CODE character varying(200),
    IND_IDE_NUMBER character varying(200),
    IND_IDE_TYPE_CODE character varying(200),
    INTERNAL_SYSTEM_ID INTEGER not null,
    NCI_DIV_PROG_HOLDER_CODE character varying(200),
    NCI_ID character varying(255),
    NIH_INSTHOLDER_CODE character varying(200),
    NIH_INSTHOLDER_NAME character varying(200),
    USER_LAST_CREATED character varying(500),
    USER_LAST_UPDATED character varying(500),
    PRIMARY KEY(INTERNAL_SYSTEM_ID)
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_STUDY_IND_IDE (
 RUN_ID TIMESTAMP,
    DATE_LAST_CREATED timestamp,
    DATE_LAST_UPDATED timestamp,
    EXEMPT_INDICATOR boolean,
    EXPANDED_ACCESS_INDICATOR character varying(3),
    EXPANDED_ACCESS_STATUS_CODE character varying(200),
    GRANTOR_CODE character varying(200),
    HOLDER_TYPE_CODE character varying(200),
    IND_IDE_NUMBER character varying(200),
    IND_IDE_TYPE_CODE character varying(200),
    INTERNAL_SYSTEM_ID INTEGER not null,
    NCI_DIV_PROG_HOLDER_CODE character varying(200),
    NCI_ID character varying(255),
    NIH_INSTHOLDER_CODE character varying(200),
    NIH_INSTHOLDER_NAME character varying(200),
    USER_LAST_CREATED character varying(500),
    USER_LAST_UPDATED character varying(500)
);
