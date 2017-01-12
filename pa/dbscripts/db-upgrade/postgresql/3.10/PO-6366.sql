--Accrual Batch Upload Exception Report email template changes

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'accrual.exception.subject', 'NCI CTRP: Accrual SUBMISSION ERRORS for BATCH FILE ${fileName}');

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'accrual.exception.body', 
'<hr>
<table border="0">
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
<p>Thank you for submitting your accruals to the NCI Clinical Trials Reporting Program (CTRP).</p>
<p>Unfortunately, the CTRP system was not able to read the accrual batch file because the file was formatted incorrectly.</p>
<p><b>NEXT STEPS:</b><br>Please correct the errors identified above, and re-submit your batch file.</p> 
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>');