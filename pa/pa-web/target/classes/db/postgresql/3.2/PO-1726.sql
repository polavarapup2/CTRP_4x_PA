ALTER TABLE PA_PROPERTIES ALTER COLUMN NAME TYPE VARCHAR(50);
ALTER TABLE PA_PROPERTIES ALTER COLUMN VALUE TYPE VARCHAR(5000);

-- Letter1

update pa_properties set value='NCI Clinical Trials Reporting Program (CTRP): Acknowledgment of Partial Submission - ${leadOrgTrialIdentifier}' 
where identifier=37 and name='trial.partial.register.subject';

update pa_properties set value='Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for initiating the submission of your trial with the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP). You are receiving this message because you saved a draft prior to completing the submission of the following trial:

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}

Lead Organization: ${leadOrgName}

Title: ${trialTitle}.

The draft of the submission that you saved has been assigned a unique temporary identifier ${temporaryidentifier} for tracking purposes.

Please note that the draft of your submission can be stored for a maximum of 30 days and is not processed or accessed by the CTRP Clinical Trials Reporting Office (CTRO) staff until it is completed. For retrieving and completing a draft submission, select the ‘Search Saved Draft’ option on the ‘Search Trials’ screen.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).' 
where identifier=38 and name='trial.partial.register.body';

-- Letter2

update pa_properties set value='NCI Clinical Trials Reporting Program (CTRP): Confirmation of Successful Trial Registration - ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}' 
where identifier=8 and name='trial.register.subject';

update pa_properties set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting your trial to the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP). You are receiving this message because you have successfully completed the registration of the following trial:

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}

Lead Organization: ${leadOrgName}

Title: ${trialTitle}.
 
Your trial has been assigned a unique NCI identification number for tracking that you will need to reference in future correspondence with the Clinical Trials Reporting Office (CTRO): 

NCI Trial Identifier: ${nciTrialIdentifier}
 
Your trial will now be considered for acceptance by the CTRO. Within 2 business days you will receive an acceptance or rejection email with further information.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).'
where identifier=9 and name='trial.register.body';

-- Letter3

update pa_properties set value='NCI Clinical Trials Reporting Program (CTRP) Trial Update Notification - ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}' 
where identifier=23 and name='trial.update.subject';

update pa_properties set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

You are receiving this email because NCI CTRP received an update for:

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   

Title: ${trialTitle}.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where identifier=24 and name='trial.update.body';

-- Letter4

update pa_properties set value='NCI Clinical Trials Reporting Program (CTRP): Trial Amendment Submission Notification, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}' 
where identifier=14 and name='trial.amend.subject';

update pa_properties set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

We have received the amendment you submitted for:

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}  

Lead Organization: ${leadOrgName}   

Title: ${trialTitle}.

Amentment Information 
	Amentment Number: ${amendmentNumber}

	Amentment Date:	${amendmentDate}.
	
Your amendment will now be considered for acceptance by the CTRO. Within 2 business days you will receive an acceptance or rejection email with further information.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where identifier=15 and name='trial.amend.body';

-- Letter5

update pa_properties set value='NCI Clinical Trials Reporting Program (CTRP): Acceptance of trial submission for registration, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}' 
where identifier=21 and name='trial.accept.subject';

update pa_properties set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting your trial for registration in the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP). It has been accepted for registration. The NCI Clinical Trials Reporting Office (CTRO) will now process the following trial:

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   

Title: ${title}.

If your trial is non–proprietary processing by CTRO will include abstraction of the protocol document to produce a CTRP record. Processing by the CTRO is expected to take no more than ten (10) business days*. The CTRO will email you a Trial Summary Report (TSR) for review once processing is complete. Attached to that email will be a file with data in a format (XML) that may be suitable for posting to ClinicalTrials.gov (if such file is requered). The XML file requires independent validation and submission. Upon receipt of the TSR and XML file, you will have five (5) business days to review the TSR and XML file and inform the CTRO of required changes.

If your trial is proprietary and you did not submit a protocol document, you will receive a TSR for review once processing is complete.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).

* The NCI Clinical Trials Reporting Office (CTRO) aims to process and return a Trial Summary Report (TSR) and XML file for all non-proprietary protocols within a ten (10) day window. This ten day timeframe begins after a complete submission, and therefore availability of the submitter to resolve any discrepancies is critical, e.g. missing documentation, regulatory information, etc. Additionally, the potential variability of submission volume at any given time and/or complexity of a protocol can impact the processing time. If protocol submission volume exceeds the CTRO’s capacity for processing, the CTRO will prioritize submissions based on submitter need. If a CTRP registrant requires expedited processing of a protocol submission please contact the CTRO, reference the NCI CTRP provided ID, and request priority processing.'
where identifier=22 and name='trial.accept.body';

