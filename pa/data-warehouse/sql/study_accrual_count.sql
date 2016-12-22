DROP TABLE IF EXISTS stg_dw_study_accrual_count;

CREATE TABLE stg_dw_study_accrual_count ( 
    accrual_count integer,
    count_type character varying(20),
    nci_id character varying(255),
    org_name character varying(200),
    org_org_family character varying(160),
    study_site_id integer
);