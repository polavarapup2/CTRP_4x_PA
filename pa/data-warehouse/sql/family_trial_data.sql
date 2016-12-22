DROP TABLE IF EXISTS stg_dw_family_trial_data; 
CREATE TABLE stg_dw_family_trial_data (
	family_name CHARACTER VARYING(200) NOT NULL,
	nci_id CHARACTER VARYING(255) NOT NULL,
	program_codes CHARACTER VARYING(4000)
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_family_trial_data (
 RUN_ID TIMESTAMP,
    family_name CHARACTER VARYING(200) NOT NULL,
    nci_id CHARACTER VARYING(255) NOT NULL,
    program_codes CHARACTER VARYING(4000)
);
