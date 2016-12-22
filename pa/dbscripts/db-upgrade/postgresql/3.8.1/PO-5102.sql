-- accrual.error.subject
insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'accrual.error.subject', 
'NCI Clinical Trials Reporting Program (CTRP): Accrual Error Report - ${nciTrialIdentifier}');

-- accrual.error.body
insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'accrual.error.body',
'Date: ${CurrentDate}
NCI Trial Identifier: ${nciTrialIdentifier}

Dear ${SubmitterName},

Thank you for submitting your accruals to the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP).
However, the submission failed due to the following errors:

Errors in batch file: ${fileName}
 
${errors}

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for registering your accruals data with NCI Clinical Trials Reporting Program (CTRP).');

-- accrual.confirmation.subject
insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'accrual.confirmation.subject',
'NCI Clinical Trials Reporting Program (CTRP): Accrual Confirmation Report - ${nciTrialIdentifier}');

-- accrual.confirmation.body
insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'accrual.confirmation.body',
'Date: ${CurrentDate}
NCI Trial Identifier: ${nciTrialIdentifier}

Dear ${SubmitterName},

Thank you for submitting your accruals to the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP). A total of ${count} subjects were registered successfully. The following file was received and processed successfully:

File Name: ${fileName}

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for registering your accruals data with NCI Clinical Trials Reporting Program (CTRP).');