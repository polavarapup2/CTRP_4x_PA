insert into pa_properties(IDENTIFIER,name,value) VALUES(37,'trial.partial.register.subject',
'NCI Clinical Trials Reporting Program (CTRP) Draft Saving Acknowledgment');

insert into pa_properties(IDENTIFIER,name,value) VALUES(38,'trial.partial.register.body',
'Date: ${CurrentDate}

Dear ${SubmitterName},

You have saved submission draft for the following trial with the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP)

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}
Lead Organization: ${leadOrgName}
Title: ${trialTitle}.  

Your saved submission draft has been assigned a unique temporary identifier ${temporaryidentifier} for tracking purpose.

Please note that submission draft can be stored for maximum of 30 days and is not processed or accessed by CTRO staff till completed. Retrieval and completion of partial submissions is enabled via ‘Search Saved Draft’ option on the ‘Search Trials’ screen.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in NCI Clinical Trials Reporting Program (CTRP).');