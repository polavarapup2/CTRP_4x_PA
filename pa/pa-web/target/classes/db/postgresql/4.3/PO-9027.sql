delete from PA_PROPERTIES where name='dashboard.counts.onholds';
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'dashboard.counts.onholds',
    'Submission Incomplete,Submission Incomplete -- Missing Documents,Invalid Grant,Pending CTRP Review,Pending Disease Curation,Pending Person Curation,Pending Organization Curation,Pending Intervention Curation,Other (CTRP),Other (Submitter)');
  