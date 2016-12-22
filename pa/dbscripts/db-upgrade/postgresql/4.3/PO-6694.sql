
UPDATE pa_properties SET value = '<p>Date: ${CurrentDate}</p>
<p>Dear ${name},</p>
<p>The following trial(s) are due for Data Verification.</p>' WHERE name ='verifyData.email.bodyHeader';

UPDATE pa_properties SET value = '<table border="0">
<tr>
  <td style="width:30%"><b>NCI ID</b></td>
  <td style="width:30%"><b>Lead Organization Trial ID</b></td>
  <td style="width:30%"><b>Verification Due Date</b></td>
</tr>
${tableRows}
</table> 
<hr>' WHERE name = 'verifyData.email.body';

UPDATE pa_properties SET value = '<p>NCI requires that all trials data in CTRP be verified every ${n_value} months. The Data Verification due date is ${n_value} months following the latest of the following dates:</p>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;Abstraction verification date <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;Last Update submitted date<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;Last Verification date<br/>
<p>To perform the verification, please follow these steps:</p>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.&nbsp;Log into the CTRP Registration Application  <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.&nbsp;Search for the trial you wish to verify, review the TSR <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.&nbsp;If the data is correct, select [Verify Data] from the [Available Actions] dropdown list <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.&nbsp;Follow the instructions on the screen <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.&nbsp;If corrections are needed, please address accordingly <br/>

<p>If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov</p>
<p>Thank you for your participation in the NCI Clinical Trials Reporting Program (CTRP).</p>' WHERE name = 'verifyData.email.bodyFooter';



