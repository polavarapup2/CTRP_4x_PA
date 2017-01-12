DROP TABLE IF EXISTS STG_DW_PERSON;

CREATE TABLE STG_DW_PERSON ( 
	ADDRESS_LINE_1 character varying (254),
	ADDRESS_LINE_2 character varying (254),
	POSTAL_CODE character varying (30),
	BIRTHDATE date,
	CITY character varying (50),
	COUNTRY character varying (50),
	CTEP_ID character varying (50),
	CURATOR_COMMENT text,
	NAME character varying (255),
	PO_ID integer,
	PREFIX character varying (10),
	STATUS character varying (20),
	STATUS_DATE date,
	SEX_CODE character varying (20),
	RACE_CODE character varying (255),
	STATE_OR_PROVINCE character varying (255),
	SUFFIX character varying (10),
	EMAIL character varying (2000),
	FAX character varying (2000),
	PHONE character varying (2000),
	TTY character varying (2000),
	WEBSITE_URL character varying (2000)
	)
	;

ALTER TABLE stg_dw_person
  ADD PRIMARY KEY (po_id);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_PERSON ( 
RUN_ID TIMESTAMP,
    ADDRESS_LINE_1 character varying (254),
    ADDRESS_LINE_2 character varying (254),
    POSTAL_CODE character varying (30),
    BIRTHDATE date,
    CITY character varying (50),
    COUNTRY character varying (50),
    CTEP_ID character varying (50),
    CURATOR_COMMENT text,
    NAME character varying (255),
    PO_ID integer,
    PREFIX character varying (10),
    STATUS character varying (20),
    STATUS_DATE date,
    SEX_CODE character varying (20),
    RACE_CODE character varying (255),
    STATE_OR_PROVINCE character varying (255),
    SUFFIX character varying (10),
    EMAIL character varying (2000),
    FAX character varying (2000),
    PHONE character varying (2000),
    TTY character varying (2000),
    WEBSITE_URL character varying (2000)
    )
    ;