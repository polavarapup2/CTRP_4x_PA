delete from pa_properties;
insert into pa_properties(IDENTIFIER,name,value) VALUES(1,'tsr.subject',
'NCI Clinical Trials Reporting Program (CTRP) Trial Summary Report and ClinicalTrials.gov Registration File');

insert into pa_properties(IDENTIFIER,name,value) VALUES(2,'tsr.body',
'${CurrentDate}

Dear ${SubmitterName}

Your Trial, ${leadOrgTrialId}, ${trialTitle}, received by the NCI Clinical Trial Reporting Office (CTRO) on ${receiptDate} and assigned NCI Trial ID ${nciTrialID}, has been processed by the CTRO.

Please review the attached Trial Summary Report (${fileName}), including all data fields, to ensure that they accurately reflect the trial. Please mark any changes that are required in the attached file and return the updated file by e-mail to the CTRO at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes, as appropriate. If there are no changes, please respond to this email confirming that the report accurately reflects your trial. Please include the NCI Trial ID, as above, in all correspondence. If a response is NOT received within five (5) business days, CTRO will conclude that the information contained in this Trial Summary Report is acceptable.
 
We are also including a file ${fileName2} suitable for posting to ClinicalTrials.gov as an attachment. This file reflects the information contained within the Trial Summary Report in a format that meets the specifications of the National Library of Medicine ClinicalTrials.gov database. You may use the file to register your trial with ClinicalTrials.gov. For information on how to submit your trial to ClinicalTrials.gov please refer to the CTRP web site at http://www.cancer.gov/ncictrp. 

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).
');

insert into pa_properties(IDENTIFIER,name,value) VALUES(3,'smtp',
'localhost');

insert into pa_properties(IDENTIFIER,name,value) VALUES(4,'fromaddress',
'ncictro@mail.nih.gov');

insert into pa_properties(IDENTIFIER,name,value) VALUES(5,'rejection.subject',
'NCI Clinical Trials Reporting Program (CTRP) Trial Registration Rejection Notice.');

insert into pa_properties(IDENTIFIER,name,value) VALUES(6,'rejection.body',
'Dear ${SubmitterName}

The following trial was submitted for registration to the NCI Clinical Trials Reporting Office (CTRO): 

Lead Organization Trial Identifier ${leadOrgTrialId} 

This trial cannot be registered by the CTRO for the following reasons: ${reasoncode}

If you feel there has been an error in rejecting this trial registration, or if you have additional questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov 

Thank you for your participation in NCI Clinical Trials Reporting Program (CTRP).
');

insert into pa_properties(IDENTIFIER,name,value) VALUES(7,'allowed.uploadfile.types','doc,pdf,xls,wpd,docx,docm,xlsx,xlsm,xlsb');
insert into pa_properties(IDENTIFIER,name,value) VALUES(8,'trial.register.subject',
'NCI Clinical Trials Reporting Program (CTRP) Trial Registration Acknowledgement');
insert into pa_properties(IDENTIFIER,name,value) VALUES(9,'trial.register.body',
'Dear ${SubmitterName}

You have successfully registered the following trial with the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP):
Lead Organization Trial Identifier ${leadOrgTrialIdentifier}   
 
Your trial has been assigned a unique NCI identification number for tracking that you will need to reference in future correspondence with the Clinical Trials Reporting Office (CTRO): 
NCI Trial Identifier ${nciTrialIdentifier}
 
Your trial will now be processed by the staff of the CTRO. Upon completion, the CTRO will e-mail you a Trial Summary Report listing key data elements that have been abstracted from the trial protocol.  At that time, the CTRO will request that you review the Trial Summary Report for accuracy and respond with any changes that may be required. 
 
If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov
 
Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).
');

insert into pa_properties(IDENTIFIER,name,value) VALUES(10,'user.account.subject',
'NCI Clinical Trials Reporting Program (CTRP) User Activation');

insert into pa_properties(IDENTIFIER,name,value) VALUES(11,'user.account.body',
'Thank you for registering with NCI Clinical Trials Reporting Program. Please click on the link below or copy paste the URL in the browser to activate your account.');

insert into pa_properties(IDENTIFIER,name,value) VALUES(12,'trial.batchUpload.subject',
'Clinical Trials Reporting Program (CTRP) - Batch Trial Upload Status');

