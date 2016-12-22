--Author   : Anubhav Das
--Date     : 04/08/2014        
--Jira#    : PO-6967 
--Comments : Updates the login name from 'ctgovimport' to 'ClinicalTrials.gov Import'. Updates last submitter to 'ClinicalTrials.gov Import' for trials which have a corresponding entry in ctgovimport_log.

update csm_user set login_name = 'ClinicalTrials.gov Import' where login_name = 'ctgovimport';

update study_protocol set user_last_created_id=(select csm_user_id from registry_user where csm_user_id IN 
(select user_id from csm_user where login_name = 'ClinicalTrials.gov Import')) 
where study_protocol.identifier IN (select a.identifier from study_protocol a, study_otheridentifiers b 
where a.identifier = b.study_protocol_id and a.status_code = 'ACTIVE' and 
b.root = '2.16.840.1.113883.3.26.4.3' and b.extension IN (SELECT distinct(d.nci_id) from ctgovimport_log d where d.action_performed = 'Update' and d.import_status = 'Success'));