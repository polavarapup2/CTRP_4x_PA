
-- Purpose: To enable storing multiple secondary identifiers.

-- create a table to map secondary identifiers to studies, and vice versa.

CREATE TABLE STUDY_OTHERIDENTIFIERS
(
  STUDY_PROTOCOL_ID BIGINT NOT NULL,
  null_flavor character varying(255),
  displayable boolean,
  extension character varying(255),
  identifier_name character varying(255),
  reliability character varying(255),
  root character varying(255),
  scope character varying(255),
  CONSTRAINT STUDY_OI_FK FOREIGN KEY (STUDY_PROTOCOL_ID)
      REFERENCES STUDY_PROTOCOL (IDENTIFIER) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) 
