-- tsr.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP): Trial Summary Report (TSR) Ready for Review, XML attached, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}'
where name = 'tsr.subject';
  
-- tsr.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

The NCI Clinical Trials Reporting Office (CTRO) has processed the following trial:
* NCI Trial Identifier: ${nciTrialIdentifier}
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   
* Title: ${trialTitle}
* Submission Date: ${receiptDate}.

Please review the attached Trial Summary Report (TSR), including all data fields, to ensure that the report is accurate.
* If you need to make any changes, enter them in the attached file and return the updated file by email as an attachment to the Clinical Trials Reporting Office (CTRO) at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes as appropriate.
* If  the information is correct, reply to this email to confirm that the report is accurate. Be sure to include the NCI Trial ID, as above, in all correspondence. 

Important! If the CTRO does not receive a response from you within five (5) business days, CTRO staff will conclude that the information contained in this TSR is accurate.

Also attached is an XML file with data formatted for posting to ClinicalTrials.gov (if required). This file contains a subset of the information contained in the TSR. The XML file requires independent validation and submission.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program.'
where name = 'tsr.body';

-- rejection.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP): Trial Registration Rejection, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}'
where name = 'rejection.subject';

-- rejection.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

The following trial was submitted to the NCI Clinical Trials Reporting Office (CTRO) for registration in the NCI Clinical Trials Reporting Program (CTRP):
* NCI Trial Identifier: ${nciTrialIdentifier}
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}
* Title: ${trialTitle}
* Submission Date: ${receiptDate}.


The CTRO staff cannot register this trial for the following reason(s): ${reasoncode}.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program.'
where name = 'rejection.body';

-- trial.register.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP): Confirmation of Successful Trial Registration - ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}'
where name = 'trial.register.subject';

-- trial.register.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting your trial to the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP). You are receiving this message because you have successfully completed the registration of the following trial:
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}
* Lead Organization: ${leadOrgName}
* Title: ${trialTitle}.
 
Your trial has been assigned a unique NCI identification number for tracking that you will need to reference in future correspondence with the Clinical Trials Reporting Office (CTRO): 
* NCI Trial Identifier: ${nciTrialIdentifier}
* ${errors}

CTRO staff will consider your trial for acceptance in the CTRP system. You will receive an acceptance or rejection email with further information within two (2) business days.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).'
where name = 'trial.register.body';

-- user.account.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP) User Activation'
where name = 'user.account.subject';

-- user.account.body
update pa_properties
set value = 'Thank you for registering with the National Cancer Institute (NCI) Clinical Trials Reporting Program. 

Click  the link below or copy and paste the URL into your browser to activate your account.'
where name = 'user.account.body';

-- trial.batchUpload.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP): Confirmation of batch trial registration'
where name = 'trial.batchUpload.subject';

-- trial.batchUpload.body
update pa_properties
set value = '* Total Number Submitted Trials: ${totalCount}
* Number of Submitted Trials Successfully Registered: ${successCount}
* Number of Submitted Trials Not Registered Due to Failure: ${failedCount}'
where name = 'trial.batchUpload.body';

-- trial.amend.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP): Trial Amendment Submission Notification, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}'
where name = 'trial.amend.subject';

-- trial.amend.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

The Clinical Trials Reporting Office (CTRO) has received the amendment you submitted for:
* NCI Trial Identifier: ${nciTrialIdentifier}
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}
* Lead Organization: ${leadOrgName}
* Title: ${trialTitle}.

Amendment Information:
*  Amendment Number: ${amendmentNumber}
*  Amendment Date: ${amendmentDate}
	
CTRO staff will consider your trial for acceptance in the CTRP system. You will receive an acceptance or rejection email with further information within 2 business days.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP)'
where name = 'trial.amend.body';

-- trial.amend.accept.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP): Acceptance of Trial Amendment Submission, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}'
where name = 'trial.amend.accept.subject';

