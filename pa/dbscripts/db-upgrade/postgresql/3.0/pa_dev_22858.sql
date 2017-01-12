ALTER TABLE STUDY_PARTICIPATION_CONTACT DROP CONSTRAINT  fk_study_participation_study_participation_contact  ;

ALTER TABLE STUDY_SITE_ACCRUAL_STATUS DROP CONSTRAINT fk_study_participation_study_site_accrual_status ;

ALTER TABLE STUDY_PARTICIPATION RENAME TO STUDY_SITE;

ALTER TABLE STUDY_PARTICIPATION_CONTACT RENAME TO STUDY_SITE_CONTACT;

ALTER TABLE STUDY_SITE_CONTACT RENAME  STUDY_PARTICIPATION_IDENTIFIER TO STUDY_SITE_IDENTIFIER ;


ALTER TABLE STUDY_SITE_ACCRUAL_STATUS RENAME  STUDY_PARTICIPATION_IDENTIFIER TO STUDY_SITE_IDENTIFIER ;


ALTER TABLE STUDY_SITE_ACCRUAL_STATUS ADD CONSTRAINT FK_STUDY_SITE_STUDY_SITE_ACCRUAL_STATUS
FOREIGN KEY (STUDY_SITE_IDENTIFIER) REFERENCES STUDY_SITE (IDENTIFIER)
ON DELETE CASCADE ;

ALTER TABLE STUDY_SITE_CONTACT ADD CONSTRAINT FK_STUDY_SITE_STUDY_SITE_CONTACT
FOREIGN KEY (STUDY_SITE_IDENTIFIER) REFERENCES STUDY_SITE (IDENTIFIER)
ON DELETE CASCADE ;