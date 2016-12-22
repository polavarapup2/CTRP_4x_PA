DROP TABLE IF EXISTS STG_DW_STUDY_BIOMARKER;
CREATE TABLE STG_DW_STUDY_BIOMARKER (
    ASSAY_PURPOSE character varying(200),
    ASSAY_PURPOSE_DESCRIPTION character varying(200),
    ASSAY_TYPE_CODE character varying(400),
    ASSAY_TYPE_DESCRIPTION character varying(200),
    ASSAY_USE character varying(200),
    INTERNAL_SYSTEM_ID INTEGER not null,
    LONG_NAME character varying(2000),
    NAME character varying(2000),
    NCI_ID character varying(255),
    nt_term_identifier varchar,
    STATUS_CODE character varying(200),
    TISSUE_COLLECTION_METHOD_CODE character varying(200),
    TISSUE_SPECIMEN_TYPE_CODE character varying(400),
    HUGO_BIOMARKER_CODE character varying(200),
    EVALUATION_TYPE_CODE character varying(400),
    EVALUATION_TYPE_OTHER_TEXT character varying(200),
    SPECIMEN_TYPE_OTHER_TEXT character varying(200),
    PRIMARY KEY(INTERNAL_SYSTEM_ID)
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_STUDY_BIOMARKER (
RUN_ID TIMESTAMP,
    ASSAY_PURPOSE character varying(200),
    ASSAY_PURPOSE_DESCRIPTION character varying(200),
    ASSAY_TYPE_CODE character varying(400),
    ASSAY_TYPE_DESCRIPTION character varying(200),
    ASSAY_USE character varying(200),
    INTERNAL_SYSTEM_ID INTEGER not null,
    LONG_NAME character varying(2000),
    NAME character varying(2000),
    NCI_ID character varying(255),
    STATUS_CODE character varying(200),
    TISSUE_COLLECTION_METHOD_CODE character varying(200),
    TISSUE_SPECIMEN_TYPE_CODE character varying(400),
    HUGO_BIOMARKER_CODE character varying(200),
    EVALUATION_TYPE_CODE character varying(400),
    EVALUATION_TYPE_OTHER_TEXT character varying(200),
    SPECIMEN_TYPE_OTHER_TEXT character varying(200)
);
