CREATE OR REPLACE FUNCTION  update_study_participation() RETURNS void AS $$
DECLARE
rr RECORD;
orgName VARCHAR(200);
BEGIN
FOR rr IN SELECT * FROM study_participation where oversight_committee_identifier IS NOT NULL and functional_code != 'STUDY_OVERSIGHT_COMMITTEE' LOOP

select into orgName org.name from organization org where org.identifier in (select organization_identifier from oversight_committee where identifier = rr.oversight_committee_identifier); 

Insert into study_participation(identifier, functional_code ,local_sp_indentifier, 
 review_board_approval_number, review_board_approval_date, 
 review_board_approval_status_code,
 study_protocol_identifier, oversight_committee_identifier,
status_code, status_date_range_low, status_date_range_high,
date_last_created, user_last_created, date_last_updated,
user_last_updated, review_board_organizational_affiliation)
select  nextval('hibernate_sequence'),'STUDY_OVERSIGHT_COMMITTEE',rr.local_sp_indentifier, 
 rr.review_board_approval_number, rr.review_board_approval_date, 
 rr.review_board_approval_status_code,
 rr.study_protocol_identifier, rr.oversight_committee_identifier,
rr.status_code, rr.status_date_range_low, rr.status_date_range_high,
rr.date_last_created, rr.user_last_created, rr.date_last_updated,
rr.user_last_updated, orgName; 

update study_participation set oversight_committee_identifier = NULL, review_board_approval_number= NULL, review_board_approval_status_code = NULL
where functional_code != 'STUDY_OVERSIGHT_COMMITTEE' and study_participation.identifier = rr.identifier;


END LOOP;
END;
$$ LANGUAGE 'plpgsql';

select update_study_participation();

drop function  update_study_participation();