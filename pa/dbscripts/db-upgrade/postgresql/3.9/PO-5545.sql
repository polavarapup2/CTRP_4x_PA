insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.onhold.email.body', 
'Date: ${CurrentDate}

Dear ${SubmitterName},

We would like to inform you that the processing of your trial, identified below, has been placed on hold as of ${HoldDate} for the following reason: ${HoldReason}.

Please contact us at your earliest convenience to ensure timely processing of your trial. If we do not hear from you by close of business on ${Deadline}, we regret to inform you that we will no longer be able to process this trial.

NCI Trial Identifier: ${nciTrialIdentifier}
Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}
Title: ${trialTitle}
Submission Date: ${receiptDate}

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).');
