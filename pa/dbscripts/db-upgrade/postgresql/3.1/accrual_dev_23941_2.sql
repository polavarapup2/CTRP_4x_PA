ALTER TABLE PERFORMED_ACTIVITY ADD COLUMN ACTUAL_DURATION_VALUE NUMERIC;
ALTER TABLE PERFORMED_ACTIVITY ADD COLUMN ACTUAL_DURATION_UNIT VARCHAR(200);
ALTER TABLE PERFORMED_ACTIVITY ADD COLUMN NAME VARCHAR(200);
ALTER TABLE PERFORMED_ACTIVITY ADD COLUMN NAME_CODE VARCHAR(200);
ALTER TABLE PERFORMED_ACTIVITY ADD COLUMN METHOD_CODE VARCHAR(200);
ALTER TABLE PERFORMED_ACTIVITY ADD COLUMN TARGET_SITE_CODE VARCHAR(200);
ALTER TABLE PERFORMED_ACTIVITY ADD COLUMN CONTRAST_AGENT_ENHANCEMENT_INDICATOR BOOLEAN;

ALTER TABLE PERFORMED_ACTIVITY  ADD COLUMN DOSE_VALUE  NUMERIC;
ALTER TABLE PERFORMED_ACTIVITY  ADD COLUMN DOSE_UNIT  VARCHAR(200);
ALTER TABLE PERFORMED_ACTIVITY  ADD COLUMN DOSE_DESCRIPTION  VARCHAR(200); 
ALTER TABLE PERFORMED_ACTIVITY  ADD COLUMN DOSE_FORM_CODE  VARCHAR(200); 
ALTER TABLE PERFORMED_ACTIVITY  ADD COLUMN DOSE_FREQUENCY_CODE  VARCHAR(200);
ALTER TABLE PERFORMED_ACTIVITY  ADD COLUMN DOSE_REGIMEN  VARCHAR(200);
ALTER TABLE PERFORMED_ACTIVITY  ADD COLUMN DOSE_TOTAL_VALUE  NUMERIC;
ALTER TABLE PERFORMED_ACTIVITY  ADD COLUMN DOSE_TOTAL_UNIT  VARCHAR(200);
ALTER TABLE PERFORMED_ACTIVITY  ADD COLUMN ROUTE_OF_ADMINISTRATION_CODE  VARCHAR(200); 
ALTER TABLE PERFORMED_ACTIVITY  ADD COLUMN DOSE_DURATION_VALUE  NUMERIC; 
ALTER TABLE PERFORMED_ACTIVITY  ADD COLUMN DOSE_DURATION_UNIT  VARCHAR(200);
ALTER TABLE PERFORMED_ACTIVITY  ADD COLUMN DOSE_MODIFICATION_TYPE VARCHAR(200);
ALTER TABLE PERFORMED_ACTIVITY  ADD COLUMN MACHINE_TYPE_CODE VARCHAR(200);

ALTER TABLE PERFORMED_ACTIVITY ADD CONSTRAINT FK_PERFORMED_ACTIVITY_DOSE_UNIT
FOREIGN KEY (DOSE_UNIT) REFERENCES UNIT_OF_MEASUREMENT (CODE) ON DELETE CASCADE;

ALTER TABLE PERFORMED_ACTIVITY ADD CONSTRAINT FK_PERFORMED_ACTIVITY_DOSE_TOTAL_UNIT
FOREIGN KEY (DOSE_TOTAL_UNIT) REFERENCES UNIT_OF_MEASUREMENT(CODE) ON DELETE CASCADE;

ALTER TABLE PERFORMED_ACTIVITY ADD CONSTRAINT FK_PERFORMED_ACTIVITY_DOSE_FORM_CODE
FOREIGN KEY (DOSE_FORM_CODE) REFERENCES DOSE_FORM (CODE) ON DELETE CASCADE;

ALTER TABLE PERFORMED_ACTIVITY ADD CONSTRAINT FK_PERFORMED_ACTIVITY_DOSE_FREQUENCY_CODE
FOREIGN KEY (DOSE_FREQUENCY_CODE) REFERENCES DOSE_FREQUENCY (CODE) ON DELETE CASCADE;

ALTER TABLE PERFORMED_ACTIVITY ADD CONSTRAINT FK_PERFORMED_ACTIVITY_ROUTE_OF_ADMINISTRATION_CODE
FOREIGN KEY (ROUTE_OF_ADMINISTRATION_CODE) REFERENCES ROUTE_OF_ADMINISTRATION (CODE) ON DELETE CASCADE;