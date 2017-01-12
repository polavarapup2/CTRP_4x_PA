CREATE TABLE study_accrual_access
(
   identifier serial NOT NULL,
   study_protocol_identifier bigint NOT NULL,
   registry_user_id bigint NOT NULL,
   comments varchar(32768),
   status_code varchar(10) NOT NULL,   
   status_date_range_low TIMESTAMP NOT NULL,
   action_code varchar(16) NOT NULL,
   date_last_created timestamp,
   date_last_updated timestamp,
   user_last_created_id int,
   user_last_updated_id int,
   PRIMARY KEY (identifier)
) WITH (OIDS=FALSE);


ALTER TABLE study_accrual_access
    ADD CONSTRAINT fk_saa_registry_user
    FOREIGN KEY (registry_user_id)
    REFERENCES registry_user(identifier) ON DELETE CASCADE;


ALTER TABLE study_accrual_access
    ADD CONSTRAINT fk_saa_created_csm_user
    FOREIGN KEY (user_last_created_id)
    REFERENCES csm_user(user_id) ON DELETE SET NULL;
    
    
ALTER TABLE study_accrual_access
    ADD CONSTRAINT fk_saa_updated_csm_user
    FOREIGN KEY (user_last_updated_id)
    REFERENCES csm_user(user_id) ON DELETE SET NULL;
    
ALTER TABLE study_accrual_access
	ADD CONSTRAINT fk_saa_study_protocol
	FOREIGN KEY (study_protocol_identifier)
	REFERENCES study_protocol(identifier) ON DELETE CASCADE;
	
CREATE INDEX study_accrual_access_study_site_idx ON study_accrual_access(study_protocol_identifier);