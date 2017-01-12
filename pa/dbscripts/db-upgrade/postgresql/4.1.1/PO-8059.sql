INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 
    'ctgov.ftp.schedule',  '0 0 0 ? * MON-FRI');