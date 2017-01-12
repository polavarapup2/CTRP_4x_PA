
CREATE TABLE study_cancer_center_accrual
(
  identifier serial NOT NULL, 
  family_id bigint NOT NULL,
  study_protocol_id bigint NOT NULL,
  targeted_accrual integer NOT NULL,
  CONSTRAINT study_cancer_center_accrual_pk PRIMARY KEY (identifier),
  CONSTRAINT fk_family_id FOREIGN KEY (family_id)
      REFERENCES family (identifier) MATCH SIMPLE
      ON DELETE CASCADE,
  CONSTRAINT fk_study_protocol_id FOREIGN KEY (study_protocol_id)
      REFERENCES study_protocol (identifier) MATCH SIMPLE
      ON DELETE CASCADE
);
