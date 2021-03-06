--Author   : Anubhav Das
--Date     : 04/16/2014        
--Jira#    : PO-6887 - PA: Enhance search for Updates of, and Acknowledged Updates for, trials from ClinicalTrials.gov 


update ctgovimport_log a set study_inbox_id = 
	( select c.identifier 
		from study_otheridentifiers b, study_inbox c
		where c.comments like '%Trial has been updated from ClinicalTrials.gov%' 
		and a.nci_id = b.extension and b.root = '2.16.840.1.113883.3.26.4.3'
		and b.study_protocol_id = c.study_protocol_identifier
		and a.date_created > c.date_last_created and (a.date_created - c.date_last_created) <= interval '1000 ms'
	) where a.action_performed = 'Update' and a.import_status='Success' and a.study_inbox_id is null;