-- trial.amend.accept.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting your trial amendment to the NCI Clinical Trials Reporting Office (CTRO). The amendment to the following trial record has been accepted for processing:
* NCI Trial Identifier: ${nciTrialIdentifier}
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}
* Title: ${title}
* Amendment Number: ${amendmentNumber}
* Amendment Date: ${amendmentDate}.

CTRO staff will abstract the amendment that you submitted to update your record in CTRP. When abstraction is complete, you will receive a new Trial Summary Report (TSR) that reflects the changes indicated in the trial amendment. Attached to that email will be an XML file which contains data formatted for posting to ClinicalTrials.gov (if required). The XML file requires independent validation and submission.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where name = 'trial.amend.accept.body';

-- trial.amend.reject.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP): Notice of Rejection of Trial Amendment, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}'
where name = 'trial.amend.reject.subject';

-- trial.amend.reject.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

An amendment for the following trial currently listed in the NCI Clinical Trials Reporting Program (CTRP) system was submitted to the NCI Clinical Trials Reporting Office (CTRO):
* NCI Trial Identifier: ${nciTrialIdentifier}
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}
* Title: ${title}
* Submission Date: ${receiptDate}
* Amendment Number: ${amendmentNumber}
* Amendment Date: ${amendmentDate}.

CTRO staff cannot accept this amendment for the following reasons: ${reasonForRejection}.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where name = 'trial.amend.reject.body';

-- tsr.amend.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

NCI Clinical Trials Reporting Office (CTRO) staff  have processed the amendment for the following trial:
* NCI Trial Identifier: ${nciTrialIdentifier}
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   
* Title: ${trialTitle}
* Submission Date: ${receiptDate}
* Amendment Number: ${amendmentNumber}
* Amendment Date: ${amendmentDate}.

Please review the attached Trial Summary Report (TSR), including all data fields, to ensure that the report is accurate.
* If you need to make any changes, enter them in the attached file and return the updated file by email as an attachment to the Clinical Trials Reporting Office (CTRO) at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes as appropriate.
* If  the information is correct, reply to this email to confirm that the report is accurate. Be sure to include the NCI Trial ID, as above, in all correspondence. 

Important! If the CTRO does not receive your response within five (5) business days, CTRO staff will conclude that the information contained in this TSR is accurate.

Also attached is an XML file which contains data formatted for posting to ClinicalTrials.gov (if required). This file contains a subset of the information contained in the TSR. The XML file requires independent validation and submission.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program.'
where name = 'tsr.amend.body';

-- trial.accept.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP): Acceptance of trial submission for registration, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}'
where name = 'trial.accept.subject';

-- trial.accept.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting the following trial for registration in the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP):
The NCI Clinical Trials Reporting Office (CTRO) has accepted the following trial for registration:
* NCI Trial Identifier: ${nciTrialIdentifier}
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}
* Title: ${title}

If you submitted a complete trial,  CTRO staff will process your trial, including abstraction of the protocol document, to produce a CTRP trial record. CTRP processing is expected to take no more than ten (10) business days*. The CTRO will email you a Trial Summary Report (TSR) for review once processing is complete. Attached to that email will be  an XML file which contains data formatted for posting to ClinicalTrials.gov (if required). This file contains a subset of the information contained in the TSR. The XML file requires independent validation and submission. 

If you submitted an abbreviated trial, and  you did not submit a protocol document, you will receive a TSR for review once processing is complete.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).

* The NCI Clinical Trials Reporting Office (CTRO) makes every effort to process and return a Trial Summary Report (TSR) and XML file for all abbreviated protocols within a ten (10) day period. This ten day period begins after a complete submission, and therefore the submitter should be available during that time to resolve any discrepancies (for example, missing documentation, regulatory information, etc.)  Additionally, the potential variability of submission volume at any given time and/or complexity of a protocol can impact the processing time. If protocol submission volume exceeds the CTRO''s capacity for processing, the CTRO will prioritize submissions based on submitter need. If a CTRP registrant requires expedited processing of a protocol submission,  please contact the CTRO. Be sure to reference the NCI CTRP ID, and request priority processing.'
where name = 'trial.accept.body';

