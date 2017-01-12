UPDATE study_subject SET status_code = 'ACTIVE' where status_code = 'PENDING';
UPDATE patient SET status_code = 'ACTIVE' where status_code = 'PENDING';