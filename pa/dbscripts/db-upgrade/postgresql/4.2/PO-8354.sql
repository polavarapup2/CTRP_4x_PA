--Author   : Aprtim Khandalkar
--Date     : 01/02/2015    
--Jira#    : PO-8350 – Improve performance of PO-8130  
--Comments : Added email subject and body for the sync email to be sent mentioned in PO-8354

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'sync.email.subject',
'NCI CTRP: DISEASE/TERM SYNCHRONIZATION PROGRESS for the term ${ncitTerm};${preferredName} ');

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'sync.email.body',
'<hr>
<table>
<tr>
<td><b>NCIt ID:</b></td>
<td>${ncitTerm}</td>
</tr>
<tr>
<td><b>Preferred Name:</b></td>
<td>${preferredName}</td>
</tr>
<tr>
<td><b>Display Name:</b></td>
<td>${displayName}</td>
</tr>
<tr>
<td><b>Submission Date:</b></td>
<td>${submissionDate}</td>
</tr>
</table>
<hr>
<p>
Date: ${submissionDate}
<p>
Dear ${user},
<p>
You have successfully synchronized the disease/condition term ${ncitTerm};${preferredName} in the NCI Thesaurus (NCIt) with the CTRP database.
<p>
<b>NEXT STEPS:</b>
<br>
If the system encountered errors during synchronization, contact the Clinical Trials Reporting Office (CTRO) staff at ncictro@mail.nih.gov. Be sure to include the NCIt IDs in your message.
<p>
In the meantime, if you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.
<p>
Thank you for submitting your term for synchronization in the Clinical Trials Reporting Program. 

');