-- Letter6

update pa_properties set value='NCI Clinical Trials Reporting Program (CTRP): Acceptance of Trial Amendment Submission, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}' 
where identifier=16 and name='trial.amend.accept.subject';

update pa_properties set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting your trial amendment to the NCI Clinical Trials Reporting Office (CTRO). The amendment to the following trial has been accepted for processing:

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   

Title: ${title}

Amendment Number: ${amendmentNumber}

Amendment Date: ${amendmentDate}.

The amendment that you submitted will be abstracted by the CTRO staff to update your record in CTRP. When abstraction is complete, you will be sent a new Trial Summary Report that reflects the changes indicated in the trial amendment. Attached to that email will be a file with data in a format (XML) that may be suitable for posting to ClinicalTrials.gov. The XML file requires independent validation and submission.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where identifier=17 and name='trial.amend.accept.body';

-- Letter7

update pa_properties set value='NCI Clinical Trials Reporting Program (CTRP): Trial Registration Rejection, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}' 
where identifier=5 and name='rejection.subject';

update pa_properties set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

The following trial was submitted to the NCI Clinical Trials Reporting Office (CTRO) for registration in NCI Clinical Trials Reporting Program:

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   

Title: ${trialTitle}

Submission Date: ${receiptDate}.

We regret to inform you that this trial cannot be registered by the CTRO for the following reasons: ${reasoncode}

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where identifier=6 and name='rejection.body';

-- Letter8

update pa_properties set value='NCI Clinical Trials Reporting Program (CTRP): Notice of Rejection of Trial Amendment, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}' 
where identifier=18 and name='trial.amend.reject.subject';

update pa_properties set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

An amendment for the following trial currently listed in the NCI Clinical Trials Reporting Program (CTRP) system was submitted to the NCI Clinical Trials Reporting Office (CTRO):

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   

Title: ${title}

Submission Date: ${receiptDate}

Amendment Number: ${amendmentNumber}

Amendment Date: ${amendmentDate}.

This amendment cannot be accepted by the CTRO for the following reasons: ${reasonForRejection}

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where identifier=19 and name='trial.amend.reject.body';

-- Letter9

update pa_properties set value='NCI Clinical Trials Reporting Program (CTRP): Proprietary Trial Summary Report (TSR) Ready for Review, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}' 
where identifier=25 and name='tsr.proprietary.subject';

update pa_properties set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

The following trial has been processed by the NCI Clinical Trials Reporting Office (CTRO):

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   

Title: ${trialTitle}

Submission Date: ${receiptDate}.

Please review the attached Trial Summary Report (${fileName}), including all data fields, to ensure that they accurately reflect your trial. Please mark any changes that are required in the attached file and return the updated file by e-mail to the CTRO at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes, as appropriate. If there are no changes, please respond to this email confirming that the report accurately reflects your trial. Please include the NCI Trial ID, as above, in all correspondence. If a response is NOT received within five (5) business days, CTRO will conclude that the information contained in this Trial Summary Report is acceptable.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where identifier=26 and name='tsr.proprietary.body';

-- Letter10

update pa_properties set value='NCI Clinical Trials Reporting Program (CTRP): Trial Summary Report (TSR) Ready for Review, XML attached, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}' 
where identifier=1 and name='tsr.subject';

update pa_properties set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

The following trial has been processed by the NCI Clinical Trials Reporting Office (CTRO):

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   

Title: ${trialTitle}

Submission Date: ${receiptDate}.

Please review the attached Trial Summary Report (${fileName}), including all data fields, to ensure that they accurately reflect the trial. Please mark any changes that are required in the attached file and return the updated file by e-mail to the CTRO at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes, as appropriate. If there are no changes, please respond to this email confirming that the report accurately reflects your trial. Please include the NCI Trial ID, provided above, in all correspondence. If a response is NOT received within five (5) business days, CTRO will conclude that the information contained in this Trial Summary Report is acceptable.

We are also including as an attachment an XML file ${fileName2} that may be suitable for posting to ClinicalTrials.gov. This file contains a subset of the information contained within the Trial Summary Report. The XML file requires independent validation and submission.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where identifier=2 and name='tsr.body';

-- Letter11

insert into pa_properties(IDENTIFIER,name,value) VALUES(45,'tsr.amend.subject',
'NCI Clinical Trials Reporting Program (CTRP): Amended Trial Summary Report (TSR) Ready for Review, XML attached, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}');

update pa_properties set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

The amendment for the following trial has been processed by the NCI Clinical Trials Reporting Office (CTRO):

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   

Title: ${trialTitle}

