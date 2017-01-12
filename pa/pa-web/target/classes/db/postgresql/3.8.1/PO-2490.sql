ALTER TABLE study_checkout ADD COLUMN checkout_date timestamp;
ALTER TABLE study_checkout ADD COLUMN checkin_date timestamp;
ALTER TABLE study_checkout ADD COLUMN checkin_comment character varying(200);
ALTER TABLE study_checkout ADD COLUMN checkin_user_identifier character varying(200);
UPDATE study_checkout SET checkout_date = date_last_created;
ALTER TABLE study_checkout ALTER COLUMN checkout_date SET NOT NULL;
ALTER TABLE study_checkout ALTER COLUMN user_identifier SET NOT NULL;