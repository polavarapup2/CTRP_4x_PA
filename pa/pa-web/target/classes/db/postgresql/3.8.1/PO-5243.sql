-- trial.register.unidentifiableOwner.sub.email.body
update pa_properties
    set value = 'A new trial  has been submitted for registration with the CTRP system.  However, one or more of the trial record owners could not be assigned because their email addresses, as submitted, do not belong to registered CTRP user(s).

The unregistered email addresses are as follows:
{0}

Please contact a representative from the owner''s affiliated organization to identify the correct owner(s).
'
where name = 'trial.register.unidentifiableOwner.sub.email.body';

-- trial.register.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting your trial to the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP). You are receiving this message because you have successfully completed the registration of the following trial:
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}
* Lead Organization: ${leadOrgName}
* Title: ${trialTitle}.
 
Your trial has been assigned a unique NCI identification number for tracking that you will need to reference in future correspondence with the Clinical Trials Reporting Office (CTRO): 
* NCI Trial Identifier: ${nciTrialIdentifier}

${errors}
CTRO staff will consider your trial for acceptance in the CTRP system. You will receive an acceptance or rejection email with further information within two (2) business days.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).'
where name = 'trial.register.body';


-- trial.register.unidentifiableOwner.email.body
update pa_properties
set value = 'Dear CTRO staff,
 
A new trial {0} has been submitted by {1} for registration with CTRP system. However, one or more of the trial record owners could not be assigned because their email addresses, as submitted by {1}, do not belong to registered CTRP user(s).

The unregistered email address is as follows:
{2}

Please contact {1} or a representative from the owner''s affiliated organization to identify the correct owner.

Thank you,

NCI Clinical Trials Reporting Program'
where name = 'trial.register.unidentifiableOwner.email.body';

