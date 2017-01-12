delete from PA_PROPERTIES where name='dashboard.counts.milestones';
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'dashboard.counts.milestones',
    'Submission Received Date,Submission Acceptance Date,Administrative Processing Start Date,Ready for Administrative QC Date,Administrative QC Start Date,Scientific Processing Start Date,Ready for Scientific QC Date,Scientific QC Start Date,Ready for Trial Summary Report Date');
  