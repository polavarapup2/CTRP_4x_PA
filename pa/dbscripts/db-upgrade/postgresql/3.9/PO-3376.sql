alter table study_protocol add column comments varchar(4000);
alter table study_protocol add column processing_priority integer not null default 2;
alter table study_protocol alter column processing_priority drop not null;
alter table study_protocol add column assigned_user_id int;

ALTER TABLE study_protocol ADD CONSTRAINT fk_study_protocol_assigned_user_id FOREIGN KEY (assigned_user_id)
    REFERENCES csm_user(user_id) ON DELETE SET NULL;
    
    
CREATE OR REPLACE VIEW rv_trial_submitter AS   
    select sp.identifier as study_protocol_identifier, ru.identifier as submitter_id, ru.first_name as submitter_fname, ru.last_name as submitter_lname, o.name as submitter_org_name, o.assigned_identifier as submitter_org_id
        from registry_user ru inner join csm_user cu on cu.user_id=ru.csm_user_id inner join organization o on o.assigned_identifier=cast (ru.affiliated_org_id as varchar)
        inner join study_protocol sp on sp.user_last_created_id=cu.user_id; 
        
CREATE OR REPLACE VIEW rv_admin_milestone AS
SELECT study_protocol_identifier, milestone_code, milestone_date 
FROM study_milestone sm1
WHERE identifier IN (
  SELECT max(identifier)
  FROM study_milestone sm2
  WHERE sm1.study_protocol_identifier = sm2.study_protocol_identifier)
    AND milestone_code IN ('ADMINISTRATIVE_PROCESSING_START_DATE'
                ,'ADMINISTRATIVE_PROCESSING_COMPLETED_DATE'
                ,'ADMINISTRATIVE_READY_FOR_QC'
                ,'ADMINISTRATIVE_QC_START'
                ,'ADMINISTRATIVE_QC_COMPLETE');  
                
CREATE OR REPLACE VIEW rv_scientific_milestone AS
SELECT study_protocol_identifier, milestone_code, milestone_date  
FROM study_milestone sm1
WHERE identifier IN (
  SELECT max(identifier)
  FROM study_milestone sm2
  WHERE sm1.study_protocol_identifier = sm2.study_protocol_identifier)
    AND milestone_code IN ('SCIENTIFIC_PROCESSING_START_DATE'
                ,'SCIENTIFIC_PROCESSING_COMPLETED_DATE'
                ,'SCIENTIFIC_READY_FOR_QC' 
                ,'SCIENTIFIC_QC_START'
                ,'SCIENTIFIC_QC_COMPLETE');   
                
CREATE OR REPLACE VIEW rv_other_milestone AS
SELECT study_protocol_identifier, milestone_code, milestone_date   
FROM study_milestone sm1
WHERE identifier IN (
  SELECT max(identifier)
  FROM study_milestone sm2
  WHERE sm1.study_protocol_identifier = sm2.study_protocol_identifier)
    AND milestone_code NOT IN ('ADMINISTRATIVE_PROCESSING_START_DATE'
                ,'ADMINISTRATIVE_PROCESSING_COMPLETED_DATE'
                ,'ADMINISTRATIVE_READY_FOR_QC'
                ,'ADMINISTRATIVE_QC_START'
                ,'ADMINISTRATIVE_QC_COMPLETE'
                ,'SCIENTIFIC_PROCESSING_START_DATE'
                ,'SCIENTIFIC_PROCESSING_COMPLETED_DATE'
                ,'SCIENTIFIC_READY_FOR_QC' 
                ,'SCIENTIFIC_QC_START'
                ,'SCIENTIFIC_QC_COMPLETE');                
                
CREATE OR REPLACE VIEW rv_last_milestone AS
SELECT study_protocol_identifier, milestone_code, milestone_date   
FROM study_milestone sm1
WHERE identifier IN (
  SELECT max(identifier)
  FROM study_milestone sm2
  WHERE sm1.study_protocol_identifier = sm2.study_protocol_identifier);                
  
