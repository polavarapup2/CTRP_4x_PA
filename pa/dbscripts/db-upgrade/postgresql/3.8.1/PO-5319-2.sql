-- accrual.changeCode.body
update pa_properties set value = 'Date: ${CurrentDate}
NCI Trial Identifier: ${nciTrialIdentifier}

Dear ${SubmitterName},

Thank you for submitting your accruals to the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP). You entered the Change Code \"2\", indicating that the file did not require processing. The batch file has been stored for future reference.

The following file was received and saved successfully:
File Name: ${fileName}

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for registering your accruals data with NCI Clinical Trials Reporting Program (CTRP).'
where name = 'accrual.changeCode.body';