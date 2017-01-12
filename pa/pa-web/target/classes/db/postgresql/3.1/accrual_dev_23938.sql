DELETE FROM CSM_GROUP WHERE GROUP_NAME = 'Outcomes';
INSERT INTO CSM_GROUP (GROUP_NAME, GROUP_DESC, APPLICATION_ID, UPDATE_DATE) VALUES ('Outcomes', 'Outcome Submitter group - security role', (select application_id from csm_application where application_name = 'pa'),current_date)
;
