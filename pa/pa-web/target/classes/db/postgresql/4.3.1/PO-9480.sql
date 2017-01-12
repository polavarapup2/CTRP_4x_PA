INSERT INTO csm_group (group_name,group_desc,update_date,application_id) VALUES ('ProgramCodeAdministrator', 'ProgramCode Administrator role', now(),
    (select application_id from csm_application where application_name='pa'));
