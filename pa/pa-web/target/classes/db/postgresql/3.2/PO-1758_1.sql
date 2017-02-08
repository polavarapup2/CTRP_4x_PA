UPDATE PA_PROPERTIES set value ='Total Trials Submitted: ${totalCount}
Successfully Registered: ${successCount}
Failed: ${failedCount}' where name = 'trial.batchUpload.body';

UPDATE PA_PROPERTIES set value ='NCI Clinical Trials Reporting Program (CTRP): Confirmation of batch trial registration'
where name = 'trial.batchUpload.subject';

UPDATE PA_PROPERTIES set value ='


If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for using the NCI Clinical Trials Reporting Program.
'
where name = 'trial.batchUpload.bodyFooter';