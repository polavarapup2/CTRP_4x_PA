DROP TABLE IF EXISTS study_protocol_flags;

CREATE TABLE study_protocol_flags
(
   identifier serial NOT NULL,   
   study_protocol_id int8 NOT NULL,
   flag_reason varchar(64) NOT NULL,
   date_flagged timestamp NOT NULL,  
   flagging_user_id int8 NOT NULL,   
   comments text,   
   deleted boolean NOT NULL,
   date_deleted timestamp,
   deleting_user_id int8,
   delete_comments text,
   PRIMARY KEY (identifier)
) WITH (OIDS=FALSE);

ALTER TABLE study_protocol_flags ADD CONSTRAINT study_protocol_id FOREIGN KEY (study_protocol_id) 
    REFERENCES study_protocol (identifier) ON DELETE CASCADE;
    
ALTER TABLE study_protocol_flags ADD CONSTRAINT flagging_user_id FOREIGN KEY (flagging_user_id) 
    REFERENCES csm_user (user_id);
    
ALTER TABLE study_protocol_flags ADD CONSTRAINT  deleting_user_id FOREIGN KEY (deleting_user_id) 
    REFERENCES csm_user (user_id);        
    
CREATE UNIQUE INDEX idx01 ON study_protocol_flags (study_protocol_id, flag_reason) WHERE deleted=false;