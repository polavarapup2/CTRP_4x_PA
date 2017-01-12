CREATE TABLE STUDY_SITE_SUBJECT_ACCRUAL_COUNT
  (
    identifier SERIAL NOT NULL,
    study_protocol_identifier BIGINT NOT NULL,
    study_site_identifier BIGINT NOT NULL,
    accrual_count INTEGER,
    date_last_created TIMESTAMP,
    user_last_created_id INTEGER,
    date_last_updated TIMESTAMP,
    user_last_updated_id INTEGER,
    PRIMARY KEY (identifier)
);
alter table STUDY_SITE_SUBJECT_ACCRUAL_COUNT add constraint STUDY_SITE_SUBJECT_ACCRUAL_COUNT_STUDY_PROTOCOL_FK foreign key (study_protocol_identifier) references STUDY_PROTOCOL;
alter table STUDY_SITE_SUBJECT_ACCRUAL_COUNT add constraint STUDY_SITE_SUBJECT_ACCRUAL_COUNT_STUDY_SITE_FK foreign key (study_site_identifier) references STUDY_SITE;