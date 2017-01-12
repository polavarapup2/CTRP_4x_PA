ALTER TABLE registry_user ADD COLUMN family_accrual_submitter boolean DEFAULT false;
UPDATE registry_user SET family_accrual_submitter = false;
ALTER TABLE registry_user ALTER COLUMN family_accrual_submitter SET NOT NULL;
