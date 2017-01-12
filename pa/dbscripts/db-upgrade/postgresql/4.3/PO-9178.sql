insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties), 'cteprss.user',
    'cteprss');  