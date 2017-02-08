ALTER TABLE study_protocol
  ADD COLUMN accrual_disease_code_system text NOT NULL DEFAULT 'SDC';

ALTER TABLE study_protocol_stage
  ADD COLUMN accrual_disease_code_system text NOT NULL DEFAULT 'SDC';

ALTER TABLE accrual_disease
  DROP CONSTRAINT accrual_disease_disease_code_key;

ALTER TABLE accrual_disease
  ADD CONSTRAINT accrual_disease_system_code_uq UNIQUE (code_system, disease_code);

UPDATE study_protocol sp
  SET accrual_disease_code_system = 
        ( SELECT MAX(ad.code_system)
          FROM study_subject ssub
          JOIN accrual_disease ad ON (ssub.disease_identifier = ad.identifier)
          WHERE ssub.study_protocol_identifier = sp.identifier )
  WHERE sp.identifier IN 
        ( SELECT ssub.study_protocol_identifier
          FROM study_subject ssub
          JOIN accrual_disease ad ON (ssub.disease_identifier = ad.identifier));