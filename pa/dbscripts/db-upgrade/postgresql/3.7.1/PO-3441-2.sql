update pa_properties set value='Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting your trial to the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP). You are receiving this message because you have successfully completed the registration of the following trial:

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   

Submitting Organization: ${subOrg}

Submitting Organization Trial Identifier: ${subOrgTrialIdentifier}

Title: ${trialTitle}.
 
Your trial has been assigned a unique NCI identification number for tracking that you will need to reference in future correspondence with the Clinical Trials Reporting Office (CTRO): 

NCI Trial Identifier ${nciTrialIdentifier}
${errors}
Your trial will now be considered for acceptance by the CTRO. Within 2 business days you will receive an acceptance or rejection email with further information.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).'
    where name='proprietarytrial.register.body';

    
update pa_properties set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

Thank you for submitting your trial to the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP). You are receiving this message because you have successfully completed the registration of the following trial:

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}

Lead Organization: ${leadOrgName}

Title: ${trialTitle}.
 
Your trial has been assigned a unique NCI identification number for tracking that you will need to reference in future correspondence with the Clinical Trials Reporting Office (CTRO): 

NCI Trial Identifier: ${nciTrialIdentifier}
${errors}
Your trial will now be considered for acceptance by the CTRO. Within 2 business days you will receive an acceptance or rejection email with further information.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).'
    where name='trial.register.body';
    

update pa_properties set value = '
Your trial has been registered successfully. However, the system was not able to properly assign trial record ownership. Some, or all, email address(es) provided do not belong to registered CTRP user(s):

{0} 
' where name='trial.register.unidentifiableOwner.sub.email.body';   
    