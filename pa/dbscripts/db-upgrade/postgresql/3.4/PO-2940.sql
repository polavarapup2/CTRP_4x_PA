UPDATE pa_properties SET value =
'Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting your trial for registration in the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP). It has been accepted for registration. The NCI Clinical Trials Reporting Office (CTRO) will now process the following trial:

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}

Title: ${title}.

If your trial is complete processing by CTRO will include abstraction of the protocol document to produce a CTRP record. Processing by the CTRO is expected to take no more than ten (10) business days*. The CTRO will email you a Trial Summary Report (TSR) for review once processing is complete. Attached to that email will be a file with data in a format (XML) that may be suitable for posting to ClinicalTrials.gov (if such file is required). The XML file requires independent validation and submission. Upon receipt of the TSR and XML file, you will have five (5) business days to review the TSR and XML file and inform the CTRO of required changes.

If your trial is abbreviated and you did not submit a protocol document, you will receive a TSR for review once processing is complete.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).

* The NCI Clinical Trials Reporting Office (CTRO) aims to process and return a Trial Summary Report (TSR) and XML file for all abbreviated protocols within a ten (10) day window. This ten day timeframe begins after a complete submission, and therefore availability of the submitter to resolve any discrepancies is critical, e.g. missing documentation, regulatory information, etc. Additionally, the potential variability of submission volume at any given time and/or complexity of a protocol can impact the processing time. If protocol submission volume exceeds the CTRO''s capacity for processing, the CTRO will prioritize submissions based on submitter need. If a CTRP registrant requires expedited processing of a protocol submission please contact the CTRO, reference the NCI CTRP provided ID, and request priority processing.'
WHERE name = 'trial.accept.body';

update pa_properties set value='NCI Clinical Trials Reporting Program (CTRP): Abbreviated Trial Summary Report (TSR) Ready for Review, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}' 
where identifier=25 and name='tsr.proprietary.subject';