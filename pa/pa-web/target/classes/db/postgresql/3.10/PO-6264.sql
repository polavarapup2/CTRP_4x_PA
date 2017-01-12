--Author   : Reshma Koganti
--Date     : 07/12/2013    
--Jira#    : PO-6264 SATV-3-2-001 – Submit automatic email reminders to open trial record owners, submitter, 
--and site administrator to verify their trial data in CTRP
--Comments : updating the Mail content, date and n values in pa_properties 

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'N_value','12');
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'verifyData.email.subject','CTRP Trials Due for Data Verification');
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'verifyData.email.bodyHeader',
'<p>Date: ${CurrentDate}</p>
<p>Dear ${name},</p>
<p>The following trial(s) are due for Data Verification.</p>
<p>NCI requires that all trials data in CTRP be verified every [n] months. The Data Verification due date is [n] months following the latest of the following dates:</p>
 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;Abstraction verification date <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;Last Update submitted date<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;Last Verification date<br/>
<p>To perform the verification, please follow these steps:</p>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.&nbsp;Log into the CTRP Registration Application  <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.&nbsp;Search for the trial you wish to verify, review the TSR <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.&nbsp;If the data is correct, select [Verify Data] from the [Available Actions] dropdown list <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.&nbsp;Follow the instructions on the screen <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.&nbsp;If corrections are needed, please address accordingly <br/>');

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'verifyData.email.body',
'
<table border="0">
<tr>
  <td style="width:60%"><b>NCI ID</b></td>
  <td><b>Verification Due Date</b></td>
</tr>
</table> 
<hr>
<table border="0">
 ${tableRows}
</table>');
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'verifyData.email.bodyFooter',
'<p>If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov</p>
<p>Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).</p>');
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties),'TrialDataVerificationNotificationsEffectiveDate','31-DEC-2050');


