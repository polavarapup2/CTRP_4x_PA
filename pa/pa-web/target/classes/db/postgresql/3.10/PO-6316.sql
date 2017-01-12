UPDATE pa_properties SET value = '<hr>
<table border="0">
<tr>
<td><b>NCI Trial ID:</b></td>
<td>${trialIdentifier}</td>
</tr>
</table>    
<hr>
<p>Date: ${CurrentDate}</p>
<p>The Clinical Trials Reporting Office (CTRO) requires a new permissible value to complete marker abstraction for trial ${trialIdentifier}.</p>
<p>
<b>Concept:</b> ${markerName}<br>
<b>Found in HUGO ?:</b> ${foundInHugo}<br>
${hugoCodeClause}
${markerTextClause}
<b>Submitter:</b> ${submitterName}<br>
</p>
<p>If you need any further information to create the new permissible value, contact the CTRO at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>'
WHERE name = 'CDE_MARKER_REQUEST_BODY';