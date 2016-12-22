-- Letter 5 body
UPDATE pa_properties SET value =
'Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting your trial for registration in the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP). It has been accepted for registration. The NCI Clinical Trials Reporting Office (CTRO) will now process the following trial:

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}

Title: ${title}.

If your trial is non proprietary processing by CTRO will include abstraction of the protocol document to produce a CTRP record. Processing by the CTRO is expected to take no more than ten (10) business days*. The CTRO will email you a Trial Summary Report (TSR) for review once processing is complete. Attached to that email will be a file with data in a format (XML) that may be suitable for posting to ClinicalTrials.gov (if such file is required). The XML file requires independent validation and submission. Upon receipt of the TSR and XML file, you will have five (5) business days to review the TSR and XML file and inform the CTRO of required changes.

If your trial is proprietary and you did not submit a protocol document, you will receive a TSR for review once processing is complete.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).

* The NCI Clinical Trials Reporting Office (CTRO) aims to process and return a Trial Summary Report (TSR) and XML file for all non-proprietary protocols within a ten (10) day window. This ten day timeframe begins after a complete submission, and therefore availability of the submitter to resolve any discrepancies is critical, e.g. missing documentation, regulatory information, etc. Additionally, the potential variability of submission volume at any given time and/or complexity of a protocol can impact the processing time. If protocol submission volume exceeds the CTRO''s capacity for processing, the CTRO will prioritize submissions based on submitter need. If a CTRP registrant requires expedited processing of a protocol submission please contact the CTRO, reference the NCI CTRP provided ID, and request priority processing.'
WHERE name = 'trial.accept.body';

-- Letter 6 body
UPDATE pa_properties SET value =
'Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting your trial amendment to the NCI Clinical Trials Reporting Office (CTRO). The amendment to the following trial has been accepted for processing:

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}

Title: ${title}

Amendment Number: ${amendmentNumber}

Amendment Date: ${amendmentDate}.

The amendment that you submitted will be abstracted by the CTRO staff to update your record in CTRP. When abstraction is complete, you will be sent a new Trial Summary Report that reflects the changes indicated in the trial amendment. Attached to that email will be a file with data in a format (XML) that may be suitable for posting to ClinicalTrials.gov (if such file is required). The XML file requires independent validation and submission.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
WHERE name = 'trial.amend.accept.body';

-- Letter 7 body
UPDATE pa_properties SET value =
'Date: ${CurrentDate}

Dear ${SubmitterName},

The following trial was submitted to the NCI Clinical Trials Reporting Office (CTRO) for registration in NCI Clinical Trials Reporting Program:

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}

Title: ${trialTitle}

Submission Date: ${receiptDate}.

We regret to inform you that this trial cannot be registered by the CTRO for the following reasons: ${reasoncode}.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
WHERE name = 'rejection.body';

-- Letter 8 body
UPDATE pa_properties SET value =
'Date: ${CurrentDate}

Dear ${SubmitterName},

An amendment for the following trial currently listed in the NCI Clinical Trials Reporting Program (CTRP) system was submitted to the NCI Clinical Trials Reporting Office (CTRO):

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}

Title: ${title}

Submission Date: ${receiptDate}

Amendment Number: ${amendmentNumber}

Amendment Date: ${amendmentDate}.

This amendment cannot be accepted by the CTRO for the following reasons: ${reasonForRejection}.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
WHERE name = 'trial.amend.reject.body';
