
    
-- roles to add to query to be displayed in users dropdown    
 delete from pa_properties where name='pa.ccct.ctro.roles.list';
 
 insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
'pa.ccct.ctro.roles.list','''AdminAbstractor'',''ScientificAbstractor'',''SuAbstractor'',''ResultsAbstractor''');

