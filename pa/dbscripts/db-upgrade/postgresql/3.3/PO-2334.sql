
-- Purpose: To enable storing multiple secondary identifiers in draft mode.

-- create a table to map secondary identifiers to studies, and vice versa..in draft mode.

CREATE TABLE STUDY_OTHERIDENTIFIERS_STAGE
(
  STUDY_PROTOCOL_ID BIGINT NOT NULL,
  null_flavor character varying(255),
  displayable boolean,
  extension character varying(255),
  identifier_name character varying(255),
  reliability character varying(255),
  root character varying(255),
  scope character varying(255),
  CONSTRAINT STUDY_OI__STAGE_FK FOREIGN KEY (STUDY_PROTOCOL_ID)
      REFERENCES STUDY_PROTOCOL_STAGE (IDENTIFIER) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
