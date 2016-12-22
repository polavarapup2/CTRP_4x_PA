-- trial.onhold.reminder.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

We would like to remind you that your trial record, identified below, has been  on hold as of  ${HoldDate} for the following reason: ${HoldReason}.
* NCI Trial Identifier: ${nciTrialIdentifier}
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}
* Title: ${trialTitle}
* Submission Date: ${receiptDate}

Important! Your trial will remain on hold until close of business on ${Deadline}. After that date, we will no longer be able to process this trial. 

Please contact us before this date to ensure timely processing of your trial. 

If you have questions on this or other Clinical Trials Reporting Program topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program.'
where name = 'trial.onhold.reminder.body';