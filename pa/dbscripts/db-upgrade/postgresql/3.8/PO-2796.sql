DROP TRIGGER IF EXISTS "trgPatient" ON patient CASCADE;
DROP TRIGGER IF EXISTS "trgStudy_subject" ON study_subject CASCADE;
DROP TABLE IF EXISTS "patient_audit" CASCADE;
DROP TABLE IF EXISTS "study_subject_audit" CASCADE;
