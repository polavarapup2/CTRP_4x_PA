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

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).'
where name = 'trial.register.body';

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

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP)'
where name = 'trial.amend.body';

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

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where name = 'trial.amend.accept.body';

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

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where name = 'trial.amend.reject.body';

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

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).

* The NCI Clinical Trials Reporting Office (CTRO) makes every effort to process and return a Trial Summary Report (TSR) and XML file for all abbreviated protocols within a ten (10) day period. This ten day period begins after a complete submission, and therefore the submitter should be available during that time to resolve any discrepancies (for example, missing documentation, regulatory information, etc.)  Additionally, the potential variability of submission volume at any given time and/or complexity of a protocol can impact the processing time. If protocol submission volume exceeds the CTRO''s capacity for processing, the CTRO will prioritize submissions based on submitter need. If a CTRP registrant requires expedited processing of a protocol submission,  please contact the CTRO. Be sure to reference the NCI CTRP ID, and request priority processing.'
where name = 'trial.accept.body';

-- trial.update.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

You are receiving this email because NCI CTRP received an update for:
* NCI Trial Identifier: ${nciTrialIdentifier}
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   
* Title: ${trialTitle}.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where name = 'trial.update.body';

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

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program.'
where name = 'tsr.proprietary.body';

-- trial.partial.register.body
update pa_properties
set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

You have saved draft of your submission for the following trial with the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP):
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}
* Lead Organization: ${leadOrgName}
* Title: ${trialTitle}.  

Your saved  draft has been assigned a unique temporary identifier for tracking purposes.

Important! You can save your draft  for a maximum of 30 days. To retrieve and complete your submission, use the "Search Saved Draft" feature on the "Search Trials" page in the CTRP Registration application.

Note that Clinical Trials Reporting Office (CTRO) staff will not access or process your trial  until you have completed the submission. 

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program.'
where name = 'trial.partial.register.body';

-- trial.batchUpload.bodyFooter
update pa_properties
set value = 'If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for using the NCI Clinical Trials Reporting Program.'
where name = 'trial.batchUpload.bodyFooter';

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

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for registering your trial with NCI Clinical Trials Reporting Program.'
where name = 'proprietarytrial.register.body';

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

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program.'
where name = 'noxml.tsr.amend.body';

-- trial.admin.accept.body
update pa_properties
set value = 'Date: ${CurrentDate}

 Dear  ${SubmitterName},

 Your request for Administrator status for the organization, ${affliateOrgName}, in CTRP has been granted.

User ${SubmitterName} can access all trials for the ${affliateOrgName}  organization, review and update access to trials owned by the organization, and assign new administrator status to other users from the organization. 

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where name = 'trial.admin.accept.body';

-- trial.admin.reject.body
update pa_properties
set value = 'Date: ${CurrentDate}

 Dear  ${SubmitterName},

 Your request for Administrator status for the organization, ${affliateOrgName}, in CTRP has been denied.
 The reason for the rejection was recorded as follows: 
       ${rejectReason}

 If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

 Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where name = 'trial.admin.reject.body';

-- self.registration.pleaseWait.email.body
update pa_properties
set value = 'Dear {0} {1},

Thank you for requesting an account for the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP) system.

You will receive an email notification with instructions for activating your account.  
Please allow two (2) business days to process your request.

If you have questions, please contact us at ncictro@mail.nih.gov.

Thank you,
 
NCI Clinical Trials Reporting Office
http://www.cancer.gov/ncictrp
ncictro@mail.nih.gov'
where name = 'self.registration.pleaseWait.email.body';