DROP TABLE IF EXISTS STG_DW_PERSON_ROLE;

CREATE TABLE STG_DW_PERSON_ROLE (
	ROLE_NAME character varying (50),
	ORGANIZATION_NAME character varying (160),
	ADDRESS_LINE_1 character varying (254),
	ADDRESS_LINE_2 character varying (254),
	POSTAL_CODE character varying (30),
	CITY character varying (50),
	COUNTRY character varying (50),
	CURATOR_COMMENT text,
    PERSON_PO_ID integer NOT NULL,
    ROLE_PO_ID integer,
	STATUS character varying (20),
	STATUS_DATE date,
	STATE_OR_PROVINCE character varying (255),
	EMAIL character varying (2000),
	FAX character varying (2000),
	PHONE character varying (2000),
	TTY character varying (2000)
	)
	;
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_PERSON_ROLE (
RUN_ID TIMESTAMP,
    ROLE_NAME character varying (50),
    ORGANIZATION_NAME character varying (160),
    ADDRESS_LINE_1 character varying (254),
    ADDRESS_LINE_2 character varying (254),
    POSTAL_CODE character varying (30),
    CITY character varying (50),
    COUNTRY character varying (50),
    CURATOR_COMMENT text,
    PERSON_PO_ID integer NOT NULL,
    ROLE_PO_ID integer,
    STATUS character varying (20),
    STATUS_DATE date,
    STATE_OR_PROVINCE character varying (255),
    EMAIL character varying (2000),
    FAX character varying (2000),
    PHONE character varying (2000),
    TTY character varying (2000)
    )
    ;