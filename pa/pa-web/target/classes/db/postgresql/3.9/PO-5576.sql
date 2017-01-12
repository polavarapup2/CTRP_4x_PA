CREATE TABLE study_protocol_association
(
   identifier serial NOT NULL,
   study_a_id int8  NOT NULL,
   study_b_id int8,
   study_identifier varchar(64),
   identifier_type varchar(32),
   study_protocol_type varchar(32),
   STUDY_SUBTYPE_CODE varchar(64),
   official_title varchar(4000),
   date_last_created timestamp,
   date_last_updated timestamp,
   user_last_created_id int,
   user_last_updated_id int,
   PRIMARY KEY (identifier),
   UNIQUE (study_a_id, study_b_id),
   UNIQUE (study_a_id, study_identifier, identifier_type)
) WITH (OIDS=FALSE);

ALTER TABLE study_protocol_association ADD CONSTRAINT spa_sp_a FOREIGN KEY (study_a_id) 
    REFERENCES study_protocol (identifier) ON DELETE CASCADE;
    
ALTER TABLE study_protocol_association ADD CONSTRAINT spa_sp_b FOREIGN KEY (study_b_id) 
    REFERENCES study_protocol (identifier) ON DELETE SET NULL;    