-- trial.update.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP) Trial Update Notification - ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}'
where name = 'trial.update.subject';

-- trial.update.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

You are receiving this email because NCI CTRP received an update for:
* NCI Trial Identifier: ${nciTrialIdentifier}
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   
* Title: ${trialTitle}.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where name = 'trial.update.body';

-- tsr.proprietary.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP): Abbreviated Trial Summary Report (TSR) Ready for Review, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}'
where name = 'tsr.proprietary.subject';

-- tsr.proprietary.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

The NCI Clinical Trials Reporting Office (CTRO) has processed the following trial:
* NCI Trial Identifier: ${nciTrialIdentifier}
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   
* Title: ${trialTitle}
* Submission Date: ${receiptDate}.

* If you need to make any changes, enter them in the attached file and return the updated file by email as an attachment to the Clinical Trials Reporting Office (CTRO) at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes as appropriate. 
* If  the information is correct, reply to this email to confirm that the report is accurate. Be sure to include the NCI Trial ID, as above, in all correspondence. 

Important! If the CTRO does not receive your response within five (5) business days, CTRO staff will conclude that the information contained in this Trial Summary Report (TSR) is accurate.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program.'
where name = 'tsr.proprietary.body';

-- CDE_REQUEST_TO_EMAIL_SUBJECT
update pa_properties
set value = 'New CDE Request'
where name = 'CDE_REQUEST_TO_EMAIL_SUBJECT';

-- CDE_REQUEST_TO_EMAIL_TEXT
update pa_properties
set value = 'The Clinical Trials Reporting Office (CTRO) has identified a need for a new permissible value to help us build structured eligibility criteria for Protocol NCI-xxxxxx .

The concept is:

The text of the eligibility criteria per the protocol is as follows:

If you need any further information to create the new permissible value, contact the CTRO at ncictro@mail.nih.gov.

Thank you,
The CTRO Staff'
where name = 'CDE_REQUEST_TO_EMAIL_TEXT';

-- trial.ownership.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP) Trial Ownership Transfer Notification'
where name = 'trial.ownership.subject';

-- trial.ownership.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

The ownership of trial record ${nciTrialIdentifier} has been transferred from ${oldOwner} to ${newOwner}. 
The new owner is now able to amend and update the trial using any of the "My Trials" features in the Clinical Trials Reporting Program (CTRP) Registration application. 

If you feel that there has been an error in trial ownership transfer, or if you have additional questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in  the NCI Clinical Trials Reporting Program.'
where name = 'trial.ownership.body';

-- trial.partial.register.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP): Acknowledgment of Partial Submission - ${leadOrgTrialIdentifier}'
where name = 'trial.partial.register.subject';

-- trial.partial.register.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

You have saved draft of your submission for the following trial with the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP):
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}
* Lead Organization: ${leadOrgName}
* Title: ${trialTitle}.  

Your saved  draft has been assigned a unique temporary identifier for tracking purposes.

Important! You can save your draft  for a maximum of 30 days. To retrieve and complete your submission, use the “Search Saved Draft” feature on the “Search Trials” page in the CTRP Registration application.

Note that Clinical Trials Reporting Office (CTRO) staff will not access or process your trial  until you have completed the submission. 

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program.'
where name = 'trial.partial.register.body';

-- xml.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP): Trial Summary Report (TSR) and XML attached, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}'
where name = 'xml.subject';

-- xml.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

Per your request, attached is the Trial Summary Report (TSR) file for the following trial:
* NCI Trial Identifier: ${nciTrialIdentifier}
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   
* Title: ${trialTitle}
* Submission Date: ${receiptDate}.

Also attached is an XML file, ${fileName}, which contains data formatted for posting to ClinicalTrials.gov (if required). This file contains a subset of the information contained in the TSR. The XML file requires independent validation and submission.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).'
where name = 'xml.body';

-- trial.batchUpload.bodyHeader
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting your trials to the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP) using the batch upload feature.  
You are receiving this message because you have completed a batch registration. 

