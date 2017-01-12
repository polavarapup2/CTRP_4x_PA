update pa_properties 

set value = 'Date: ${CurrentDate}

 Dear: ${SubmitterName},
 
 The update for NCI Trial Identifier ${nciTrialIdentifier} has been submitted.
 
 If you have questions on this or other CTRP topic please contact us at ncictro@mail.nih.gov
 
 Thank you for submitting update to the trial.'

 where IDENTIFIER = 24;



