CREATE UNIQUE INDEX unqiue_study_subject_idx ON study_subject (study_site_identifier, status_code, upper(assigned_identifier))
	WHERE status_code='ACTIVE' AND assigned_identifier IS NOT NULL 
	   AND study_site_identifier IS NOT NULL AND trim(assigned_identifier)<>'';