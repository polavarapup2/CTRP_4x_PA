delete from PA_PROPERTIES where name='dashboard.counts.trialdist';
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'dashboard.counts.trialdist',
    '0-3,4-7,8-10,>10');
  