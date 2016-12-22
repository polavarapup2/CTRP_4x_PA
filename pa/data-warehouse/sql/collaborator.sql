DROP TABLE IF EXISTS STG_DW_STUDY_COLLABORATOR;
CREATE TABLE STG_DW_STUDY_COLLABORATOR ( 
    FUNCTIONAL_ROLE character varying(50),
    NAME character varying(200),
    NCI_ID character varying(255),
	STATUS character(20)
    );
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_STUDY_COLLABORATOR ( 
RUN_ID TIMESTAMP,
    FUNCTIONAL_ROLE character varying(50),
    NAME character varying(200),
    NCI_ID character varying(255),
    STATUS character(20)
    );