You can review the summary of your submission below.

SUMMARY OF SUBMISSION
------------------------------------------------------

'
where name = 'trial.batchUpload.bodyHeader';

-- trial.batchUpload.reportMsg
update pa_properties
set value = 'Note:

A progress report is attached to this email. The summary of submission report includes the total number of submitted trials read by the system.  The number of submissions that failed can be misleading because it may include a number of empty lines in the file you submitted.
The progress report contains the following information:
* Loading status for each trial record in your data file
* Record identifier captured from the first column in the data file 
* NCI trial identifier  for each successfully recorded trial
* Error message  for each record that failed registration'
where name = 'trial.batchUpload.reportMsg';

-- trial.batchUpload.bodyFooter
update pa_properties
set value = 'If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for using the NCI Clinical Trials Reporting Program.'
where name = 'trial.batchUpload.bodyFooter';

-- trial.batchUpload.errorMsg
update pa_properties
set value = 'Note:
If the CTRP system cannot process your files, you will not receive a progress report, and the summary of submission report will contain error messages.
If you have received error messages, check your trial data file to ensure that you have used valid values. Validate the trial information using the specification for  the release ${ReleaseNumber}.'
where name = 'trial.batchUpload.errorMsg';

-- tsr.amend.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP): Amended Trial Summary Report (TSR) Ready for Review, XML attached, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}'
where name = 'tsr.amend.subject';

-- noxml.tsr.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP): Trial Summary Report (TSR) Ready for Review, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}'
where name = 'noxml.tsr.subject';

-- noxml.tsr.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

The NCI Clinical Trials Reporting Office (CTRO) has processed the following trial:
* NCI Trial Identifier: ${nciTrialIdentifier}
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   
* Title: ${trialTitle}
* Submission Date: ${receiptDate}.

Please review the attached Trial Summary Report (TSR), including all data fields, to ensure that the report is accurate.
* If you need to make any changes, enter them in the attached file and return the updated file by email as an attachment to the Clinical Trials Reporting Office (CTRO) at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes as appropriate.
* If  the information is correct, reply to this email to confirm that the report is accurate. Be sure to include the NCI Trial ID, as above, in all correspondence. 

Important! If the CTRO does not receive a response from you within five (5) business days, CTRO staff will conclude that the information contained in this TSR is accurate.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program.'
where name = 'noxml.tsr.body';

-- proprietarytrial.register.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP): Confirmation of Successful Trial Registration - ${subOrgTrialIdentifier}, ${nciTrialIdentifier}'
where name = 'proprietarytrial.register.subject';

-- proprietarytrial.register.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting your trial to the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP). You are receiving this message because you have successfully completed the registration of the following trial:

* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   
* Submitting Organization: ${subOrg}
* Submitting Organization Trial Identifier: ${subOrgTrialIdentifier}
* Title: ${trialTitle}
 
Your trial has been assigned a unique NCI identification number for tracking  purposes. You will need to reference this identifier in future correspondence with the Clinical Trials Reporting Office (CTRO):
* NCI Trial Identifier ${nciTrialIdentifier}
* ${errors}

CTRO staff will consider your trial for acceptance in the CTRP system. You will receive an acceptance or rejection email with further information within two (2) business days.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for registering your trial with NCI Clinical Trials Reporting Program.'
where name = 'proprietarytrial.register.body';

-- noxml.tsr.amend.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP): Amended Trial Summary Report (TSR) Ready for Review, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}'
where name = 'noxml.tsr.amend.subject';

-- noxml.tsr.amend.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

The NCI Clinical Trials Reporting Office (CTRO) has processed an amendment to the following trial record:
* NCI Trial Identifier: ${nciTrialIdentifier}
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   
* Title: ${trialTitle}
* Submission Date: ${receiptDate}
* Amendment Date: ${amendmentDate}

Please review the attached Trial Summary Report (TSR), including all data fields, to ensure that the report is accurate.
* If you need to make any changes, enter them in the attached file and return the updated file by email as an attachment to the Clinical Trials Reporting Office (CTRO) at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes as appropriate.
* If  the information is correct, reply to this email to confirm that the report is accurate. Be sure to include the NCI Trial ID, as above, in all correspondence. 

