DROP TABLE IF EXISTS STG_DW_STUDY_AUDIT;

CREATE TABLE STG_DW_STUDY_AUDIT ( 
	NCI_ID character varying (50),
	DATE timestamp,
	USERNAME character varying (255),
	FIRST_NAME character varying(255),
	LAST_NAME character varying(255),
	TYPE character varying(255),
	INTERNAL_SYSTEM_ID INTEGER
	)
	;

ALTER TABLE stg_dw_study_audit ADD PRIMARY KEY (internal_system_id);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_STUDY_AUDIT ( 
 RUN_ID TIMESTAMP,
    NCI_ID character varying (50),
    DATE timestamp,
    USERNAME character varying (255),
    FIRST_NAME character varying(255),
    LAST_NAME character varying(255),
    TYPE character varying(255),
    INTERNAL_SYSTEM_ID INTEGER
    )
    ;
