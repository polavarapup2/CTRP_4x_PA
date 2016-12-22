DROP TABLE IF EXISTS study_subject;
DROP TABLE IF EXISTS patient;
DROP TABLE IF EXISTS performed_activity;
DROP TABLE IF EXISTS submission;
DROP TABLE IF EXISTS study_site_accrual_access;

-- Table: patient
CREATE TABLE patient (
    identifier SERIAL NOT NULL,
    assigned_identifier VARCHAR(200),
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
    PRIMARY KEY (identifier)
)WITH (OIDS=FALSE);


-- Table: study_subject
CREATE TABLE study_subject (
    identifier SERIAL NOT NULL,
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
    PRIMARY KEY (identifier)
)WITH (OIDS=FALSE);

ALTER TABLE study_subject ADD CONSTRAINT fk_study_subject_patient
FOREIGN KEY (patient_identifier) REFERENCES patient (identifier)
ON DELETE RESTRICT;

ALTER TABLE study_subject ADD CONSTRAINT fk_study_subject_study_protocol
FOREIGN KEY (study_protocol_identifier) REFERENCES study_protocol (identifier)
ON DELETE CASCADE;

ALTER TABLE study_subject ADD CONSTRAINT fk_study_subject_study_site
FOREIGN KEY (study_site_identifier) REFERENCES study_site (identifier)
ON DELETE SET NULL;

ALTER TABLE study_subject ADD CONSTRAINT fk_study_subject_disease
FOREIGN KEY (disease_identifier) REFERENCES disease (identifier)
ON DELETE SET NULL;


-- Table:  performed_activity
CREATE TABLE performed_activity (
    identifier SERIAL NOT NULL,
    category_code VARCHAR(200),
    subcategory_code VARCHAR(200),
    text_description VARCHAR(2000),
    actual_date_range_low TIMESTAMP,
    actual_date_range_high TIMESTAMP,
    informed_consent_date TIMESTAMP,
    study_protocol_identifier BIGINT NOT NULL,
    performed_activity_type VARCHAR(100),
    date_last_created TIMESTAMP,
    user_last_created VARCHAR(200),
    date_last_updated TIMESTAMP,
    user_last_updated VARCHAR(200),
    PRIMARY KEY (identifier)
)WITH (OIDS=FALSE);

ALTER TABLE performed_activity ADD CONSTRAINT fk_performed_activity_study_protocol
FOREIGN KEY (study_protocol_identifier) REFERENCES study_protocol (identifier)
ON DELETE CASCADE;


-- Table:  submission
CREATE TABLE submission (
    identifier SERIAL NOT NULL,
    label VARCHAR(200),
    description VARCHAR(400),
    cut_off_date TIMESTAMP,
    status_code VARCHAR(200),
    status_date_range_low TIMESTAMP,
    status_date_range_high TIMESTAMP,
    study_protocol_identifier BIGINT NOT NULL,
    date_last_created TIMESTAMP,
    user_last_created VARCHAR(200),
    date_last_updated TIMESTAMP,
    user_last_updated VARCHAR(200),
    PRIMARY KEY (identifier)
)WITH (OIDS=FALSE);

ALTER TABLE submission ADD CONSTRAINT fk_submission_study_protocol
FOREIGN KEY (study_protocol_identifier) REFERENCES study_protocol (identifier)
ON DELETE CASCADE;


--Table:  study_site_accrual_access
CREATE TABLE study_site_accrual_access (
    identifier SERIAL NOT NULL,
    csm_user_id BIGINT NOT NULL,
    study_site_identifier BIGINT NOT NULL,
    request_details VARCHAR(10000),
    status_code VARCHAR(200) NOT NULL,
    status_date_range_low TIMESTAMP NOT NULL,
    date_last_created TIMESTAMP,
    user_last_created VARCHAR(200),
    date_last_updated TIMESTAMP,
    user_last_updated VARCHAR(200),
    PRIMARY KEY (identifier)
)WITH (OIDS=FALSE);

ALTER TABLE study_site_accrual_access ADD CONSTRAINT fk_study_site_accrual_access_study_site
FOREIGN KEY (study_site_identifier) REFERENCES study_site (identifier)
ON DELETE CASCADE;

ALTER TABLE study_site_accrual_access ADD CONSTRAINT study_site_accrual_access_unique 
UNIQUE (csm_user_id, study_site_identifier);

