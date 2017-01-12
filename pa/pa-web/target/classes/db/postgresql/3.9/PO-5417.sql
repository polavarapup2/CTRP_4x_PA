alter table document add column original bool;
alter table document add column deleted bool;
alter table document add column study_inbox_id int8;
ALTER TABLE document ADD CONSTRAINT document_study_inbox_id FOREIGN KEY (study_inbox_id) REFERENCES study_inbox (identifier) 
    ON DELETE SET NULL;
    
alter table study_inbox add column type_code varchar(16) not null default 'UPDATE';
update study_inbox set type_code = 
    CASE WHEN (position('<b>Type :</b>' in comments) = 1 OR position('<table><tr><td>' in comments) = 1) THEN 'VALIDATION'
     ELSE 'UPDATE'
END;
	