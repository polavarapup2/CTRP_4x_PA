CREATE OR REPLACE VIEW rv_trial_id_nci AS
SELECT study_protocol_id, extension 
FROM study_otheridentifiers
WHERE root = '2.16.840.1.113883.3.26.4.3';

CREATE OR REPLACE VIEW rv_trial_id_nct AS
SELECT ss.study_protocol_identifier, ss.local_sp_indentifier
FROM study_site ss
JOIN research_organization ro ON (ro.identifier = ss.research_organization_identifier)
JOIN organization org ON (org.identifier = ro.organization_identifier)
WHERE org.name = 'ClinicalTrials.gov';

CREATE OR REPLACE VIEW rv_lead_organization AS
SELECT ss.study_protocol_identifier, org.assigned_identifier, org.name, ss.local_sp_indentifier
FROM study_site ss
JOIN research_organization ro ON (ro.identifier = ss.research_organization_identifier)
JOIN organization org ON (org.identifier = ro.organization_identifier)
WHERE functional_code = 'LEAD_ORGANIZATION';

CREATE OR REPLACE VIEW rv_study_principal_investigator AS
SELECT sc.study_protocol_identifier,per.first_name,per.last_name
FROM study_contact sc
JOIN clinical_research_staff crs ON (crs.identifier = sc.clinical_research_staff_identifier)
JOIN person per ON (crs.person_identifier = per.identifier)
WHERE role_code = 'STUDY_PRINCIPAL_INVESTIGATOR';

CREATE OR REPLACE VIEW rv_dwf_current AS
SELECT status_code,status_date_range_low,study_protocol_identifier
FROM DOCUMENT_WORKFLOW_STATUS dwf1
WHERE identifier in (
  select max(identifier) 
  from DOCUMENT_WORKFLOW_STATUS dwf2 
  where dwf1.study_protocol_identifier = dwf2.study_protocol_identifier);
  
CREATE OR REPLACE VIEW rv_sos_current AS
SELECT status_code,study_protocol_identifier
FROM STUDY_OVERALL_STATUS sos1
WHERE identifier in (
  select max(identifier) 
  from STUDY_OVERALL_STATUS sos2 
  where sos1.study_protocol_identifier = sos2.study_protocol_identifier);  

CREATE OR REPLACE VIEW rv_admin_milestone AS
SELECT study_protocol_identifier, milestone_code 
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
SELECT study_protocol_identifier, milestone_code 
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
SELECT study_protocol_identifier, milestone_code 
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
  
CREATE OR REPLACE VIEW rv_checkout_admin AS
SELECT identifier,study_protocol_identifier,user_identifier 
FROM STUDY_CHECKOUT
WHERE CHECKOUT_TYPE = 'ADMINISTRATIVE';
  
CREATE OR REPLACE VIEW rv_checkout_scientific AS
SELECT identifier,study_protocol_identifier,user_identifier 
FROM STUDY_CHECKOUT
WHERE CHECKOUT_TYPE = 'SCIENTIFIC';

CREATE OR REPLACE VIEW rv_updating_trials AS
SELECT DISTINCT study_protocol_identifier, true updating 
FROM study_inbox
WHERE close_date IS NULL;

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
