DROP TABLE IF EXISTS STUDY_OBJECTIVE;

CREATE TABLE STUDY_OBJECTIVE (
    IDENTIFIER SERIAL NOT NULL,
    DESCRIPTION VARCHAR(2000) NOT NULL,
    TYPE_CODE VARCHAR(200) NOT NULL,
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200),
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200),
    PRIMARY KEY (IDENTIFIER) 
);

ALTER TABLE STUDY_OBJECTIVE ADD CONSTRAINT FK_STUDY_OBJECTIVE_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;