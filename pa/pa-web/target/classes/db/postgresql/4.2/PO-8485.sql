INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 
    'CADSR_URL',  'http://cadsrapi.nci.nih.gov/cadsrapi40');