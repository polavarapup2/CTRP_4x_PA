update study_subject set assigned_identifier=upper(trim(assigned_identifier)) where status_code='ACTIVE' and assigned_identifier is not null;

CREATE OR REPLACE FUNCTION upper_case_assigned_identifier() RETURNS trigger AS $upper_case_assigned_identifier$
    BEGIN        
        NEW.assigned_identifier = upper(NEW.assigned_identifier);
        RETURN NEW;
    END;
$upper_case_assigned_identifier$ LANGUAGE plpgsql;

CREATE TRIGGER study_subject_assigned_identifier_trigger BEFORE INSERT OR UPDATE
    ON study_subject FOR EACH ROW
    WHEN (NEW.assigned_identifier is not null AND NEW.assigned_identifier<>upper(NEW.assigned_identifier))
    EXECUTE PROCEDURE upper_case_assigned_identifier();