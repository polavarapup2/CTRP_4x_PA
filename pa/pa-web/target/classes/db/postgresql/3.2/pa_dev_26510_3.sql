--DROP TABLE TEMP_STUDY_INDIDE ;

CREATE TABLE TEMP_STUDY_INDIDE (
    IDENTIFIER SERIAL NOT NULL,
    EXPANDED_ACCESS_STATUS_CODE CHARACTER VARYING(200),
    EXPANDED_ACCESS_INDICATOR BOOLEAN,
    GRANTOR_CODE CHARACTER VARYING(200),
    NIH_INST_HOLDER_CODE CHARACTER VARYING(200),
    NCI_DIV_PROG_HOLDER_CODE CHARACTER VARYING(200),
    HOLDER_TYPE_CODE CHARACTER VARYING(200),
    INDIDE_NUMBER CHARACTER VARYING(200),
    INDIDE_TYPE_CODE CHARACTER VARYING(200),
    DATE_LAST_CREATED TIMESTAMP WITHOUT TIME ZONE,
    USER_LAST_CREATED CHARACTER VARYING(200),
    DATE_LAST_UPDATED TIMESTAMP WITHOUT TIME ZONE,
    USER_LAST_UPDATED CHARACTER VARYING(200),
    TEMP_STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL 
    REFERENCES TEMP_STUDY_PROTOCOL (IDENTIFIER) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE CASCADE);