insert into pa_properties(IDENTIFIER,name,value) VALUES(13,'trial.batchUpload.body',
'Thank you for using the NCI Clinical Trials Reporting Program.
 
Here is a brief summary of the batch trial submission.
Please see attached report for more details.
----------------------------------------------------------
Total Trials Submitted: ${totalCount}
Successfully Registered: ${successCount}
Failed: ${failedCount}');

insert into pa_properties(IDENTIFIER,name,value) VALUES(14,'trial.amend.subject',
'NCI Clinical Trials Reporting Program (CTRP) Trial Amendment Acknowledgement');

insert into pa_properties(IDENTIFIER,name,value) VALUES(15,'trial.amend.body',
'Date: ${CurrentDate}

Dear ${SubmitterName},

The amendment for NCI Trial Identifier ${nciTrialIdentifier} has been submitted.

Amendment Information 
Amendment Number: ${amendmentNumber}
Amendment Date: ${amendmentDate}

The amendment will be processed by CTRO staff. You will be notified about the status of this submission once it has been reviewed by our staff.

If you have questions regarding this submission, please contact us at ncictro@mail.nih.gov

Thank you for submitting your trial amendment to CTRP.');

insert into pa_properties(IDENTIFIER,name,value) VALUES(16,'trial.amend.accept.subject',
'NCI Clinical Trials Reporting Program (CTRP) Trial  Amendment Acceptance');

insert into pa_properties(IDENTIFIER,name,value) VALUES(17,'trial.amend.accept.body',
'Date: ${CurrentDate}

Dear ${SubmitterName},

The amendment for the following trial was accepted by the NCI Clinical Trials Reporting Office (CTRO).

NCI Trial Identifier: ${nciTrialIdentifier}

Title: ${title}

Amendment Number: ${amendmentNumber}

Amendment Date: ${amendmentDate}.

Amendment will be abstracted by the CTRO staff. Upon completion of this abstraction, the CTRO will e-mail you a new Trial Summary Report that contains key data elements.
We request that  you review the Trial Summary Report for accuracy and respond to note your acceptance of this abstraction or to provide your changes to the report. 
 
If you have questions regarding this acceptance please contact us at ncictro@mail.nih.gov
 
Thank you for registering amendment to your trial with NCI Clinical Trials Reporting Program (CTRP).
');

insert into pa_properties(IDENTIFIER,name,value) VALUES(18,'trial.amend.reject.subject',
'NCI Clinical Trials Reporting Program (CTRP) Trial Amendment Rejection Notice.');

insert into pa_properties(IDENTIFIER,name,value) VALUES(19,'trial.amend.reject.body',
'Date: ${CurrentDate}

Dear ${SubmitterName},

The amendment for the following trial was submitted to the NCI Clinical Trials Reporting Office (CTRO)

NCI Trial Identifier:  ${nciTrialIdentifier}

Title: ${title}

Amendment Number: ${amendmentNumber}

Amendment Date: ${amendmentDate}.

This amendment was not accepted by the CTRO for the following reasons: ${reasonForRejection}

If you wish to discuss the rejection of this trial amendment, please contact us at ncictro@mail.nih.gov

Thank you for your participation in NCI Clinical Trials Reporting Program (CTRP).
');
insert into pa_properties(IDENTIFIER,name,value) VALUES(20,'tsr.amend.body',
'Date: ${CurrentDate}

Dear ${SubmitterName},


Amendment for the trial ${trialTitle} with NCI trial Identifier: ${nciTrialID} received by the NCI Clinical Trial Reporting Office (CTRO) on ${receiptDate} has been processed by the CTRO.

Please review the attached Trial Summary Report (${fileName}), including all data fields, to ensure that they accurately reflect the trial. Please mark any changes that are required in the attached file and return the updated file by e-mail to the CTRO at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes, as appropriate. If there are no changes, please respond to this email confirming that the report accurately reflects your trial. Please include the NCI Trial ID, as above, in all correspondence. If a response is NOT received within five (5) business days, CTRO will conclude that the information contained in this Trial Summary Report is acceptable.
 
We are also including a file ${fileName2} suitable for posting to ClinicalTrials.gov as an attachment. This file reflects the information contained within the Trial Summary Report in a format that meets the specifications of the National Library of Medicine ClinicalTrials.gov database. You may use the file to update your trial with ClinicalTrials.gov. For information on how to submit your trial to ClinicalTrials.gov please refer to the CTRP web site at http://www.cancer.gov/ncictrp. 

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).
');

insert into pa_properties(IDENTIFIER,name,value) VALUES(21,'trial.accept.subject',
'NCI Clinical Trials Reporting Program (CTRP) Trial Submission Acceptance Notice');

insert into pa_properties(IDENTIFIER,name,value) VALUES(22,'trial.accept.body',
'Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting your trial for registration.  NCI Clinical Trials Reporting Office (CTRO) has accepted and will abstract  the following trial:

NCI Trial Identifier: ${nciTrialIdentifier}

Title: ${title}

If you submitted a protocol document with your registration, CTRO will email you a Trial Summary Report for your review once abstracting is complete. Attached to that email will be a file with data in a format that can be used to upload to ClinicalTrials.gov (XML format). 

Abstracting by the CTRO is expected to not take more than ten (10) business days.  Upon receipt of the Trial Summary Report, you will have five (5) business days to review the Trial Summary Report and inform the CTRO of required changes.

If you did not submit a protocol document (i.e., for a proprietary trial), you will not receive a Trial Summary Report or XML file. 

If you have any questions about the processing of this trial, please contact CTRO at ncictro@mail.nih.gov
');

--insert into pa_properties(IDENTIFIER,name,value) VALUES(23,'trial.update.subject',
--'NCI Clinical Trials Reporting Program (CTRP) Trial Update Acknowledgement');

--insert into pa_properties(IDENTIFIER,name,value) VALUES(24,'trial.update.body',
--'You have successfully submitted the update to the trial with NCI Trial Identifier ${nciTrialIdentifier}

--Your submission will now be processed by the staff of the CTRO. Upon completion, the CTRO will e-mail you a Trial Summary Report listing key data elements that have been abstracted from the trial protocol.  At that time, the CTRO will request that you review the Trial Summary Report for accuracy and respond with any changes that may be required. 
 
--If you have questions on this or other CTRP topics, please contact us at ncictrp@mail.nih.gov
 
--Thank you for registering your update with NCI Clinical Trials Reporting Program (CTRP).
--');
