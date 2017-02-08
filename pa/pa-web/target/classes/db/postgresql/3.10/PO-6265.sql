--Author   : Reshma Koganti
--Date     : 07/16/2013    
--Jira#    : PO-6265 SATV-3-2-002 – Notify the CTRO via email of all trials that are seven days from their verification due dates  
--Comments : updating the mail content in pa_properties 

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'thresholdValue','7');
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'verifyDataCTRO.email.subject','CTRP Trials Due for Data Verification on ${dueDate}');
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'verifyDataCTRO.email.bodyHeader',
'<p>Date: ${CurrentDate}</p>
<p>Dear CTRO Staff,</p>
<p>The following trial(s) are due for Data Verification on ${dueDate}</p>');

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'verifyDataCTRO.email.body',
'
<table border="0">
<tr>
  <td style="width:60%"><b>NCI ID</b></td>
  <td><b>Lead Organization</b></td>
</tr>
</table> 
<hr>
<table border="0">
 ${tableRows}
</table>');
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'verifyDataCTRO.email.bodyFooter',
'<p>Thank you.</p> <p>This is an automatically generated email message.</p>');