Important! If the CTRO does not receive your response within five (5) business days, CTRO staff will conclude that the information contained in this TSR is accurate.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program.'
where name = 'noxml.tsr.amend.body';

-- trial.admin.accept.subject
update pa_properties
set value = 'Administration status request'
where name = 'trial.admin.accept.subject';

-- trial.admin.accept.body
update pa_properties
set value = 'Date: ${CurrentDate}

 Dear  ${SubmitterName},

 Your request for Administrator status for the organization, ${affliateOrgName}, in CTRP has been granted.

User ${SubmitterName} can access all trials for the ${affliateOrgName}  organization, review and update access to trials owned by the organization, and assign new administrator status to other users from the organization. 

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where name = 'trial.admin.accept.body';

-- trial.admin.reject.body
update pa_properties
set value = 'Date: ${CurrentDate}

 Dear  ${SubmitterName},

 Your request for Administrator status for the organization, ${affliateOrgName}, in CTRP has been denied.
 The reason for the rejection was recorded as follows: 
       ${rejectReason}

 If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

 Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where name = 'trial.admin.reject.body';

-- abstraction.script.subject
update pa_properties
set value = 'Abstraction Verified No Response Script Failure'
where name = 'abstraction.script.subject';

-- MARKER_REQUEST_SUBJECT
update pa_properties
set value = 'Additional Permissible Value Request: ${markerName} for ${trialIdentifier}'
where name = 'MARKER_REQUEST_SUBJECT';

-- MARKER_REQUEST_BODY
update pa_properties
set value = 'The Clinical Trials Reporting Office (CTRO) has identified a need for a new permissible value to help with the marker abstraction for Protocol ${trialIdentifier}.
The concept is ${markerName}.
Found in HUGO? ${foundInHugo}
HUGO Marker Code: ${hugoCode}

Following is the text of the marker per the protocol:
${markerText}

If you need any further information to create the new permissible value, contact the CTRO at ncictro@mail.nih.gov.

Thank you,
The CTRO Staff'
where name = 'MARKER_REQUEST_BODY';

-- CDE_MARKER_REQUEST_HUGO_CLAUSE
update pa_properties
set value = 'HUGO Marker Code: ${hugoCode}'
where name = 'CDE_MARKER_REQUEST_HUGO_CLAUSE';

-- CDE_MARKER_REQUEST_MARKER_TEXT_CLAUSE
update pa_properties
set value = 'Here is the text of the marker as written in the protocol:
${markerText}'
where name = 'CDE_MARKER_REQUEST_MARKER_TEXT_CLAUSE';

-- user.usernameSearch.body
update pa_properties
set value = 'Dear ${firstName} ${lastName},

Thank you for your inquiry.

Per your request, below is the National Cancer Institute Clinical Trials Reporting Program (CTRP) username that is associated with the email you provided:

Username: ${userNames}

If you have questions, please contact us at ncictro@mail.nih.gov.

Thank you,

NCI Clinical Trials Reporting Office
http://www.cancer.gov/ncictrp
ncictro@mail.nih.gov'
where name = 'user.usernameSearch.body';

-- user.usernameSearch.subject
update pa_properties
set value = 'NCI CTRP Username Retrieval.'
where name = 'user.usernameSearch.subject';

-- self.registration.appsupport.email.subject
update pa_properties
set value = 'CTRP: New Account Request'
where name = 'self.registration.appsupport.email.subject';

-- self.registration.appsupport.email.body
update pa_properties
set value = 'We have received a request for a  new account in the  Clinical Trials Reporting Program (CTRP) Clinical Trials Registration application. 

To create the account, follow the steps below:
1. Create an account in the NCI external LDAP. The user information is as follows:
    * First Name: {0}
    * Last Name: {1}
    * Affiliated Organization: {2}
    * Phone Number: {3}
    * Email: {4}

