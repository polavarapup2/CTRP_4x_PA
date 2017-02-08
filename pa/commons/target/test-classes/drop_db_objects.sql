drop sequence AUDIT_ID_SEQ;
drop table if exists dual_AUDIT_ID_SEQ;
drop table if exists prs_sync_history;
DROP SEQUENCE nci_identifiers_seq;

drop view rv_active_milestone IF EXISTS CASCADE;
drop view rv_admin_milestone IF EXISTS CASCADE;
drop view rv_biomarker_existence IF EXISTS CASCADE;
drop view rv_ccr_id  IF EXISTS CASCADE;
drop view rv_checkout_admin  IF EXISTS CASCADE;
drop view rv_checkout_scientific IF EXISTS CASCADE;
drop view rv_ctep_id  IF EXISTS CASCADE;	
drop view rv_dcp_id  IF EXISTS CASCADE;	
drop view rv_dwf_current  IF EXISTS CASCADE;		
drop view rv_dwf_previous  IF EXISTS CASCADE;
drop view rv_last_hold IF EXISTS CASCADE;
drop view rv_last_milestone  IF EXISTS CASCADE;
drop view rv_lead_organization IF EXISTS CASCADE;
drop view rv_organization_responsible_party  IF EXISTS CASCADE;
drop view rv_other_milestone  IF EXISTS CASCADE;
drop view rv_principal_investigator_responsible_party  IF EXISTS CASCADE;
drop view rv_scientific_milestone  IF EXISTS CASCADE;
drop view rv_sos_current  IF EXISTS CASCADE;
drop view rv_sponsor_organization IF EXISTS CASCADE;
drop view rv_study_principal_investigator  IF EXISTS CASCADE;
drop view rv_study_resourcing  IF EXISTS CASCADE;
drop view rv_trial_id_cdr  IF EXISTS CASCADE;
drop view rv_trial_id_nci  IF EXISTS CASCADE;
drop view rv_trial_id_nct IF EXISTS CASCADE;
drop view rv_trial_submitter  IF EXISTS CASCADE;
drop view rv_updating_trials  IF EXISTS CASCADE;
drop view rv_search_results  IF EXISTS CASCADE;

DROP TRIGGER study_milestone_set_active_trigger_insert;    
DROP TRIGGER study_milestone_set_active_trigger_update;
DROP TRIGGER study_milestone_set_active_trigger_delete;

DROP TRIGGER study_milestone_set_admin_trigger_insert;    
DROP TRIGGER study_milestone_set_admin_trigger_update;
DROP TRIGGER study_milestone_set_admin_trigger_delete;

DROP TRIGGER study_milestone_set_last_trigger_insert;    
DROP TRIGGER study_milestone_set_last_trigger_update;
DROP TRIGGER study_milestone_set_last_trigger_delete;

DROP TRIGGER study_milestone_set_other_trigger_insert;    
DROP TRIGGER study_milestone_set_other_trigger_update;
DROP TRIGGER study_milestone_set_other_trigger_delete;

DROP TRIGGER study_milestone_set_scientific_trigger_insert;    
DROP TRIGGER study_milestone_set_scientific_trigger_update;
DROP TRIGGER study_milestone_set_scientific_trigger_delete;

DROP TRIGGER study_overall_status_set_current_trigger_insert;    
DROP TRIGGER study_overall_status_set_current_trigger_update;
DROP TRIGGER study_overall_status_set_current_trigger_delete;

DROP TRIGGER document_workflow_status_set_current_trigger_insert;    
DROP TRIGGER document_workflow_status_set_current_trigger_update;
DROP TRIGGER document_workflow_status_set_current_trigger_delete;

DROP TRIGGER document_workflow_status_set_previous_trigger_insert;    
DROP TRIGGER document_workflow_status_set_previous_trigger_update;
DROP TRIGGER document_workflow_status_set_previous_trigger_delete;

DROP TRIGGER sync_trial_nci_id_trigger_insert;    
DROP TRIGGER sync_trial_nci_id_trigger_update;
DROP TRIGGER sync_trial_nci_id_trigger_delete;

DROP TRIGGER sync_trial_identifier_assigner_ids_trigger_insert;    
DROP TRIGGER sync_trial_identifier_assigner_ids_triggerr_update;
DROP TRIGGER sync_trial_identifier_assigner_ids_trigger_delete;


