delete from PA_PROPERTIES where name ='ctgov.sync.schedule';
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties), 'ctgov.sync.schedule','0 0 2 ? * MON-FRI');   
