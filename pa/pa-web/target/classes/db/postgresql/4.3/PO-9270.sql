UPDATE pa_properties set value='<hr>
<p><b>Title: </b>${trialTitle}</p>
<table border="0">
<tr>
<td><b>Lead Organization Trial ID:</b></td>
<td>${leadOrgTrialIdentifier}</td>
</tr>
<tr>
<td><b>Lead Organization:</b></td>
<td>${leadOrgName}</td>
</tr>
<tr>
<td><b>Submission Date:</b></td>
<td>${submissionDate}</td>
</tr>
</table>    
<hr>
<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>You have saved a draft of the trial identified above for submission to the NCI Clinical Trials Reporting Program (CTRP).</p>
<p>The CTRP has assigned your draft a unique temporary identifier for tracking purposes.</p> 
<p><b>NEXT STEPS:</b><br>
To retrieve and complete your submission, use the "Search Saved Drafts" feature on the "Search Trials" page in the CTRP Registration application.
</p>
<p>Clinical Trials Reporting Office (CTRO) staff will not access or process your trial until you have completed the submission.</p>
<p><b>Important!</b> You can save your draft  for a maximum of 30 days.</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>' where name='trial.partial.register.body';