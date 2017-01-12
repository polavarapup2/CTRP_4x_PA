CREATE OR REPLACE FUNCTION  update_doc_workflow_status() RETURNS void AS $$
DECLARE
rr RECORD;

BEGIN
--- change the user_last_created to the appropriate user for the tier before executing the script
FOR rr IN SELECT * FROM study_protocol where user_last_created ='sundareb@mail.nih.gov' LOOP

update study_contact 
	set 
		clinical_research_staff_identifier=(
					select clinical_research_staff_identifier
					from study_contact where study_protocol_identifier=rr.identifier and 
					role_code='STUDY_PRINCIPAL_INVESTIGATOR'),
		user_last_updated ='Script', 
		date_last_updated=now()
  where 
	study_protocol_identifier=rr.identifier 
	and role_code='RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR';

END LOOP;
END;
$$ LANGUAGE 'plpgsql';

select update_doc_workflow_status();

drop function update_doc_workflow_status()