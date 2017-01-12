UPDATE PA_PROPERTIES SET VALUE = 'Date: ${CurrentDate}

Dear ${SubmitterName},

You have saved a draft of your submission for the following trial with the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP):
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}
* Lead Organization: ${leadOrgName}
* Title: ${trialTitle}.  

Your saved  draft has been assigned a unique temporary identifier for tracking purposes.

Important! You can save your draft  for a maximum of 30 days. To retrieve and complete your submission, use the "Search Saved Draft" feature on the "Search Trials" page in the CTRP Registration application.

Note that Clinical Trials Reporting Office (CTRO) staff will not access or process your trial  until you have completed the submission. 

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in the NCI Clinical Trials Reporting Program.' WHERE NAME = 'trial.partial.register.body';