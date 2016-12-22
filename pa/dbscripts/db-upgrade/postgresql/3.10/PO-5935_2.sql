--PO-5935 : Adding a property to enable or disable CTGOV sync nightly job
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'ctgov.sync.enabled','false');