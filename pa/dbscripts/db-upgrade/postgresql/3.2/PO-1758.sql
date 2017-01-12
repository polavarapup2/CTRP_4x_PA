INSERT INTO PA_PROPERTIES values(41,'trial.batchUpload.bodyHeader','Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting your trials to the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP) using the batch upload functionality.  
You are receiving this message because you have completed a batch registration. 

Please review the summary of your submission.

SUMMARY OF SUBMISSION
------------------------------------------------------

');

INSERT INTO PA_PROPERTIES values(42,'trial.batchUpload.reportMsg','

Please note:

A progress report is attached to this email and the summary of submission includes the total number of submitted trials read by the system.  The failed number can be misleading as it might include number of empty lines that include any blank.
Progress report attachment reading:
1.  Progress report includes loading status for each trial record in your data file. 
2.  Record is identified by the unique identifier captured from the first column in the data file. 
3.  NCI trial identifier is listed for each successfully recorded trial.
4.  Error message is displayed for each record with failed registration.    
');

INSERT INTO PA_PROPERTIES values(43,'trial.batchUpload.bodyFooter','


If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for using the NCI Clinical Trials Reporting Program.
');

INSERT INTO PA_PROPERTIES values(44,'trial.batchUpload.errorMsg','

Please note:

No progress report is attached to this email and the summary of submission includes error message in case if uploaded files reading failed. 
Please check your trial data file specification for validity. 
You need to use trial specification valid for the release ${ReleaseNumber}.  
');

UPDATE PA_PROPERTIES set value ='

Please see attached report for more details.

Total Trials Submitted: ${totalCount}
Successfully Registered: ${successCount}
Failed: ${failedCount}' where name = 'trial.batchUpload.body';

UPDATE PA_PROPERTIES set value ='NCI Clinical Trials Reporting Program (CTRP): Confirmation of successful batch trial registration'
where name = 'trial.batchUpload.subject';
