
delete from pa_properties where name in (
'trial.ownership.add.email.subject',
'trial.ownership.add.email.body',
'trial.ownership.remove.email.subject',
'trial.ownership.remove.email.body');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.ownership.add.email.subject', 
'NCI Clinical Trials Reporting Program (CTRP): Added as Trial Record Owner, ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.ownership.add.email.body', 
'Date: ${CurrentDate}

Dear ${name},

The Clinical Trials Reporting Office (CTRO) has added you as an owner of the following trial record in the Clinical Trials Reporting Program (CTRP):

     Title: ${trialTitle}
     NCI Trial Identifier: ${nciTrialIdentifier}
     Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}

As an owner of this trial record, you have the ability to update or amend the trial in the CTRP Clinical Trials Registration application. If you do not want to be listed as one of the owners of this trial record in CTRP, or if you have questions about this or other CTRP topics, please contact the CTRO at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program.');


insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.ownership.remove.email.subject', 
'NCI Clinical Trials Reporting Program (CTRP): Removed as Trial Record Owner, ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.ownership.remove.email.body', 
'Date: ${CurrentDate}

Dear ${name},

The Clinical Trials Reporting Office (CTRO) has cancelled your ownership of the following trial record in the Clinical Trials Reporting Program (CTRP):

     Title: ${trialTitle}
     NCI Trial Identifier: ${nciTrialIdentifier}
     Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}

If you believe this to be an error, or if you have additional questions about this or other CTRP topics, please contact the CTRO at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program.');