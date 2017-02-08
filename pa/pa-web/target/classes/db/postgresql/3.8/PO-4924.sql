insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.onhold.startdate', 
'06/29/2012');