CREATE OR REPLACE VIEW rv_active_milestone AS
	SELECT study_protocol_identifier, milestone_code, milestone_date   
	FROM study_milestone sm1
	WHERE sm1.identifier IN (
	  SELECT max(identifier)
	  FROM study_milestone sm2
	  WHERE sm1.study_protocol_identifier = sm2.study_protocol_identifier
	    AND milestone_code NOT IN ('SUBMISSION_TERMINATED'
	                ,'SUBMISSION_REACTIVATED'));  

CREATE OR REPLACE VIEW rv_checkout_admin AS
 SELECT
    study_checkout.identifier,
    study_checkout.study_protocol_identifier,
    study_checkout.user_identifier,
    csm_user.first_name as csm_first_name,
    csm_user.last_name as csm_last_name,
    registry_user.first_name as reg_first_name,
    registry_user.last_name as reg_last_name,
    study_checkout.checkout_date
    FROM study_checkout LEFT JOIN csm_user ON study_checkout.user_identifier=csm_user.login_name LEFT JOIN registry_user ON registry_user.csm_user_id=csm_user.user_id
    WHERE   
       study_checkout.checkout_type = 'ADMINISTRATIVE'
       AND (study_checkout.checkin_date IS NULL);

CREATE OR REPLACE VIEW rv_checkout_scientific AS
 SELECT
    study_checkout.identifier,
    study_checkout.study_protocol_identifier,
    study_checkout.user_identifier,
    csm_user.first_name as csm_first_name,
    csm_user.last_name as csm_last_name,
    registry_user.first_name as reg_first_name,
    registry_user.last_name as reg_last_name,
    study_checkout.checkout_date
    FROM study_checkout LEFT JOIN csm_user ON study_checkout.user_identifier=csm_user.login_name LEFT JOIN registry_user ON registry_user.csm_user_id=csm_user.user_id
    WHERE   
       study_checkout.checkout_type = 'SCIENTIFIC'
       AND (study_checkout.checkin_date IS NULL);	                
	

CREATE OR REPLACE VIEW rv_last_hold AS
	SELECT study_protocol_identifier, onhold_reason_code, onhold_date, offhold_date   
	FROM study_onhold sh1
	WHERE sh1.identifier IN (
	  SELECT max(sh2.identifier)
	  FROM study_onhold sh2
	  WHERE sh1.study_protocol_identifier = sh2.study_protocol_identifier);  
       
       
CREATE OR REPLACE VIEW rv_trial_id_cdr AS
    SELECT study_otheridentifiers.study_protocol_id, study_otheridentifiers.extension
    FROM study_otheridentifiers
    WHERE substr(study_otheridentifiers.extension, 1, 3)='CDR';
	  
	  
