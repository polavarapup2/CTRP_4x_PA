ALTER TABLE study_onhold ADD COLUMN processing_log varchar(4096);

delete from pa_properties where name in ('trial.onhold.reminder.frequency','trial.onhold.deadline','trial.onhold.reminder.reasons',
'trial.onhold.reminder.subject', 'trial.onhold.reminder.body','trial.onhold.reminder.logentry', 'trial.onhold.termination.subject', 
'trial.onhold.termination.body','trial.onhold.termination.comment', 'trial.onhold.termination.logentry');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.onhold.reminder.frequency', 
'3');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.onhold.deadline', 
'21');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.onhold.reminder.reasons', 
'SUBMISSION_INCOM,SUBMISSION_INCOM_MISSING_DOCS');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.onhold.reminder.subject', 
'NCI Clinical Trials Reporting Program (CTRP): The processing of trial ${leadOrgTrialIdentifier}, ${nciTrialIdentifier} is on Hold');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.onhold.reminder.body', 
'Date: ${CurrentDate}

Dear ${SubmitterName},

We would like to remind you that the processing of your trial, identified below, has been placed on hold as of ${HoldDate} for the following reason: ${HoldReason}.

Please contact us at your earliest convenience to ensure timely processing of your trial. If we do not hear from you by close of business on ${Deadline}, we regret to inform you that we will no longer be able to process this trial.

NCI Trial Identifier: ${nciTrialIdentifier}
Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}
Title: ${trialTitle}
Submission Date: ${receiptDate}

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.onhold.reminder.logentry', 
'${date} An automatic request for additional information was submitted to ${emails}.

');



insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.onhold.termination.subject', 
'Submission terminated for trial ${nciTrialIdentifier} due to lack of response');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.onhold.termination.body', 
'The system has performed the following actions on trial ${nciTrialIdentifier}, ${trialTitle}:
-- Submitted multiple requests for information to the submitter and trial record owner(s) with no response received within the allotted response period,
-- Closed the trial''s on-hold state as of today''s date,
-- Added a Submission Terminated Date milestone.');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.onhold.termination.logentry', 
'${date} The System has automatically closed this trial''s on-hold state due to lack of response from the submitter.

${date} The System has added a Submission Terminated Date milestone.

');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'trial.onhold.termination.comment', 
'Milestone was added automatically by the system due to lack of response from the submitter.');