update pa_properties set value =
'Date: ${CurrentDate}

Dear ${SubmitterName},

You are receiving this email because NCI CTRP received an update for:
* NCI Trial Identifier: ${nciTrialIdentifier}
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}   
* Title: ${trialTitle}.

The following updates were made to the trial:

${updates}

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'

where name = 'trial.update.body';

