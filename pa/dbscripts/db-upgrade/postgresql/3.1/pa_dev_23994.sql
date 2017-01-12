ALTER TABLE PLANNED_ACTIVITY  ADD COLUMN CDE_PUBLIC_IDENTIFIER  NUMERIC;
ALTER TABLE PLANNED_ACTIVITY  ADD COLUMN CDE_VERSION_NUMBER  VARCHAR(200);

insert into pa_properties(IDENTIFIER,name,value) VALUES(27,'CADSR_CS_ID','2960572');

insert into pa_properties(IDENTIFIER,name,value) VALUES(28,'CADSR_CS_VERSION','1');

insert into pa_properties(IDENTIFIER,name,value) VALUES(29,'CDE_REQUEST_TO_EMAIL','ncicbmb@mail.nih.gov');
insert into pa_properties(IDENTIFIER,name,value) VALUES(30,'CDE_REQUEST_TO_EMAIL_SUBJECT','Request to Create CDE');