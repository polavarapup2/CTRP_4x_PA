ALTER TABLE STUDY_PROTOCOL ADD COLUMN PROPRIETARYTRIALINDICATOR BOOLEAN;
ALTER TABLE STUDY_PROTOCOL ALTER COLUMN PRI_COMPL_DATE DROP NOT NULL;
ALTER TABLE STUDY_PROTOCOL ALTER COLUMN PRI_COMPL_DATE_TYPE_CODE DROP NOT NULL;
ALTER TABLE STUDY_PROTOCOL ALTER COLUMN START_DATE DROP NOT NULL;
ALTER TABLE STUDY_PROTOCOL ALTER COLUMN START_DATE_TYPE_CODE DROP NOT NULL;