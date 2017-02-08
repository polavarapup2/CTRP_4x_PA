CREATE OR REPLACE VIEW rv_trial_id_nct AS
SELECT ss.study_protocol_identifier, ss.local_sp_indentifier
FROM study_site ss
JOIN research_organization ro ON (ro.identifier = ss.research_organization_identifier)
JOIN organization org ON (org.identifier = ro.organization_identifier)
WHERE org.name = 'ClinicalTrials.gov' and ss.functional_code='IDENTIFIER_ASSIGNER';
