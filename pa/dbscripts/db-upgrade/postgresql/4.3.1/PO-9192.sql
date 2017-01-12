drop table org_family_program_code;

CREATE TABLE family
(
  identifier serial NOT NULL,
  po_id bigint NOT NULL, 
  rep_period_end date NOT NULL,
  rep_period_len_months integer NOT NULL,
  CONSTRAINT family_pk PRIMARY KEY (identifier)
);

CREATE TABLE program_code
(
  identifier serial NOT NULL, 
  family_id bigint NOT NULL,
  program_code text NOT NULL,
  program_name text NOT NULL,  
  status_code text NOT NULL,
  CONSTRAINT program_code_pk PRIMARY KEY (identifier),
  CONSTRAINT fk_family_id FOREIGN KEY (family_id)
      REFERENCES family (identifier) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE UNIQUE INDEX unqiue_program_code_idx ON program_code (family_id, status_code, upper(program_code))
    WHERE status_code='ACTIVE' AND trim(program_code)<>'';


CREATE TABLE study_program_code ( 
  program_code_id bigint NOT NULL,
  study_protocol_id bigint NOT NULL,
  CONSTRAINT study_program_code_pk PRIMARY KEY (program_code_id, study_protocol_id),
  CONSTRAINT fk_program_code_id FOREIGN KEY (program_code_id)
      REFERENCES program_code (identifier) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_study_protocol_id FOREIGN KEY (study_protocol_id)
  REFERENCES study_protocol (identifier) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE CASCADE
);
