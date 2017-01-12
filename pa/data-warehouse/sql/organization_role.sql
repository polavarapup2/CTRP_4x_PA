DROP TABLE IF EXISTS STG_DW_ORGANIZATION_ROLE;

CREATE TABLE STG_DW_ORGANIZATION_ROLE ( 
	ADDRESS_LINE_1 character varying (2000),
	ADDRESS_LINE_2 character varying (2000),
	POSTAL_CODE character varying (2000),
	CITY character varying (2000),
	STATE_OR_PROVINCE character varying (2000),
	COUNTRY character varying (2000),
	CTEP_ID character varying (2000),
	NAME character varying (255),
	ROLE_NAME character varying (30),
	ORGANIZATION_PO_ID integer,
	ROLE_PO_ID integer,
	STATUS character varying (20),
	STATUS_DATE date,
	ROLE_TYPE character varying (255),
	EMAIL character varying (2000),
	FAX character varying (2000),
	PHONE character varying (2000),
	TTY character varying (2000),
	IDENTIFIED_ORG_EXTENSION character varying 
	);

ALTER TABLE stg_dw_organization_role
  ADD PRIMARY KEY (role_po_id, role_name);
  
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_ORGANIZATION_ROLE ( 
    RUN_ID TIMESTAMP,
    ADDRESS_LINE_1 character varying (2000),
    ADDRESS_LINE_2 character varying (2000),
    POSTAL_CODE character varying (2000),
    CITY character varying (2000),
    STATE_OR_PROVINCE character varying (2000),
    COUNTRY character varying (2000),
    CTEP_ID character varying (2000),
    NAME character varying (255),
    ROLE_NAME character varying (30),
    ORGANIZATION_PO_ID integer,
    ROLE_PO_ID integer,
    STATUS character varying (20),
    STATUS_DATE date,
    ROLE_TYPE character varying (255),
    EMAIL character varying (2000),
    FAX character varying (2000),
    PHONE character varying (2000),
    TTY character varying (2000),
    IDENTIFIED_ORG_EXTENSION character varying 
    ); 
  