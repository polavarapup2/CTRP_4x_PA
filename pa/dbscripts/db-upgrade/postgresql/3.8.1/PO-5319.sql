-- accrual.changeCode.body
insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'accrual.changeCode.body',
'Date: ${CurrentDate}
NCI Trial Identifier: ${nciTrialIdentifier}

Dear ${SubmitterName},

Thank you for submitting your accruals to the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP). Change Code was set to ''2'' indicating that the file did not need to be processed. The batch file has been stored for future reference. The following file was received and saved successfully:

File Name: ${fileName}

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for registering your accruals data with NCI Clinical Trials Reporting Program (CTRP).');