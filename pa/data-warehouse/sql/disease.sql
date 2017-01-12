DROP TABLE IF EXISTS STG_DW_STUDY_DISEASE;
CREATE TABLE STG_DW_STUDY_DISEASE ( 
    CT_GOV_XML_INDICATOR character varying(3),
    INCLUSION_INDICATOR character varying(5),
    DATE_LAST_CREATED timestamp,
    DATE_LAST_UPDATED timestamp,
    DISEASE_CODE character varying(200),
    DISEASE_PREFERRED_NAME character varying(1000),
    DISEASE_MENU_DISPLAY_NAME character varying(1000),
    INTERNAL_SYSTEM_ID INTEGER NOT NULL,    -- study_disease_identifier
    INTERNAL_SYSTEM_ID2 INTEGER NOT NULL,   -- disease_identifier
    LEAD_DISEASE_INDICATOR character varying(3),
    NCI_ID character varying(255),
    NCI_THESAURUS_CONCEPT_ID character varying(200),
    USER_LAST_CREATED character varying(500),
    USER_LAST_UPDATED character varying(500),
    PRIMARY KEY (INTERNAL_SYSTEM_ID, INTERNAL_SYSTEM_ID2, INCLUSION_INDICATOR)
);

DROP TABLE IF EXISTS STG_DW_DISEASE_PARENTS;
CREATE TABLE STG_DW_DISEASE_PARENTS ( 
    DISEASE_IDENTIFIER bigint NOT NULL,
    PARENT_DISEASE_IDENTIFIER bigint NOT NULL,
    DISEASE_CODE character varying(200),
    NT_TERM_IDENTIFIER character varying(200),
    PREFERRED_NAME character varying(1000),
    MENU_DISPLAY_NAME character varying(1000),
    PRIMARY KEY (DISEASE_IDENTIFIER, PARENT_DISEASE_IDENTIFIER)
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_STUDY_DISEASE ( 
 RUN_ID TIMESTAMP,
    CT_GOV_XML_INDICATOR character varying(3),
    INCLUSION_INDICATOR character varying(5),
    DATE_LAST_CREATED timestamp,
    DATE_LAST_UPDATED timestamp,
    DISEASE_CODE character varying(200),
    DISEASE_PREFERRED_NAME character varying(1000),
    DISEASE_MENU_DISPLAY_NAME character varying(1000),
    INTERNAL_SYSTEM_ID INTEGER NOT NULL,    -- study_disease_identifier
    INTERNAL_SYSTEM_ID2 INTEGER NOT NULL,   -- disease_identifier
    LEAD_DISEASE_INDICATOR character varying(3),
    NCI_ID character varying(255),
    NCI_THESAURUS_CONCEPT_ID character varying(200),
    USER_LAST_CREATED character varying(500),
    USER_LAST_UPDATED character varying(500)
   
);
CREATE TABLE IF NOT EXISTS HIST_DW_DISEASE_PARENTS ( 
 RUN_ID TIMESTAMP,
    DISEASE_IDENTIFIER bigint NOT NULL,
    PARENT_DISEASE_IDENTIFIER bigint NOT NULL,
    DISEASE_CODE character varying(200),
    NT_TERM_IDENTIFIER character varying(200),
    PREFERRED_NAME character varying(1000),
    MENU_DISPLAY_NAME character varying(1000)
);

