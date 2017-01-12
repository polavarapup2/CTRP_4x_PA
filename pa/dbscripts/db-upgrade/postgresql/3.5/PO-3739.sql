update pa_properties set value = 'Date: ${CurrentDate}

Dear ${SubmitterName},

We have received the amendment you submitted for:

NCI Trial Identifier: ${nciTrialIdentifier}

Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}  

Lead Organization: ${leadOrgName}   

Title: ${trialTitle}.

Amendment Information 
	Amendment Number: ${amendmentNumber}

	Amendment Date:	${amendmentDate}.
	
Your amendment will now be considered for acceptance by the CTRO. Within 2 business days you will receive an acceptance or rejection email with further information.

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov

Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).'
where name='trial.amend.body';