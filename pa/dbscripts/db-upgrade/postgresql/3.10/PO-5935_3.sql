--PO-5935 : Adding status summary e-mail properties
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'ctgovsync.email.to', 'ctgovsyncreport@example.com');
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'ctgovsync.email.subject', 'Summary of CTRP trials sync with CTGOV on ${CurrentDate}');
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'ctgovsync.email.body', '<hr>
<p>Date: ${CurrentDate}</p>
<p><b>Summary of CTRP Sync with CTGOV:</b></p>
<table border="1">
<tr>
<td>Trial Type:</td>
<td>Total Submitted:</td>
<td>Successful Updates:</td>
<td>Failures:</td>
</tr>
<tr>
<td>Industrial</td>
<td>${totalSubmitted}</td>
<td>${successfulUpdates}</td>
<td>${failures}</td>
</tr>
</table>
<p>See the attached zip file for a full list and status of the trials updated from CTGOV during the last synchronization event.</p>');

