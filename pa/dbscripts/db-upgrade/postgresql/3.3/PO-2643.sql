UPDATE study_protocol SET primary_purpose_additional_qualifier_code ='OTHER' where primary_purpose_code = 'OTHER' and primary_purpose_other_text is not null;

UPDATE study_protocol SET primary_purpose_additional_qualifier_code='EPIDEMIOLOGIC' where primary_purpose_code='EPIDEMIOLOGIC';
UPDATE study_protocol SET primary_purpose_code='OTHER' where primary_purpose_code='EPIDEMIOLOGIC';

UPDATE study_protocol SET primary_purpose_additional_qualifier_code='OUTCOME' where primary_purpose_code='OUTCOME';
UPDATE study_protocol SET primary_purpose_code='OTHER' where primary_purpose_code='OUTCOME';

UPDATE study_protocol SET primary_purpose_additional_qualifier_code='OBSERVATIONAL' where primary_purpose_code='OBSERVATIONAL';
UPDATE study_protocol SET primary_purpose_code='OTHER' where primary_purpose_code='OBSERVATIONAL';


UPDATE study_protocol SET primary_purpose_additional_qualifier_code='ANCILLARY' where primary_purpose_code='ANCILLARY';
UPDATE study_protocol SET primary_purpose_code='OTHER' where primary_purpose_code='ANCILLARY';


UPDATE study_protocol SET primary_purpose_additional_qualifier_code='CORRELATIVE' where primary_purpose_code='CORRELATIVE';
UPDATE study_protocol SET primary_purpose_code='OTHER' where primary_purpose_code='CORRELATIVE';
--
CREATE OR REPLACE FUNCTION migrate_trial_purpose_other() RETURNS void AS 
$$
DECLARE
row_data study_protocol%ROWTYPE;
v_other varchar;
BEGIN
v_other := E'OTHER';
-- Iterate through the results of a query.
FOR row_data IN SELECT sp.* FROM study_protocol sp WHERE sp.primary_purpose_code NOT IN 
('TREATMENT','PREVENTION','SUPPORTIVE_CARE','SCREENING','DIAGNOSTIC','HEALTH_SERVICES_RESEARCH','BASIC_SCIENCE','OTHER') LOOP

        UPDATE study_protocol SET primary_purpose_other_text=row_data.primary_purpose_code where identifier = row_data.identifier;
        UPDATE study_protocol SET primary_purpose_additional_qualifier_code=v_other where identifier = row_data.identifier;
        UPDATE study_protocol SET primary_purpose_code=v_other where identifier = row_data.identifier;
END LOOP;
END;
$$ LANGUAGE plpgsql;

select migrate_trial_purpose_other();  