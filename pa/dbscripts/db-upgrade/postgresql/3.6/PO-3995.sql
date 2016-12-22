CREATE TABLE BATCH_FILE (
    identifier SERIAL NOT NULL,
    registry_user_id BIGINT NOT NULL,
    passed_validation BOOL NOT NULL,
    is_processed BOOL NOT NULL,
    file_location VARCHAR(1000) NOT NULL,
    PRIMARY KEY (identifier)
);

alter table BATCH_FILE add constraint BATCH_FILE_REGISTRY_USER_FK foreign key (registry_user_id) references REGISTRY_USER;
create index BATCH_FILE_REGISTRY_USER_IDX on BATCH_FILE(registry_user_id);