2a.  Add the NCI account to the Grid by logging the account into the Grid Portal, or logging the account into GAARDS.
2b. Add the NCI account to the Submitters group.
3. Wait five (5) minutes to allow the CTRP system to synchronize your information and create a local record. 
4. Navigate to the following URL, where you will receive confirmation that the user was  activated successfully: {5} 
5. Inform the user that his/her account is ready.

If you have questions, please contact us at ncictro@mail.nih.gov.

Thank you,
NCI Clinical Trials Reporting Office'
where name = 'self.registration.appsupport.email.body';

-- self.registration.pleaseWait.email.subject
update pa_properties
set value = 'New NCI CTRP Account Request'
where name = 'self.registration.pleaseWait.email.subject';

-- self.registration.pleaseWait.email.body
update pa_properties
set value = 'Dear {0} {1},

Thank you for requesting an account for the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP) system.

You will receive an email notification with instructions for activating your account.  
Please allow two (2) business days to process your request.

If you have questions, please contact us at ncictro@mail.nih.gov

Thank you,
 
NCI Clinical Trials Reporting Office
http://www.cancer.gov/ncictrp
ncictro@mail.nih.gov'
where name = 'self.registration.pleaseWait.email.body';

-- trial.register.unidentifiableOwner.email.subject
update pa_properties
set value = 'New {0} Trial {1} Registration with Errors/Warnings'
where name = 'trial.register.unidentifiableOwner.email.subject';

-- trial.register.unidentifiableOwner.email.body
update pa_properties
set value = 'Dear CTRO staff,
 
A new trial {0} has been submittedy {1} for registration with CTRP system. However, one or more of the trial record owners could not be assigned because their email addresses, as submitted by {1}, do not belong to registered CTRP user(s).

The unregistered email address is as follows:
{2}

Please contact {1} or a representative from the owner''s affiliated organization to identify the correct owner.

Thank you,

NCI Clinical Trials Reporting Program'
where name = 'trial.register.unidentifiableOwner.email.body';

-- trial.register.unidentifiableOwner.sub.email.body
update pa_properties
set value = 'A new trial  has been submitted for registration with the CTRP system.  However, one or more of the trial record owners could not be assigned because their email addresses, as submitted, do not belong to registered CTRP user(s).

The unregistered email address is as follows:
{0}

Please contact {ownerName} or a representative from the owner’s affiliated organization to identify the correct owner.

Thank you,

NCI Clinical Trials Reporting Program'
where name = 'trial.register.unidentifiableOwner.sub.email.body';

-- trial.onhold.reminder.subject
update pa_properties
set value = 'NCI Clinical Trials Reporting Program (CTRP): The processing of trial ${leadOrgTrialIdentifier}, ${nciTrialIdentifier} is on Hold'
where name = 'trial.onhold.reminder.subject';

-- trial.onhold.reminder.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

We would like to remind you that your trial record, identified below, has been  on hold as of  ${HoldDate} for the following reason: ${HoldReason}.
* NCI Trial Identifier: ${nciTrialIdentifier}
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}
* Title: ${trialTitle}
* Submission Date: ${receiptDate}

Important! Your trial will remain on hold until close of business on xxx. After that date, we will no longer be able to process this trial. 

Please contact us before this date to ensure timely processing of your trial. 

If you have questions on this or other Clinical Trials Reporting Program topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program.'
where name = 'trial.onhold.reminder.body';

-- trial.onhold.termination.subject
update pa_properties
set value = 'Submission terminated for trial ${nciTrialIdentifier} due to lack of response'
where name = 'trial.onhold.termination.subject';

-- trial.onhold.termination.body
update pa_properties
set value = 'The CTRP system has performed the following actions on trial record ${nciTrialIdentifier}, ${trialTitle}:
* Sent multiple requests for information to the submitter and trial record owner(s) to which no response was received within the allotted response period
* Closed the trial''s On-Hold state as of today''s date
* Terminated trial processing 
* Recorded the termination date in the CTRP system'
where name = 'trial.onhold.termination.body';

