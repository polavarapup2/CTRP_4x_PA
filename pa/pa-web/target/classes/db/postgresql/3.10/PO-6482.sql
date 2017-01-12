DELETE FROM pa_properties where name='ctgov.sync.skip_po_data';

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 
    'ctgov.sync.import_orgs', 
    'true');
    
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 
    'ctgov.sync.import_persons', 
    'false');