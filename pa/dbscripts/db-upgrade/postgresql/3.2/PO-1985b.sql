UPDATE pa_properties SET value = 
'Date: ${CurrentDate}

 Dear  ${SubmitterName},

 Your request for Administrator status for the organization, ${affliateOrgName}, in CTRP has been granted.

 User ${SubmitterName} can access all trials for the ${affliateOrgName}  organization, review and update access to trials owned by the organization and assign new administrator status to other users from the organization. 

 If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

 Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
WHERE name = 'trial.admin.accept.body';

UPDATE pa_properties SET value = 
'Date: ${CurrentDate}

 Dear  ${SubmitterName},

 Your request for Administrator status for the organization, ${affliateOrgName}, in CTRP has been denied.
 
 If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

 Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
WHERE name = 'trial.admin.reject.body';