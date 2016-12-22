INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 
    'smtp.port',  '25');