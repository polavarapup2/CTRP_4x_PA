ALTER TABLE patient ADD COLUMN birth_month_excluded boolean;
UPDATE patient SET birth_month_excluded = false;
ALTER TABLE patient ALTER COLUMN birth_month_excluded SET NOT NULL;