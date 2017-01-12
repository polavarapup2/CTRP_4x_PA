--PO-8949 Implement On-Hold Feature Enhancements (On-hold Information screen in PA)
-- change size of ONHOLD_REASON_TEXT and add new column ONHOLD_REASON_CATEGORY


--drop view other it will not let column length change
-- cannot alter type of a column used by a view or rule
drop VIEW rv_search_results;
drop VIEW rv_last_hold;


ALTER TABLE study_onhold ALTER COLUMN ONHOLD_REASON_TEXT TYPE varchar(4000);
ALTER TABLE study_onhold ADD COLUMN ONHOLD_REASON_CATEGORY varchar(4000);

--recreate the views back again


CREATE OR REPLACE VIEW rv_last_hold AS
	SELECT study_protocol_identifier, onhold_reason_code, onhold_date, offhold_date, onhold_reason_text   
	FROM study_onhold sh1
	WHERE sh1.identifier IN (
	  SELECT max(sh2.identifier)
	  FROM study_onhold sh2
	  WHERE sh1.study_protocol_identifier = sh2.study_protocol_identifier);  
	  
CREATE OR REPLACE VIEW rv_search_results AS 
 SELECT sp.identifier AS study_protocol_identifier, sp.official_title, 
    sp.proprietary_trial_indicator, sp.record_verification_date, 
    sp.date_last_created, sp.submission_number, sp.ctgov_xml_required_indicator, 
    upd.updating, nciid.extension AS nci_number, 
    nctid.local_sp_indentifier AS nct_number, 
    lo.assigned_identifier AS lead_org_poid, lo.name AS lead_org_name, 
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
    spi.first_name AS study_pi_first_name, spi.last_name AS study_pi_last_name, 
    usr.login_name AS user_last_created_login, 
    usr.first_name AS user_last_created_first, 
    usr.last_name AS user_last_created_last, dcp.local_sp_indentifier AS dcp_id, 
    ctep.local_sp_indentifier AS ctep_id, sp.amendment_date, 
    sp.date_last_updated, sp.phase_code, sp.primary_purpose_code, sp.start_date, 
    sr.type_code AS summary4fundingsponsor_type, so.name AS sponsor_name, 
    orp.name AS responsible_party_organization_name, 
    pirp.first_name AS responsible_party_pi_first_name, 
    pirp.last_name AS responsible_party_pi_last_name, 
    usr.login_name AS user_last_updated_login, 
    usr.first_name AS user_last_updated_first, 
    usr.last_name AS user_last_updated_last, 
    sp.pri_compl_date AS primary_completion_date, sp.study_protocol_type, 
    sp.study_subtype_code, ts.submitter_org_name, 
    ams.milestone_date AS current_admin_milestone_date, 
    sms.milestone_date AS current_scientific_milestone_date, 
    oms.milestone_date AS current_other_milestone_date, sp.processing_priority, 
    lms.milestone_code AS last_milestone, 
    lms.milestone_date AS last_milestone_date, 
    actms.milestone_code AS active_milestone, 
    actms.milestone_date AS active_milestone_date, 
    coa.csm_first_name AS admin_checkout_csm_fname, 
    coa.csm_last_name AS admin_checkout_csm_lname, 
    coa.reg_first_name AS admin_checkout_reg_fname, 
    coa.reg_last_name AS admin_checkout_reg_lname, 
    cos.csm_first_name AS scientific_checkout_csm_fname, 
    cos.csm_last_name AS scientific_checkout_csm_lname, 
    cos.reg_first_name AS scientific_checkout_reg_fname, 
    cos.reg_last_name AS scientific_checkout_reg_lname, hold.onhold_reason_code, 
    hold.onhold_date, hold.offhold_date, cdr.extension AS cdr_id, 
    sp.amendment_number, coa.checkout_date AS admin_checkout_date, 
    cos.checkout_date AS scientific_checkout_date, sp.comments, 
    hold.onhold_reason_text, false AS planned_marker_existence_indicator, 
    sp.study_source, ccr.local_sp_indentifier AS ccr_id, 
    sp.accrual_disease_code_system, 
    prev_dwf.status_code AS previous_dwf_status_code, 
    ts.submitter_org_id AS submiting_org_id
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
   LEFT JOIN rv_trial_id_cdr cdr ON sp.identifier = cdr.study_protocol_id
   LEFT JOIN rv_ccr_id ccr ON sp.identifier = ccr.study_protocol_identifier
   LEFT JOIN rv_dwf_previous prev_dwf ON sp.identifier = prev_dwf.study_protocol_identifier;	  
	  


-- Add default value for newly added column 
update  study_onhold set ONHOLD_REASON_CATEGORY='Submitter' 
where ONHOLD_REASON_CODE in ('SUBMISSION_INCOM','SUBMISSION_INCOM_MISSING_DOCS','INVALID_GRANT');

update  study_onhold set ONHOLD_REASON_CATEGORY='CTRP' 
where ONHOLD_REASON_CODE in ('PENDING_CTRP_REVIEW','PENDING_DISEASE_CUR','PENDING_PERSON_CUR','PENDING_ORG_CUR','PENDING_INTERVENTION_CUR','OTHER');

--Add new row in pa_properties to set all possible values for ONHOLD_REASON_CATEGORY column
delete from PA_PROPERTIES where name='studyonhold.reason_category';
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
'studyonhold.reason_category','Submitter,CTRP');

--Add new row in pa_properties to set mapping for ONHOLD_REASON_CATEGORY column
delete from PA_PROPERTIES where name='studyonhold.reason_category.mapping';
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
'studyonhold.reason_category.mapping',
E'SUBMISSION_INCOM=Submitter\nSUBMISSION_INCOM_MISSING_DOCS=Submitter\nINVALID_GRANT=Submitter\nPENDING_CTRP_REVIEW=CTRP\nPENDING_DISEASE_CUR=CTRP\nPENDING_PERSON_CUR=CTRP\nPENDING_ORG_CUR=CTRP\nPENDING_INTERVENTION_CUR=CTRP\nOTHER=CTRP');


