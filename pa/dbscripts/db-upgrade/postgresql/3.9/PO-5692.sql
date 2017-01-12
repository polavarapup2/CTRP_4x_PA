CREATE TABLE primary_purpose
( 
   name varchar(64) NOT NULL,
   code varchar(64) NOT NULL,
   PRIMARY KEY (name)
) WITH (OIDS=FALSE);

INSERT INTO primary_purpose (name,code) VALUES ('TREATMENT','Treatment');
INSERT INTO primary_purpose (name,code) VALUES ('PREVENTION','Prevention');   
INSERT INTO primary_purpose (name,code) VALUES ('SUPPORTIVE_CARE','Supportive Care');   
INSERT INTO primary_purpose (name,code) VALUES ('SCREENING','Screening');   
INSERT INTO primary_purpose (name,code) VALUES ('DIAGNOSTIC','Diagnostic');   
INSERT INTO primary_purpose (name,code) VALUES ('HEALTH_SERVICES_RESEARCH','Health Services Research');  
INSERT INTO primary_purpose (name,code) VALUES ('BASIC_SCIENCE','Basic Science');   
INSERT INTO primary_purpose (name,code) VALUES ('OTHER','Other');

ALTER TABLE study_protocol ADD CONSTRAINT study_protocol_primary_purpose_code FOREIGN KEY (primary_purpose_code) 
    REFERENCES primary_purpose (name) ON DELETE SET NULL ON UPDATE CASCADE;


