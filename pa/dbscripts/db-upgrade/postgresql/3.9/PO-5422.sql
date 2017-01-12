ALTER TABLE study_outcome_measure ADD COLUMN type_code character varying(200);

update study_outcome_measure
  set type_code = case
                  when primary_indicator = TRUE then 'PRIMARY'
                  else 'SECONDARY'
                 end;