UPDATE pa_properties SET value = 
'Date: ${CurrentDate}

 Dear  ${SubmitterName},

 Your request for Administrator status for the ${affliateOrgName} organization in CTRP was granted.

 User ${SubmitterName} can access all trials for the ${affliateOrgName}  organization, review and update access to trials owned by the organization and assign new administrator status to other users from the organization. 

 If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

 Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
WHERE name = 'trial.admin.accept.body';

UPDATE pa_properties SET value = 
'Date: ${CurrentDate}

 Dear  ${SubmitterName},

 Your request for Administrator status for the ${affliateOrgName} organization in CTRP was denied.
 
 If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

 Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
WHERE name = 'trial.admin.reject.body';

UPDATE pa_properties set value='Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for initiating the submission of your trial with the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP). You are receiving this message because you saved a draft prior to completing the submission of the following trial:

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}

Lead Organization: ${leadOrgName}

Title: ${trialTitle}.

The draft of the submission that you saved has been assigned a unique temporary identifier ${temporaryidentifier} for tracking purposes.

Please note that the draft of your submission can be stored for a maximum of 30 days and is not processed or accessed by the CTRP Clinical Trials Reporting Office (CTRO) staff until it is completed. For retrieving and completing a draft submission, select the ''Search Saved Draft'' option on the ''Search Trials'' screen.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).' 
WHERE identifier=38 and name='trial.partial.register.body';

UPDATE pa_properties set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting your trial for registration in the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP). It has been accepted for registration. The NCI Clinical Trials Reporting Office (CTRO) will now process the following trial:

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   

Title: ${title}.

If your trial is non proprietary processing by CTRO will include abstraction of the protocol document to produce a CTRP record. Processing by the CTRO is expected to take no more than ten (10) business days*. The CTRO will email you a Trial Summary Report (TSR) for review once processing is complete. Attached to that email will be a file with data in a format (XML) that may be suitable for posting to ClinicalTrials.gov (if such file is requered). The XML file requires independent validation and submission. Upon receipt of the TSR and XML file, you will have five (5) business days to review the TSR and XML file and inform the CTRO of required changes.

If your trial is proprietary and you did not submit a protocol document, you will receive a TSR for review once processing is complete.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).

* The NCI Clinical Trials Reporting Office (CTRO) aims to process and return a Trial Summary Report (TSR) and XML file for all non-proprietary protocols within a ten (10) day window. This ten day timeframe begins after a complete submission, and therefore availability of the submitter to resolve any discrepancies is critical, e.g. missing documentation, regulatory information, etc. Additionally, the potential variability of submission volume at any given time and/or complexity of a protocol can impact the processing time. If protocol submission volume exceeds the CTRO''s capacity for processing, the CTRO will prioritize submissions based on submitter need. If a CTRP registrant requires expedited processing of a protocol submission please contact the CTRO, reference the NCI CTRP provided ID, and request priority processing.'
WHERE identifier=22 and name='trial.accept.body';

UPDATE  pa_properties set value = 
'Date: ${CurrentDate}

Dear ${SubmitterName},

You have saved submission draft for the following trial with the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP)

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}
Lead Organization: ${leadOrgName}
Title: ${trialTitle}.  

Your saved submission draft has been assigned a unique temporary identifier ${temporaryidentifier} for tracking purpose.

Please note that submission draft can be stored for maximum of 30 days and is not processed or accessed by CTRO staff until completed. Retrieval and completion of partial submissions is enabled via ''Search Saved Draft'' option on the ''Search Trials'' screen.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in NCI Clinical Trials Reporting Program (CTRP).'
WHERE name = 'trial.partial.register.body';

