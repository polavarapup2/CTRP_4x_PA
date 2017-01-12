CREATE TABLE org_family_program_code
(
   identifier serial NOT NULL,
   org_family_po_id varchar NOT NULL,
   program_code varchar NOT NULL,
   program_name varchar NOT NULL,
   date_last_created timestamp,
   user_last_created_id integer,
   date_last_updated timestamp,
   user_last_updated_id integer,
   PRIMARY KEY (identifier)
) WITH (OIDS=FALSE);

ALTER TABLE org_family_program_code
    ADD CONSTRAINT fk_ofpc_created_csm_user
    FOREIGN KEY (user_last_created_id)
    REFERENCES csm_user(user_id) ON DELETE SET NULL;
    
    
ALTER TABLE org_family_program_code
    ADD CONSTRAINT fk_ofpc_updated_csm_user
    FOREIGN KEY (user_last_updated_id)
    REFERENCES csm_user(user_id) ON DELETE SET NULL;
    
CREATE INDEX org_family_program_code_fampoid_idx ON org_family_program_code(org_family_po_id);