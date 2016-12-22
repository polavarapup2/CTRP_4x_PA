DROP TABLE IF EXISTS STG_DW_STUDY_ELIGIBILITY_CRITERIA;

CREATE TABLE STG_DW_STUDY_ELIGIBILITY_CRITERIA (
	CDE_PUBLIC_IDENTIFIER integer,
	DISPLAY_ORDER integer,
    CDE_VERSION character varying(25),
    ELIGIBLE_GENDER_CODE character varying(25),
    INCLUSION_INDICATOR boolean,
    STRUCTURED_INDICATOR boolean,
    INTERNAL_SYSTEM_ID integer,
    CRITERION_NAME character varying(500),
    NCI_ID character varying(255),
    OPERATOR character varying(50),
    UNIT character varying(50),
    DESCRIPTION character varying (5000),
    VALUE character varying(50)    
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_STUDY_ELIGIBILITY_CRITERIA (
RUN_ID TIMESTAMP,
    CDE_PUBLIC_IDENTIFIER integer,
    DISPLAY_ORDER integer,
    CDE_VERSION character varying(25),
    ELIGIBLE_GENDER_CODE character varying(25),
    INCLUSION_INDICATOR boolean,
    STRUCTURED_INDICATOR boolean,
    INTERNAL_SYSTEM_ID integer,
    CRITERION_NAME character varying(500),
    NCI_ID character varying(255),
    OPERATOR character varying(50),
    UNIT character varying(50),
    DESCRIPTION character varying (5000),
    VALUE character varying(50)    
);

