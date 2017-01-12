alter table study_protocol add expected_abstraction_completion_date timestamp;
alter table study_protocol add expected_abstraction_completion_comments varchar(1000);

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
    ts.submitter_org_id AS submiting_org_id,
    hold.onhold_reason_category,
    sp.expected_abstraction_completion_date,
    sp.expected_abstraction_completion_comments
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
