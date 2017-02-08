CREATE OR REPLACE VIEW rv_search_results AS
SELECT sp.identifier                    study_protocol_identifier
      ,sp.official_title           
      ,sp.proprietary_trial_indicator
      ,sp.record_verification_date
      ,sp.date_last_created
      ,sp.submission_number
      ,sp.ctgov_xml_required_indicator
      ,upd.updating
      ,nciid.extension                  nci_number
      ,nctid.local_sp_indentifier       nct_number
      ,lo.assigned_identifier           lead_org_poid
      ,lo.name                          lead_org_name
      ,lo.local_sp_indentifier          lead_org_sp_identifier
      ,dwf.status_code                  current_dwf_status_code
      ,dwf.status_date_range_low        current_dwf_status_date
      ,sos.status_code                  current_study_overall_status
      ,ams.milestone_code               current_admin_milestone
      ,sms.milestone_code               current_scientific_milestone
      ,oms.milestone_code               current_other_milestone
      ,coa.identifier                   admin_checkout_identifier
      ,coa.user_identifier              admin_checkout_user
      ,cos.identifier                   scientific_checkout_identifier
      ,cos.user_identifier              scientific_checkout_user
      ,spi.first_name                   study_pi_first_name
      ,spi.last_name                    study_pi_last_name
      ,usr.login_name                   user_last_created_login
      ,usr.first_name                   user_last_created_first
      ,usr.last_name                    user_last_created_last
      ,dcp.local_sp_indentifier         dcp_id
      ,ctep.local_sp_indentifier        ctep_id
      ,sp.amendment_date 				amendment_date
      ,sp.date_last_updated				date_last_updated
FROM study_protocol sp
LEFT OUTER JOIN rv_updating_trials upd ON (sp.identifier = upd.study_protocol_identifier)
LEFT OUTER JOIN rv_trial_id_nci nciid ON (sp.identifier = nciid.study_protocol_id)
LEFT OUTER JOIN rv_trial_id_nct nctid ON (sp.identifier = nctid.study_protocol_identifier)
LEFT OUTER JOIN rv_lead_organization lo ON (sp.identifier = lo.study_protocol_identifier) 
LEFT OUTER JOIN rv_dwf_current dwf ON (sp.identifier = dwf.study_protocol_identifier) 
LEFT OUTER JOIN rv_sos_current sos ON (sp.identifier = sos.study_protocol_identifier)
LEFT OUTER JOIN rv_admin_milestone ams ON (sp.identifier = ams.study_protocol_identifier)
LEFT OUTER JOIN rv_scientific_milestone sms ON (sp.identifier = sms.study_protocol_identifier)
LEFT OUTER JOIN rv_other_milestone oms ON (sp.identifier = oms.study_protocol_identifier)
LEFT OUTER JOIN rv_checkout_admin coa ON (sp.identifier = coa.study_protocol_identifier)  
LEFT OUTER JOIN rv_checkout_admin cos ON (sp.identifier = cos.study_protocol_identifier)  
LEFT OUTER JOIN rv_study_principal_investigator spi ON (sp.identifier = spi.study_protocol_identifier)
LEFT OUTER JOIN csm_user usr ON (sp.user_last_created_id = usr.user_id)
LEFT OUTER JOIN rv_dcp_id dcp ON (sp.identifier = dcp.study_protocol_identifier)
LEFT OUTER JOIN rv_ctep_id ctep ON (sp.identifier = ctep.study_protocol_identifier);