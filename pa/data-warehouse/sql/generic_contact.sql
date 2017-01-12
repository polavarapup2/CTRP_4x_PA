DROP TABLE IF EXISTS STG_DW_GENERIC_CONTACT;
CREATE TABLE STG_DW_GENERIC_CONTACT (
    IDENTIFIER INTEGER,
    TITLE character varying(255),
    PRIMARY KEY (IDENTIFIER)
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_GENERIC_CONTACT (
 RUN_ID TIMESTAMP,
    IDENTIFIER INTEGER,
    TITLE character varying(255)
);