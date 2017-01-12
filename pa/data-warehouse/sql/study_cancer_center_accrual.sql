DROP TABLE IF EXISTS stg_dw_study_cancer_center_accrual; 

CREATE TABLE stg_dw_study_cancer_center_accrual
(
  internal_system_id bigint, 
  family_po_id bigint,
  nci_id CHARACTER VARYING(200),
  targeted_accrual INTEGER
);

--Create history table
CREATE TABLE IF NOT EXISTS hist_dw_study_cancer_center_accrual
(
  RUN_ID TIMESTAMP,
  internal_system_id bigint, 
  family_po_id bigint,
  nci_id CHARACTER VARYING(200),
  targeted_accrual INTEGER
);
