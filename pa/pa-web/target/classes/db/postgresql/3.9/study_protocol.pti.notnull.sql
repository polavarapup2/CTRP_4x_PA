UPDATE study_protocol
  SET proprietary_trial_indicator = false
  WHERE proprietary_trial_indicator IS NULL;
ALTER TABLE study_protocol
   ALTER COLUMN proprietary_trial_indicator SET DEFAULT false;
ALTER TABLE study_protocol
   ALTER COLUMN proprietary_trial_indicator SET NOT NULL;