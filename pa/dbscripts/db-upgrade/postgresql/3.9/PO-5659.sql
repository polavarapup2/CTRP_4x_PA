update pa_properties set value = 
'Dear Sir or Madam,

A new trial {0} has been registered by {1} with the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP) system. You were identified as an owner of the trial. However, your email address {2} does not belong to a registered CTRP user and your trial ownership could not be assigned.

Please contact NCI Clinical Trials Reporting Office to resolve this problem.

Thank you,

NCI Clinical Trials Reporting Program'

where name='trial.register.mismatchedUser.email.body';