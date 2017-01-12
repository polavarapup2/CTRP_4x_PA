CREATE FUNCTION updatePatient_audit() RETURNS TRIGGER AS $$
DECLARE
currentRecord RECORD;
BEGIN

FOR currentRecord IN SELECT * FROM patient where identifier = NEW.identifier LOOP

insert into patient_audit(audit_type, audit_user, patient_identifier, patient_assigned_identifier,person_assigned_identifier, 
	race_code, sex_code, ethnic_code, birth_date, status_code, status_date_range_low, date_last_created, user_last_created,
    date_last_updated, user_last_updated, country_identifier, zip) 
    VALUES ('Update', currentRecord.user_last_updated,  currentRecord.identifier, currentRecord.assigned_identifier,
    currentRecord.person_assigned_identifier, currentRecord.race_code, currentRecord.sex_code, currentRecord.ethnic_code, 
    currentRecord.birth_date, currentRecord.status_code, currentRecord.status_date_range_low,  currentRecord.date_last_created, 
    currentRecord.user_last_created, currentRecord.date_last_updated, currentRecord.user_last_updated,
    currentRecord.country_identifier, currentRecord.zip);
	
RETURN NEW;
END LOOP;
END;
$$ LANGUAGE 'plpgsql';
 
CREATE TRIGGER "trgPatient" BEFORE UPDATE
ON patient FOR EACH ROW
EXECUTE PROCEDURE updatePatient_audit();




CREATE FUNCTION updateStudy_subject_audit() RETURNS TRIGGER AS $$
DECLARE
currentRecord RECORD;
BEGIN

FOR currentRecord IN SELECT * FROM study_subject where identifier = NEW.identifier LOOP

insert into study_subject_audit(audit_type, audit_user, study_subject_identifier, patient_identifier, study_protocol_identifier,study_site_identifier, 
	disease_identifier, payment_method_code, status_code, status_date_range_low, status_date_range_high, date_last_created, user_last_created,
    date_last_updated, user_last_updated, assigned_identifier, healthcare_provider_identifier, clinical_research_staff_identifier) 
    VALUES ('Update', currentRecord.user_last_updated,  currentRecord.identifier,  currentRecord.patient_identifier,  
    currentRecord.study_protocol_identifier, currentRecord.study_site_identifier, currentRecord.disease_identifier,  currentRecord.payment_method_code, 
    currentRecord.status_code,  currentRecord.status_date_range_low,  currentRecord.status_date_range_high,  currentRecord.date_last_created, 
    currentRecord.user_last_created, currentRecord.date_last_updated,  currentRecord.user_last_updated, currentRecord.assigned_identifier,
    currentRecord.healthcare_provider_identifier, currentRecord.clinical_research_staff_identifier);
	
RETURN NEW;
END LOOP;
END;
$$ LANGUAGE 'plpgsql';
 
CREATE TRIGGER "trgStudy_subject" BEFORE UPDATE
ON study_subject FOR EACH ROW
EXECUTE PROCEDURE updateStudy_subject_audit();