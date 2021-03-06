ALTER TABLE batch_file ADD COLUMN date_last_created TIMESTAMP;
ALTER TABLE batch_file ADD COLUMN user_last_created_id INTEGER;
ALTER TABLE batch_file ADD COLUMN date_last_updated TIMESTAMP;
ALTER TABLE batch_file ADD COLUMN user_last_updated_id INTEGER;

ALTER TABLE batch_file ADD CONSTRAINT BATCH_FILE_USER_LAST_CREATED_FK FOREIGN KEY (user_last_created_id) REFERENCES CSM_USER;
ALTER TABLE batch_file ADD CONSTRAINT BATCH_FILE_USER_LAST_UPDATED_FK FOREIGN KEY (user_last_updated_id) REFERENCES CSM_USER;