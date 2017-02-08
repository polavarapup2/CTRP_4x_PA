alter table study_contact ADD COLUMN organizational_contact_identifier bigint;
alter table study_contact alter COLUMN clinical_research_staff_identifier DROP NOT NULL;