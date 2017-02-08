insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.register.unidentifiableOwner.email.subject', 
'New {0} Trial {1} Registration with Errors/Warnings');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.register.unidentifiableOwner.email.body', 
'Dear CTRO,

A new trial {0} has been registered by {1} with the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP) system. However, one or more of the trial record owners could not be assigned because their emails, as submitted by {1}, do not belong to registered CTRP user(s).

Please see below:

{2}

Please contact {1} or a representative from the owner institution to identify the correct owner.

Thank you,

NCI Clinical Trials Reporting Program
');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.register.unidentifiableOwner.sub.email.body', 
'
Your trial has been registered successfully. However, the system was not able to properly assign trial record ownership. Some, or all, email address(es) provided do not belong to registered CTRP user(s):

{0}
 
');
