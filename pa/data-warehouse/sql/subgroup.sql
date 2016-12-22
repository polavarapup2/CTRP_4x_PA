DROP TABLE IF EXISTS STG_DW_STUDY_SUBGROUP;
CREATE TABLE STG_DW_STUDY_SUBGROUP (
    DESCRIPTION character varying(1000),
    GROUP_CODE character varying(256),
    NCI_ID character varying(255)
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_STUDY_SUBGROUP (
RUN_ID TIMESTAMP,
    DESCRIPTION character varying(1000),
    GROUP_CODE character varying(256),
    NCI_ID character varying(255)
);
