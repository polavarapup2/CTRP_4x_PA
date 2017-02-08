CREATE OR REPLACE FUNCTION  populate_study_secondary_identifiers() RETURNS void AS $$
DECLARE
rr RECORD;
BEGIN
FOR rr IN SELECT * FROM study_protocol LOOP

Insert into STUDY_OTHERIDENTIFIERS(STUDY_PROTOCOL_ID, extension,root,identifier_name) values (rr.identifier,rr.assigned_identifier,'2.16.840.1.113883.3.26.4.3','NCI study protocol entity identifier') ; 

END LOOP;
END;
$$ LANGUAGE 'plpgsql';

select populate_study_secondary_identifiers();

drop function  populate_study_secondary_identifiers();

ALTER TABLE STUDY_PROTOCOL DROP COLUMN ASSIGNED_IDENTIFIER;
