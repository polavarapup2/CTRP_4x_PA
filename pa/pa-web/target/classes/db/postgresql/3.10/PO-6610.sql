--Author   : Reshma Koganti
--Date     : 10/31/2013        
--Jira#    :     PO-6610 PA: Trial Verification Job Produced 9 emails to Same User - All Missing Data 

update pa_properties set value='<p>Date: ${CurrentDate}</p>
<p>Dear ${name},</p>
<p>The following trial(s) are due for Data Verification.</p>
<p>NCI requires that all trials data in CTRP be verified every ${n_value} months. The Data Verification due date is ${n_value} months following the latest of the following dates:</p>
 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;Abstraction verification date <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;Last Update submitted date<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;Last Verification date<br/>
<p>To perform the verification, please follow these steps:</p>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.&nbsp;Log into the CTRP Registration Application  <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.&nbsp;Search for the trial you wish to verify, review the TSR <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.&nbsp;If the data is correct, select [Verify Data] from the [Available Actions] dropdown list <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.&nbsp;Follow the instructions on the screen <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.&nbsp;If corrections are needed, please address accordingly <br/>' where 
name = 'verifyData.email.bodyHeader';