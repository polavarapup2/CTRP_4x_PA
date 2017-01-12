INSERT INTO study_protocol (identifier, assigned_identifier, submission_number, study_protocol_type) 
    values (((SELECT MAX(identifier) FROM study_protocol) + 1), 'Outcomes', 1, 'StudyProtocol');
