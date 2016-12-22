alter TABLE patient_stage alter column birth_date type timestamp using birth_date :: timestamp;
alter TABLE patient_stage alter column registration_date type timestamp using registration_date :: timestamp;
alter table patient_stage add column site_disease_code varchar(200);


INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'accrualjob.email.subject', 'NCI Clinical Trials Reporting Program (CTRP): Accrual Submission Summary');
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'accrualjob.email.body', 
'<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p><b>Summary of Accrual Submission:</b></p>
<p>The following trial(s) were re-tried by the automated queue processing and failed again due to the fact that these sites have not yet been added to the given trials.</p>
<table border="1">
<tr>
  <td><b>NCI ID</b></td>
  <td><b>Missing Participating Sites</b></td>
</tr> 
${tableRows}
</table>
<p>Thank you.</p> <p>This is an automatically generated email message.</p>');


INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'deletePendingAccruals.email.subject', 'NCI Clinical Trials Reporting Program (CTRP): Accrual Deleted Sites Summary');
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'deletePendingAccruals.email.body', 
'<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p><b>Summary of Accrual Deleted Sites:</b></p>
<p>For the following trial(s) these sites have been permanently removed from the automated queue and will not be re-tried. If the user wants to re-submit those sites then please use the original batch file.</p>
<table border="1">
<tr>
  <td><b>NCI ID</b></td>
  <td><b>Deleted Participating Site</b></td>
  <td><b>FileName</b></td>
</tr>
 ${tableRows}
</table>
<p>Thank you.</p> <p>This is an automatically generated email message.</p>');