Submission Date: ${receiptDate}

Amendment Number: ${amendmentNumber}

Amendment Date: ${amendmentDate}.

Please review the attached Trial Summary Report (${fileName}), including all data fields, to ensure that they accurately reflect the trial. Please mark any changes that are required in the attached file and return the updated file by e-mail to the CTRO at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes, as appropriate. If there are no changes, please respond to this email confirming that the report accurately reflects your trial. Please include the NCI Trial ID, as above, in all correspondence. If a response is NOT received within five (5) business days, CTRO will conclude that the information contained in this Trial Summary Report is acceptable.

We are also including as an attachment an XML file ${fileName2} that may be suitable for posting to ClinicalTrials.gov. This file contains a subset of the information contained within the Trial Summary Report. The XML file requires independent validation and submission.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where identifier=20 and name='tsr.amend.body';

-- Letter12

insert into pa_properties(IDENTIFIER,name,value) VALUES(46,'noxml.tsr.subject',
'NCI Clinical Trials Reporting Program (CTRP): Trial Summary Report (TSR) Ready for Review, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}');

insert into pa_properties(IDENTIFIER,name,value) VALUES(47,'noxml.tsr.body',
'Date: ${CurrentDate}

Dear ${SubmitterName},

The following trial has been processed by the NCI Clinical Trials Reporting Office (CTRO):

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   

Title: ${trialTitle}

Submission Date: ${receiptDate}.

Please review the attached Trial Summary Report (${fileName}), including all data fields, to ensure that they accurately reflect the trial. Please mark any changes that are required in the attached file and return the updated file by e-mail to the CTRO at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes, as appropriate. If there are no changes, please respond to this email confirming that the report accurately reflects your trial. Please include the NCI Trial ID, provided above, in all correspondence. If a response is NOT received within five (5) business days, CTRO will conclude that the information contained in this Trial Summary Report is acceptable.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).');

-- Letter13

update pa_properties set value='NCI Clinical Trials Reporting Program (CTRP): Trial Summary Report (TSR) and XML attached, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}' 
where identifier=39 and name='xml.subject';

update pa_properties set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

Per your request, attached please find the Trial Summary Report (TSR) and associated XML file for the following trial:

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   

Title: ${trialTitle}

Submission Date: ${receiptDate}.

The XML file attached, ${fileName}, may be suitable for posting to ClinicalTrials.gov. This file contains a subset of the information contained within the Trial Summary Report. The XML file requires independent validation and submission.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).'
where identifier=40 and name='xml.body';

-- Letter14

insert into pa_properties(IDENTIFIER,name,value) VALUES(48,'proprietarytrial.register.subject',
'NCI Clinical Trials Reporting Program (CTRP): Confirmation of Successful Trial Registration - ${subOrgTrialIdentifier}, ${nciTrialIdentifier}');

insert into pa_properties(IDENTIFIER,name,value) VALUES(49,'proprietarytrial.register.body',
'Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting your trial to the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP). You are receiving this message because you have successfully completed the registration of the following trial:

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   

Submitting Organization: ${subOrg}

Submitting Organization Trial Identifier: ${subOrgTrialIdentifier}

Title: ${trialTitle}.
 
Your trial has been assigned a unique NCI identification number for tracking that you will need to reference in future correspondence with the Clinical Trials Reporting Office (CTRO): 

NCI Trial Identifier ${nciTrialIdentifier}
 
Your trial will now be considered for acceptance by the CTRO. Within 2 business days you will receive an acceptance or rejection email with further information.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).');

-- Letter15

insert into pa_properties(IDENTIFIER,name,value) VALUES(50,'noxml.tsr.amend.subject',
'NCI Clinical Trials Reporting Program (CTRP): Amended Trial Summary Report (TSR) Ready for Review, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}');

insert into pa_properties(IDENTIFIER,name,value) VALUES(51,'noxml.tsr.amend.body',
'Date: ${CurrentDate}

Dear ${SubmitterName},

The amendment for the following trial has been processed by the NCI Clinical Trials Reporting Office (CTRO):

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   

Title: ${trialTitle}

Submission Date: ${receiptDate}

Amendment Number: ${amendmentNumber}

Amendment Date: ${amendmentDate}.

Please review the attached Trial Summary Report (${fileName}), including all data fields, to ensure that they accurately reflect the trial. Please mark any changes that are required in the attached file and return the updated file by e-mail to the CTRO at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes, as appropriate. If there are no changes, please respond to this email confirming that the report accurately reflects your trial. Please include the NCI Trial ID, as above, in all correspondence. If a response is NOT received within five (5) business days, CTRO will conclude that the information contained in this Trial Summary Report is acceptable.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).');
