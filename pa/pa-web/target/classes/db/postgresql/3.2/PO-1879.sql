INSERT INTO pa_properties (identifier, name, value)
            VALUES (52, 'trial.admin.accept.subject' ,
'Administration status request');

INSERT INTO pa_properties (identifier, name, value)
            VALUES (53, 'trial.admin.accept.body' ,
'Date: ${CurrentDate}

 Dear  ${SubmitterName},

 Administration status for the organization ${affliateOrgName} in CTRP had been granted.

 User ${SubmitterName} can access all trials for the ${affliateOrgName}  organization, review and update access to trials owned by the organization and assign new administrator status to other users from the organization. 

 If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

 Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).');


 INSERT INTO pa_properties (identifier, name, value)
            VALUES (54, 'trial.admin.reject.body' ,
'Date: ${CurrentDate}

 Dear  ${SubmitterName},

 Administration status for the ${affliateOrgName} organization in CTRP had been denied.
 
 If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

 Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).');