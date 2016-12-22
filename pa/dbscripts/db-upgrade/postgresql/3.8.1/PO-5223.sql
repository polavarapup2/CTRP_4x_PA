delete from pa_properties where name in ('trial.register.mismatchedUser.email.subject', 
    'trial.register.mismatchedUser.email.body');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.register.mismatchedUser.email.subject', 
'Unable to assign trial ownership');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.register.mismatchedUser.email.body', 
'Dear Sir or Madam,

A new trial {0} has been registered by {1} with the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP) system. You were identified as an owner of the trial. However, your email address {2} does not belong to a registered CTRP user and your trial ownership could not be assigned.

Please contact {1} to resolve this problem.

Thank you,

NCI Clinical Trials Reporting Program');