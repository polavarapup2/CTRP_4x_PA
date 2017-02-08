CREATE TABLE consortia_category
( 
   name varchar(64) NOT NULL,
   code varchar(64) NOT NULL,
   summary4_funding_codes VARCHAR(64),
   PRIMARY KEY (name)
) WITH (OIDS=FALSE);

INSERT INTO consortia_category (name,code,summary4_funding_codes) VALUES ('NATIONAL','National','Industrial;National');
INSERT INTO consortia_category (name,code,summary4_funding_codes) VALUES ('EXTERNALLY_PEER_REVIEWED','Externally Peer-Reviewed','Industrial;Externally Peer-Reviewed');


ALTER TABLE study_protocol ADD CONSTRAINT study_protocol_consortia_trial_category FOREIGN KEY (consortia_trial_category) 
    REFERENCES consortia_category (name) ON DELETE SET NULL ON UPDATE CASCADE;


