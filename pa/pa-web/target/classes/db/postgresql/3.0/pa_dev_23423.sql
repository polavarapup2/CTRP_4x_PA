DROP TABLE IF EXISTS STUDY_CHECKOUT;

CREATE TABLE STUDY_CHECKOUT (
    IDENTIFIER SERIAL NOT NULL,
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL,    
    USER_IDENTIFIER VARCHAR(200),
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200),
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200),
    PRIMARY KEY (IDENTIFIER) 
);

ALTER TABLE STUDY_CHECKOUT ADD CONSTRAINT FK_STUDY_CHECKOUT_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;
