CREATE OR REPLACE FUNCTION update_study_subject_trial() RETURNS VOID AS $$
DECLARE
    row_data RECORD;
    row_data2 RECORD;
    v_study_site_exists bigint;
    v_user_id bigint;
    v_status_code TEXT := '';
    v_updated boolean;
BEGIN

    SELECT INTO v_user_id user_id from csm_user where login_name = 'ejbclient';
    
    delete from study_subject where status_code != 'ACTIVE' and identifier in (
        select ssubj.identifier from study_subject ssubj
        join study_site ssite on (ssubj.study_site_identifier = ssite.identifier)
        where ssubj.study_protocol_identifier != ssite.study_protocol_identifier);
      
    FOR row_data IN select ssubj.identifier as subjectIdentifier, ssubj.study_protocol_identifier as subjectSpId, ssite.healthcare_facility_identifier as siteHCF, ssubj.assigned_identifier as subjAssignedIdentifier 
       from study_subject ssubj
       join study_site ssite on (ssubj.study_site_identifier = ssite.identifier)
       where ssubj.study_protocol_identifier != ssite.study_protocol_identifier
    LOOP
    
    select into v_study_site_exists identifier from study_site 
    where study_protocol_identifier = row_data.subjectSpId and healthcare_facility_identifier = row_data.siteHCF;

    IF v_study_site_exists IS NULL THEN
        -- delete all the subjects for that study which doesn't have access to the participating site
        delete from study_subject where identifier = row_data.subjectIdentifier;
        
    ELSE
		-- for that participating site if the study has access then get the results
        v_updated := false;
        FOR row_data2 IN select ssubj.*  from study_subject ssubj
            join study_site ssite on (ssubj.study_site_identifier = ssite.identifier)
            where ssubj.study_protocol_identifier = row_data.subjectSpId and ssubj.assigned_identifier = row_data.subjAssignedIdentifier and ssite.healthcare_facility_identifier = row_data.siteHCF
        LOOP
                v_status_code := row_data2.status_code;
                IF v_status_code != 'ACTIVE' THEN 
					-- delete all inactive subjects
                    delete from study_subject where identifier = row_data2.identifier;
                ELSIF  v_status_code = 'ACTIVE' and  v_study_site_exists = row_data2.study_site_identifier THEN                        
                    v_updated := true;
                END IF;     
        END LOOP;  
			IF v_updated = true AND v_study_site_exists != row_data2.study_site_identifier THEN
				-- delete the duplicate subjects
				delete from study_subject where identifier = row_data2.identifier;
			ELSIF  v_updated = false THEN
				-- update the corrupt data to correct study_site_id
                UPDATE study_subject SET study_site_identifier=v_study_site_exists, date_last_updated=now(), user_last_updated_id=v_user_id WHERE identifier = row_data.subjectIdentifier;
            END IF; 
    END IF;     
        
    END LOOP;
END;
$$ LANGUAGE 'plpgsql';

select update_study_subject_trial() as output;