ALTER TABLE STUDY_PROTOCOL RENAME COLUMN PROPRIETARYTRIALINDICATOR TO PROPRIETARY_TRIAL_INDICATOR ;

UPDATE STUDY_PROTOCOL SET PROPRIETARY_TRIAL_INDICATOR = NULL WHERE PROPRIETARY_TRIAL_INDICATOR = FALSE;