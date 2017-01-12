INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'programcodes.reporting.default.end_date',
'12/31/2015');

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'programcodes.reporting.default.length',
'12');