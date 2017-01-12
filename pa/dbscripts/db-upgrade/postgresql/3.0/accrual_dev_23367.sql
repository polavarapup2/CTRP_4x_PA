DROP TABLE IF EXISTS study_subject_audit;
DROP TABLE IF EXISTS patient_audit;

-- Table: patient_audit 
CREATE TABLE patient_audit (
	audit_id SERIAL NOT NULL,
	audit_type VARCHAR(20),
	audit_user VARCHAR(200),
    patient_identifier VARCHAR(200),
    patient_assigned_identifier VARCHAR(200),
    person_assigned_identifier VARCHAR(200),
    race_code VARCHAR(200),
    sex_code VARCHAR(200),
    ethnic_code VARCHAR(200),
    birth_date TIMESTAMP,
    status_code VARCHAR(200) NOT NULL,
    status_date_range_low TIMESTAMP,
    date_last_created TIMESTAMP,
    user_last_create VARCHAR(200) ,
    date_last_updated TIMESTAMP,
    user_last_updated VARCHAR(200),
    PRIMARY KEY (audit_id)
)WITH (OIDS=FALSE);

-- Table: study_subject_audit
CREATE TABLE study_subject_audit ( 
	audit_id SERIAL NOT NULL, 
	audit_type VARCHAR(20),  
	audit_user VARCHAR(200), 
    study_subject_identifier VARCHAR(200), 
    patient_identifier BIGINT NOT NULL, 
    study_protocol_identifier BIGINT NOT NULL, 
    study_site_identifier BIGINT, 
    disease_identifier BIGINT, 
    payment_method_code VARCHAR(200) NOT NULL , 
    status_code VARCHAR(200), 
    status_date_range_low TIMESTAMP,
    status_date_range_high TIMESTAMP, 
    date_last_created TIMESTAMP, 
    user_last_create VARCHAR(200) , 
    date_last_updated TIMESTAMP, 
    user_last_updated VARCHAR(200) , 
    PRIMARY KEY (audit_id) 
)WITH (OIDS=FALSE); 

