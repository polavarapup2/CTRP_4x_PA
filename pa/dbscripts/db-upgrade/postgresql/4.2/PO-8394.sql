ALTER TABLE study_protocol ADD COLUMN ctro_override_flag_comments varchar(1000);
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'ctep.ccr.learOrgIds',
'NCICCR');
