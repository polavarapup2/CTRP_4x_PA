ALTER TABLE study_protocol ADD COLUMN nci_grant boolean;
ALTER TABLE study_resourcing ADD COLUMN funding_percent double precision;

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'GrantsRequiredRegServiceEffectiveDate', '31-DEC-2050');
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'GrantsRequiredBatchRegEffectiveDate', '31-DEC-2050');









