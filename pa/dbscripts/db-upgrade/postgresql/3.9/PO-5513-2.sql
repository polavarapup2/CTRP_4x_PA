    
CREATE UNIQUE INDEX secondary_purpose_name ON secondary_purpose  USING btree ("name");

update secondary_purpose set name='Treatment' where identifier=1;
update secondary_purpose set name='Prevention' where identifier=2;

INSERT INTO secondary_purpose (name) VALUES ('Supportive Care');
INSERT INTO secondary_purpose (name) VALUES ('Screening');
INSERT INTO secondary_purpose (name) VALUES ('Diagnostic');
INSERT INTO secondary_purpose (name) VALUES ('Health Services Research');
INSERT INTO secondary_purpose (name) VALUES ('Basic Science');
INSERT INTO secondary_purpose (name) VALUES ('Other');

alter table study_protocol drop column secondary_purpose_id;

CREATE TABLE study_protocol_sec_purpose
(
   identifier serial NOT NULL,
   secondary_purpose_id int8 NOT NULL,
   study_protocol_id int8 NOT NULL,
   PRIMARY KEY (identifier)
) WITH (OIDS=FALSE);

ALTER TABLE study_protocol_sec_purpose ADD CONSTRAINT study_protocol_sec_purpose_secondary_purpose_id FOREIGN KEY (secondary_purpose_id) 
    REFERENCES secondary_purpose (identifier) ON DELETE CASCADE;
ALTER TABLE study_protocol_sec_purpose ADD CONSTRAINT study_protocol_sec_purpose_study_id FOREIGN KEY (study_protocol_id) 
    REFERENCES study_protocol (identifier) ON DELETE CASCADE;   
    
alter table study_protocol_stage drop column secondary_purpose_id;    
alter table study_protocol_stage add column secondary_purposes varchar(512);



