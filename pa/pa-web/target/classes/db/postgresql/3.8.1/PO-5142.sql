ALTER TABLE study_subject ADD COLUMN delete_reason CHARACTER VARYING(200);
insert into pa_properties values ((select max(identifier) + 1 from pa_properties),'subject.delete.reasons','Incorrect Study,');