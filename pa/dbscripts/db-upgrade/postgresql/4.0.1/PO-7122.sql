INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'AccrualBlankSiteName', 'Unidentified Site');
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'AccrualBlankSiteRejectDate', '01-MAR-2014');
