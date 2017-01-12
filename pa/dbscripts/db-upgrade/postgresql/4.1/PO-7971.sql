ALTER TABLE INTERVENTION DROP CONSTRAINT IF EXISTS INTERVENTION_UNIQUE;
ALTER TABLE INTERVENTION ALTER COLUMN PDQ_TERM_IDENTIFIER DROP NOT NULL;

ALTER TABLE PDQ_DISEASE DROP CONSTRAINT IF EXISTS DISEASE_UNIQUE;
ALTER TABLE PDQ_DISEASE ALTER COLUMN DISEASE_CODE DROP NOT NULL;