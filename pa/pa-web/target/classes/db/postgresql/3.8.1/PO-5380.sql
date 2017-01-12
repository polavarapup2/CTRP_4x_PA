-- trial.batchUpload.reportMsg
update pa_properties
set value = '
Note: The number of submissions that failed can be misleading because it may include a number of empty lines in the file you submitted.

The progress report attached to this email contains the following information:
* Loading status for each trial record in your data file
* Record identifier captured from the first column in the data file 
* NCI trial identifier  for each trial successfully recorded
* Error message for each record that failed registration

Note: If the CTRP system cannot process your files, you will not receive a progress report, and the Summary of Submission Report will contain error messages. 
If you have received error messages, check your trial data file to ensure that you have used valid values. Validate the trial information using the current specification.

'
where name = 'trial.batchUpload.reportMsg';

-- trial.batchUpload.errorMsg
update pa_properties
set value = '
Note: If the CTRP system cannot process your files, you will not receive a progress report, and the Summary of Submission Report will contain error messages.
If you have received error messages, check your trial data file to ensure that you have used valid values. Validate the trial information using the specification for the release ${ReleaseNumber}.

'
where name = 'trial.batchUpload.errorMsg';

-- trial.batchUpload.body
update pa_properties
set value = '* Number of Trials Submitted: ${totalCount}
* Number of Trials Successfully Registered: ${successCount}
* Number of Trials Not Registered Due to Failure: ${failedCount}'
where name = 'trial.batchUpload.body';

-- trial.batchUpload.bodyHeader
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting your trials to the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP) using the batch upload feature.  
You are receiving this message because you have completed a batch registration. 

You can review the Summary of Your Submission below.  

SUMMARY OF SUBMISSION
------------------------------------------------------

'
where name = 'trial.batchUpload.bodyHeader';