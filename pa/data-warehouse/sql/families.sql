DROP TABLE IF EXISTS STG_DW_FAMILY_ORGANIZATION; 
CREATE TABLE STG_DW_FAMILY_ORGANIZATION (
	FAMILY_NAME character varying(200) not null,
	ORGANIZATION_NAME character varying(200) not null,
	FUNCTIONALTYPE character varying(255) not null,
	FAMILY_ID bigint not null,
	ORGANIZATION_ID bigint not null,
	reporting_period_end_date date,
	reporting_period_months integer
	
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_FAMILY_ORGANIZATION (
RUN_ID TIMESTAMP,
    FAMILY_NAME character varying(200) not null,
    ORGANIZATION_NAME character varying(200) not null,
    FUNCTIONALTYPE character varying(255) not null,
    FAMILY_ID bigint not null,
    ORGANIZATION_ID bigint not null,
    reporting_period_end_date date,
    reporting_period_months integer
    
);
