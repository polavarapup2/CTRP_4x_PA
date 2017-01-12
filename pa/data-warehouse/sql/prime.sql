/*
Script needs to be run on any tiers which have data warehouse after refresh from production
*/


CREATE TABLE IF NOT EXISTS DW_STUDY_AUDIT ( 
	NCI_ID character varying (50),
	DATE timestamp,
	USERNAME character varying (255),
	FIRST_NAME character varying(255),
	LAST_NAME character varying(255),
	TYPE character varying(255),
	INTERNAL_SYSTEM_ID INTEGER
	)
	;
CREATE TABLE IF NOT EXISTS DW_ORGANIZATION_AUDIT ( 
	CTEP_ID character varying (50),
	NAME character varying (255),
	PO_ID integer,
	DATE timestamp,
	USERNAME character varying (255),
	INTERNAL_SYSTEM_ID INTEGER
	)
	;
CREATE TABLE IF NOT EXISTS DW_PERSON_AUDIT ( 
	CTEP_ID character varying (50),
	NAME character varying (255),
	PO_ID integer,
	DATE timestamp,
	USERNAME character varying (255),
	FIRST_NAME character varying(255),
	LAST_NAME character varying(255),
	INTERNAL_SYSTEM_ID INTEGER
	)
	;
	CREATE TABLE IF NOT EXISTS DW_RUN ( 
     RUN_ID  timestamp PRIMARY KEY NOT NULL
    
    )
    ;
    
--Create history tables
    
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
CREATE TABLE IF NOT EXISTS HIST_DW_ORGANIZATION_AUDIT ( 
    RUN_ID TIMESTAMP,
    CTEP_ID character varying (50),
    NAME character varying (255),
    PO_ID integer,
    DATE timestamp,
    USERNAME character varying (255),
    INTERNAL_SYSTEM_ID INTEGER
    )
    ;
CREATE TABLE IF NOT EXISTS HIST_DW_PERSON_AUDIT ( 
    RUN_ID TIMESTAMP,
    CTEP_ID character varying (50),
    NAME character varying (255),
    PO_ID integer,
    DATE timestamp,
    USERNAME character varying (255),
    FIRST_NAME character varying(255),
    LAST_NAME character varying(255),
    INTERNAL_SYSTEM_ID INTEGER
    )
    ;

