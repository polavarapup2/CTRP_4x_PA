update pa_properties 

set value = '${CurrentDate}

Dear ${SubmitterName}

You have successfully registered the following trial with the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP):
Lead Organization Trial Identifier ${leadOrgTrialIdentifier}   
 
Your trial has been assigned a unique NCI identification number for tracking that you will need to reference in future correspondence with the Clinical Trials Reporting Office (CTRO): 
NCI Trial Identifier ${nciTrialIdentifier}
 
Your trial will now be processed by the staff of the CTRO. Upon completion, the CTRO will e-mail you a Trial Summary Report listing key data elements that have been abstracted from the trial protocol.  At that time, the CTRO will request that you review the Trial Summary Report for accuracy and respond with any changes that may be required. 
 
If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov
 
Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).'

where IDENTIFIER = 9;
