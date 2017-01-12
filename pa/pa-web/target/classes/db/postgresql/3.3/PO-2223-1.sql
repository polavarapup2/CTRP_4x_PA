CREATE OR REPLACE FUNCTION migrate_trial_purpose(purpose varchar) RETURNS void AS 
$$
DECLARE
row_data study_protocol%ROWTYPE;
v_other varchar;
BEGIN
v_other := E'OTHER';
-- Iterate through the results of a query.
FOR row_data IN SELECT sp.* FROM study_protocol sp WHERE sp.primary_purpose_code IN (purpose) LOOP
	-- update primary_purpose_additional_qualifier_code with given code and purpose as OTHER
        UPDATE study_protocol SET primary_purpose_additional_qualifier_code=row_data.primary_purpose_code where identifier = row_data.identifier;
        UPDATE study_protocol SET primary_purpose_code=v_other where identifier = row_data.identifier;
END LOOP;
END;
$$ LANGUAGE plpgsql;

-- Execute the function with appropriate arguments
select migrate_trial_purpose('EPIDEMIOLOGIC');  
select migrate_trial_purpose('OUTCOME');
select migrate_trial_purpose('OBSERVATIONAL');
select migrate_trial_purpose('ANCILLARY');
select migrate_trial_purpose('CORRELATIVE');
