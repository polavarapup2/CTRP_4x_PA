ALTER TABLE study_subject ADD COLUMN outcomes_login_name character varying(200);

--DELETE FROM study_protocol where assigned_identifier = 'Outcomes';
--INSERT INTO study_protocol (identifier, assigned_identifier, submission_number, study_protocol_type) 
--    values (((SELECT MAX(identifier) FROM study_protocol) + 1), 'Outcomes', 1, 'StudyProtocol');
