ALTER TABLE study_subject ADD COLUMN registration_group_id CHARACTER VARYING(200);
ALTER TABLE study_subject DROP COLUMN outcomes_login_name;