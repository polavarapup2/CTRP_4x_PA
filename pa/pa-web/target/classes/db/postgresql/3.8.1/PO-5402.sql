-- accrual.industrialTrial.body
insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'accrual.industrialTrial.body',
'Date: ${CurrentDate}
NCI Trial Identifier: ${nciTrialIdentifier}

Dear ${SubmitterName},

Thank you for submitting your accruals to the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP). Accrual count on following Study Sites was updated successfully:

${studySiteCounts}
The following file was received and processed successfully:

File Name: ${fileName}

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for registering your accruals data with NCI Clinical Trials Reporting Program (CTRP).');