CREATE OR REPLACE VIEW rv_search_results AS 
 SELECT sp.identifier AS study_protocol_identifier, 
        sp.official_title,
        sp.proprietary_trial_indicator,
        sp.record_verification_date,
        sp.date_last_created,
        sp.submission_number,
        sp.ctgov_xml_required_indicator,
        upd.updating,
        nciid.extension AS nci_number,
        nctid.local_sp_indentifier AS nct_number,
        lo.assigned_identifier AS lead_org_poid,
        lo.name AS lead_org_name,
        lo.local_sp_indentifier AS lead_org_sp_identifier,
        dwf.status_code AS current_dwf_status_code,
        dwf.status_date_range_low AS current_dwf_status_date,
        sos.status_code AS current_study_overall_status,
        ams.milestone_code AS current_admin_milestone,
        sms.milestone_code AS current_scientific_milestone,
        oms.milestone_code AS current_other_milestone,        
        coa.identifier AS admin_checkout_identifier,
        coa.user_identifier AS admin_checkout_user,
        cos.identifier AS scientific_checkout_identifier,
        cos.user_identifier AS scientific_checkout_user,
        spi.first_name AS study_pi_first_name,
        spi.last_name AS study_pi_last_name,
        usr.login_name AS user_last_created_login,
        usr.first_name AS user_last_created_first,
        usr.last_name AS user_last_created_last,
        dcp.local_sp_indentifier AS dcp_id,
        ctep.local_sp_indentifier AS ctep_id,
        sp.amendment_date,
        sp.date_last_updated,
        sp.phase_code,
        sp.primary_purpose_code,
        sp.start_date,
        sr.type_code AS summary4fundingsponsor_type,
        so.name AS sponsor_name,
        orp.name AS responsible_party_organization_name,
        pirp.first_name AS responsible_party_pi_first_name,
        pirp.last_name AS responsible_party_pi_last_name,
        usr.login_name AS user_last_updated_login,
        usr.first_name AS user_last_updated_first,
        usr.last_name AS user_last_updated_last,
        sp.pri_compl_date AS primary_completion_date,
        sp.study_protocol_type,
        sp.study_subtype_code,
        ts.submitter_org_name,
        ams.milestone_date  AS current_admin_milestone_date,
        sms.milestone_date  AS current_scientific_milestone_date,
        oms.milestone_date  AS current_other_milestone_date,
        sp.processing_priority,
        lms.milestone_code AS last_milestone,
        lms.milestone_date  AS last_milestone_date,
        actms.milestone_code AS active_milestone,
        actms.milestone_date  AS active_milestone_date,
        
        coa.csm_first_name AS admin_checkout_csm_fname,
        coa.csm_last_name AS admin_checkout_csm_lname,        
        coa.reg_first_name AS admin_checkout_reg_fname,
        coa.reg_last_name AS admin_checkout_reg_lname,
        
        cos.csm_first_name AS scientific_checkout_csm_fname,
        cos.csm_last_name AS scientific_checkout_csm_lname,        
        cos.reg_first_name AS scientific_checkout_reg_fname,
        cos.reg_last_name AS scientific_checkout_reg_lname,        

        hold.onhold_reason_code, 
        hold.onhold_date, 
        hold.offhold_date,
        
        cdr.extension as cdr_id,
        sp.amendment_number,
        coa.checkout_date AS admin_checkout_date,
        cos.checkout_date AS scientific_checkout_date,
        sp.comments
        
   FROM study_protocol sp
   LEFT JOIN rv_updating_trials upd ON sp.identifier = upd.study_protocol_identifier
   LEFT JOIN rv_trial_id_nci nciid ON sp.identifier = nciid.study_protocol_id
   LEFT JOIN rv_trial_id_nct nctid ON sp.identifier = nctid.study_protocol_identifier
   LEFT JOIN rv_lead_organization lo ON sp.identifier = lo.study_protocol_identifier
   LEFT JOIN rv_dwf_current dwf ON sp.identifier = dwf.study_protocol_identifier
   LEFT JOIN rv_sos_current sos ON sp.identifier = sos.study_protocol_identifier
   LEFT JOIN rv_admin_milestone ams ON sp.identifier = ams.study_protocol_identifier
   LEFT JOIN rv_scientific_milestone sms ON sp.identifier = sms.study_protocol_identifier
   LEFT JOIN rv_other_milestone oms ON sp.identifier = oms.study_protocol_identifier
   LEFT JOIN rv_last_milestone lms ON sp.identifier = lms.study_protocol_identifier
   LEFT JOIN rv_active_milestone actms ON sp.identifier = actms.study_protocol_identifier
   LEFT JOIN rv_checkout_admin coa ON sp.identifier = coa.study_protocol_identifier
   LEFT JOIN rv_checkout_scientific cos ON sp.identifier = cos.study_protocol_identifier
   LEFT JOIN rv_study_principal_investigator spi ON sp.identifier = spi.study_protocol_identifier
   LEFT JOIN csm_user usr ON sp.user_last_created_id = usr.user_id
   LEFT JOIN rv_dcp_id dcp ON sp.identifier = dcp.study_protocol_identifier
   LEFT JOIN rv_ctep_id ctep ON sp.identifier = ctep.study_protocol_identifier
   LEFT JOIN rv_study_resourcing sr ON sp.identifier = sr.study_protocol_identifier
   LEFT JOIN rv_sponsor_organization so ON sp.identifier = so.study_protocol_identifier
   LEFT JOIN rv_organization_responsible_party orp ON sp.identifier = orp.study_protocol_identifier
   LEFT JOIN rv_principal_investigator_responsible_party pirp ON sp.identifier = pirp.study_protocol_identifier
   LEFT JOIN rv_trial_submitter ts ON sp.identifier = ts.study_protocol_identifier
   LEFT JOIN rv_last_hold hold ON sp.identifier = hold.study_protocol_identifier
   LEFT JOIN rv_trial_id_cdr cdr ON sp.identifier = cdr.study_protocol_id;
