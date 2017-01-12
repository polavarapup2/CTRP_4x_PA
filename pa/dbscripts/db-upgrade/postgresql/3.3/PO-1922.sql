UPDATE pa_properties  SET value ='Date: ${CurrentDate}

 Dear  ${SubmitterName},

 Your request for Administrator status for the organization, ${affliateOrgName}, in CTRP has been denied.
 The reason for the rejection was recorded as follows: 
       ${rejectReason}

 If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

 Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
 WHERE identifier =54