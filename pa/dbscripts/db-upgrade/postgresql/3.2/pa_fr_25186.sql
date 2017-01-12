UPDATE PA_PROPERTIES SET VALUE = 'Dear ${SubmitterName},

The following trial was submitted for registration to the NCI Clinical Trials Reporting Office (CTRO): 

Lead Organization Trial Identifier ${leadOrgTrialId} 

This trial cannot be registered by the CTRO for the following reasons: ${reasoncode}

If you feel there has been an error in rejecting this trial registration, or if you have additional questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov 

Thank you for your participation in NCI Clinical Trials Reporting Program (CTRP).'
where IDENTIFIER='6' and name='rejection.body';


UPDATE PA_PROPERTIES SET VALUE = 'Date: ${CurrentDate}

Dear ${SubmitterName},

You have successfully registered the following trial with the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP):
Lead Organization Trial Identifier ${leadOrgTrialIdentifier}   
 
Your trial has been assigned a unique NCI identification number for tracking that you will need to reference in future correspondence with the Clinical Trials Reporting Office (CTRO): 
NCI Trial Identifier ${nciTrialIdentifier}
 
Your trial will now be processed by the staff of the CTRO. Upon completion, the CTRO will e-mail you a Trial Summary Report listing key data elements that have been abstracted from the trial protocol.  At that time, the CTRO will request that you review the Trial Summary Report for accuracy and respond with any changes that may be required. 
 
If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov
 
Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).'
where IDENTIFIER='9' and name='trial.register.body';


UPDATE PA_PROPERTIES SET VALUE = 'Date: ${CurrentDate}

Dear ${SubmitterName},

Your Trial, ${leadOrgTrialId}, ${trialTitle}, received by the NCI Clinical Trial Reporting Office (CTRO) on ${receiptDate} and assigned NCI Trial ID ${nciTrialID}, has been processed by the CTRO.

Please review the attached Trial Summary Report (${fileName}), including all data fields, to ensure that they accurately reflect the trial. Please mark any changes that are required in the attached file and return the updated file by e-mail to the CTRO at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes, as appropriate. If there are no changes, please respond to this email confirming that the report accurately reflects your trial. Please include the NCI Trial ID, as above, in all correspondence. If a response is NOT received within five (5) business days, CTRO will conclude that the information contained in this Trial Summary Report is acceptable. 

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).'
where IDENTIFIER='26' and name='tsr.proprietary.body';