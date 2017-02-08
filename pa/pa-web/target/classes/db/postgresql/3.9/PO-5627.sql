ALTER TABLE study_accrual_access ADD COLUMN source character varying(32);
ALTER TABLE study_site_accrual_access ADD COLUMN source character varying(32);

UPDATE study_accrual_access SET source = 'REG_ADMIN_PROVIDED';
UPDATE study_site_accrual_access SET source = 'PA_SITE_REQUEST';

UPDATE study_site_accrual_access ssaa
SET source = 'REG_ADMIN_PROVIDED'
WHERE identifier IN
  ( SELECT ssaa.identifier
    FROM study_site ss
    JOIN study_accrual_access saa USING (study_protocol_identifier)
    JOIN study_site_accrual_access ssaa ON (ss.identifier =  ssaa.study_site_identifier)
    WHERE (saa.status_code = 'ACTIVE' AND ssaa.status_code = 'ACTIVE')
       OR (saa.status_code = 'INACTIVE' AND ssaa.status_code = 'INACTIVE'));

ALTER TABLE study_accrual_access
   ALTER COLUMN source SET NOT NULL;
ALTER TABLE study_site_accrual_access
   ALTER COLUMN source SET NOT NULL;
