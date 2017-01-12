DROP TABLE IF EXISTS STG_DW_AFFILIATE_ORG;

CREATE TABLE STG_DW_AFFILIATE_ORG (
    EMAIL_ADDRESS character varying(255) primary key,
    AFFILIATE_ORG character varying(200),
    AFFILIATED_ORG_ID bigint
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_AFFILIATE_ORG (
RUN_ID TIMESTAMP,
    EMAIL_ADDRESS character varying(255) ,
    AFFILIATE_ORG character varying(200),
    AFFILIATED_ORG_ID bigint
);