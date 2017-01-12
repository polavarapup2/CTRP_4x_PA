CREATE OR REPLACE FUNCTION migrate_trial_phase() RETURNS void AS 
$$
DECLARE
row_data study_protocol%ROWTYPE;
v_phase_code varchar;
v_na varchar;
BEGIN
v_phase_code := E'PILOT';
v_na := E'NA';
-- Iterate through the results of a query.
FOR row_data IN SELECT sp.* FROM study_protocol sp WHERE sp.phase_code IN(E'PILOT',E'OTHER')LOOP
  -- if trial has phase code pilot then update phase_additional_qualifier_code with Pilot and phase as NA
  IF row_data.phase_code = v_phase_code THEN
        UPDATE study_protocol SET phase_additional_qualifier_code=v_phase_code where identifier = row_data.identifier;
  END IF;
          UPDATE study_protocol SET phase_code=v_na where identifier = row_data.identifier;
END LOOP;
END;
$$ LANGUAGE plpgsql;

-- Execute the function with appropriate arguments
select migrate_trial_phase();  