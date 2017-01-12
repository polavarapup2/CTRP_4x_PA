update pa_properties set value ='<hr>
<table border="0">
<tr>
<td><b>NCI Trial ID:</b></td>
<td>${nciTrialIdentifier}</td>
</tr>
<tr>
<td><b>Batch File Name:</b></td>
<td>${fileName}</td>
</tr>
<tr>
<td><b>Submission Date:</b></td>
<td>${submissionDate}</td>
</tr>
</table>    
<hr>

<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>Thank you for submitting your accrual counts to the NCI Clinical Trials Reporting Program (CTRP).</p>
<p>Accrual counts for the following Study Site(s) were updated successfully as follows:<br> 
${studySiteCounts}
</p>
<p>${errorsDesc}</p>
<ul>${errors}</ul>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>' where name='accrual.industrialTrial.body';