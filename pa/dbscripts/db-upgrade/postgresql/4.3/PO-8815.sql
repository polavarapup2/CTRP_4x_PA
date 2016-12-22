insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'trial.batchUpload.reportCreateMsg','<p><b>Note:</b> The number of submissions that failed can be misleading because it may include a number of empty lines in the file you submitted.</p>
<p>The progress report attached to this email contains the following information:
<ul>
<li>Loading status for each trial record in your data file
<li>Record identifier captured from the first column in the data file 
<li>NCI trial identifier  for each trial successfully recorded
<li>Error message for each record that failed registration
</ul>
</p>
<p><b>Note:</b> If the CTRP system cannot process your files, you will not receive a progress report, and the Summary of Submission Report will contain error messages.</p> 
<p><b>WARNING:</b> The file submitted contains one or more trial where the Delayed Posting Indicator value is set to "Yes", see the list below. Please note that as of ${changeDate}, 
the CTRP application no longer supports the capability of setting, or modifying, the value of this indicator by the submitter. This capability is now restricted to the CTRO. 
If you wish to modify the value of this indicator in the future please contact the CTRO at the email listed below. The application has defaulted the value of this indicator 
to "No" for these trials, and has notified the CTRO of this action on your behalf. The CTRO may contact you for additional information before setting the value of the indicator 
for these trials to "Yes".</p><br>
${tableRows}
<p><b>NEXT STEPS:</b><br>
If you have received error messages, check your trial data file to ensure that you have used valid values. Validate the trial information using the current specification.
</p>');

insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'trial.batchUpload.reporAmendtMsg','<p><b>Note:</b> The number of submissions that failed can be misleading because it may include a number of empty lines in the file you submitted.</p>
<p>The progress report attached to this email contains the following information:
<ul>
<li>Loading status for each trial record in your data file
<li>Record identifier captured from the first column in the data file 
<li>NCI trial identifier  for each trial successfully recorded
<li>Error message for each record that failed registration
</ul>
</p>
<p><b>Note:</b> If the CTRP system cannot process your files, you will not receive a progress report, and the Summary of Submission Report will contain error messages.</p> 
<p><b>WARNING:</b> The file submitted contains amendments for one or more trial where the value of the Delayed Posting Indicator is different than that stored in CTRP
 for that trial. See the list of trials below. Please note that as of ${changeDate}, the CTRP application no longer supports the capability of setting, or modifying, 
the value of this indicator by the submitter. This capability is now restricted to the CTRO. If you wish to modify the value of this indicator in the future please contact 
the CTRO at the email listed below. The application has NOT changed the value of this Indicator for these trials. The application has notified the CTRO of this action on your behalf. 
The CTRO may contact you for additional information before modifying the value of the indicator for these trials</p><br>
${tableRows}
<p><b>NEXT STEPS:</b><br>
If you have received error messages, check your trial data file to ensure that you have used valid values. Validate the trial information using the current specification.
</p>');


insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'trial.batchUpload.reporBothtMsg','<p><b>Note:</b> The number of submissions that failed can be misleading because it may include a number of empty lines in the file you submitted.</p>
<p>The progress report attached to this email contains the following information:
<ul>
<li>Loading status for each trial record in your data file
<li>Record identifier captured from the first column in the data file 
<li>NCI trial identifier  for each trial successfully recorded
<li>Error message for each record that failed registration
</ul>
</p>
<p><b>Note:</b> If the CTRP system cannot process your files, you will not receive a progress report, and the Summary of Submission Report will contain error messages.</p> 
<p><b>WARNING:</b> The file submitted contains one or more trial where the Delayed Posting Indicator value is set to "Yes", see the list below. Please note that as of ${changeDate}, 
the CTRP application no longer supports the capability of setting, or modifying, the value of this indicator by the submitter. This capability is now restricted to the CTRO. 
If you wish to modify the value of this indicator in the future please contact the CTRO at the email listed below. The application has defaulted the value of this indicator 
to "No" for these trials, and has notified the CTRO of this action on your behalf. The CTRO may contact you for additional information before setting the value of the indicator 
for these trials to "Yes".</p><br>
${createtableRows}
<br>
<p><b>WARNING:</b> The file submitted contains amendments for one or more trial where the value of the Delayed Posting Indicator is different than that stored in CTRP
 for that trial. See the list of trials below. Please note that as of ${changeDate}, the CTRP application no longer supports the capability of setting, or modifying, 
the value of this indicator by the submitter. This capability is now restricted to the CTRO. If you wish to modify the value of this indicator in the future please contact 
the CTRO at the email listed below. The application has NOT changed the value of this Indicator for these trials. The application has notified the CTRO of this action on your behalf. 
The CTRO may contact you for additional information before modifying the value of the indicator for these trials</p><br>
${amendtableRows}
<p><b>NEXT STEPS:</b><br>
If you have received error messages, check your trial data file to ensure that you have used valid values. Validate the trial information using the current specification.
</p>');


insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'trial.batchUpload.create.warning.subject','Delayed Posting Indicator set to "Yes" -${trial_ids}');
    
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'trial.batchUpload.create.warning.body','<p>Dear CTRO Staff,</p>
<p>The following trial(s) were submitted with the value of the Delayed Posting Indicator set to "Yes":</p>
${tableRows}
<p>As of ${changeDate}, the CTRP application no longer supports the capability of setting, or modifying, the value of this indicator by 
the submitter. This capability is now restricted to the CTRO. Therefore, the trial(s) was registered in 
CTRP with the value of the Delayed Posting Indicator set to "No".Please review and update the value of the indicator accordingly for these trials.<p>

<p>The following warning message was sent to the submitter:</p><br>
Submitter: ${submitter_name} <br>
Submitting Organization: ${submitting_organization_name} <br>
Submission Date: ${submission_date}<br>
<p><b>WARNING:</b> The file submitted contains one or more trial where the Delayed Posting Indicator value is set to "Yes", see the list below. Please note that as of ${changeDate}, 
the CTRP application no longer supports the capability of setting, or modifying, the value of this indicator by the submitter. This capability is now restricted to the CTRO. 
If you wish to modify the value of this indicator in the future please contact the CTRO at the email listed below. The application has defaulted the value of this indicator 
to "No" for these trials, and has notified the CTRO of this action on your behalf. The CTRO may contact you for additional information before setting the value of the indicator 
for these trials to "Yes".</p><br>
${tableRows}
<p>Thank you,<p>
<p>This is an automatically generated message. Please do not reply.</p>');


insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'trial.batchUpload.amend.warning.subject','Delayed Posting Indicator value was modified -${trial_ids}');
    
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'trial.batchUpload.amend.warning.body','<p>Dear CTRO Staff,</p>
<p>An amendment was submitted for the following trial(s) where the value of the Delayed Posting Indicator was changed:</p>
${tableRows}
<p>As of ${changeDate}, the CTRP application no longer supports the capability of setting, or modifying, the value of this indicator by the submitter. This capability 
is now restricted to the CTRO. Therefore, the amendment(s) was loaded into CTRP WITHOUT modifying the value of the indicator. Please review and update the value of 
the indicator accordingly for these trials</p>
<p>The following warning message was sent to the submitter:</p><br>
Submitter: ${submitter_name} <br>
Submitting Organization: ${submitting_organization_name} <br>
Submission Date: ${submission_date}<br>
<p><b>WARNING:</b> The file submitted contains amendments for one or more trial where the value of the Delayed Posting Indicator is different than that stored in CTRP for
 that trial. See the list of trials below. Please note that as of ${changeDate}, the CTRP application no longer supports the capability of setting, or modifying, the value 
of this indicator by the submitter. This capability is now restricted to the CTRO. If you wish to modify the value of this indicator in the future please contact the CTRO at the 
email listed below. The application has NOT changed the value of this Indicator for these trials. The application has notified the CTRO of this action on your behalf. The CTRO may 
contact you for additional information before modifying the value of the indicator for these trials</p><br>
${tableRows}
<br>
<p>Thank you,<p><br>
<p>This is an automatically generated message. Please do not reply.</p>'); 
    
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'delayed.posting.change.date','September 2015');   

insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'trial.reg.service.amend.warning.body','<hr>
<p><b>Title: </b>${trialTitle}</p>
<table border="0">
<tr>
<td><b>Lead Organization Trial ID:</b></td>
<td>${leadOrgTrialIdentifier}</td>
</tr>
<tr>
<td><b>Lead Organization:</b></td>
<td>${leadOrgName}</td>
</tr>
<tr>
<td><b>CTRP-assigned Lead Organization ID:</b></td>
<td>${leadOrgID}</td>
</tr>
<tr>
<td><b>NCI Trial ID:</b></td>
<td>${nciTrialIdentifier}</td>
</tr>
${nctIdentifierRow}
${ctepIdentifierRow}
${dcpIdentifierRow}
${otherIdentifiersRow}
<tr>
<td><b>Submission Date:</b></td>
<td>${submissionDate}</td>
</tr>
</table>    
<hr>
<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>The Clinical Trials Reporting Office (CTRO) has received the amendment you submitted for the trial identified above.</p>
<p><b>Amendment Information:</b>
<UL>
<LI><b>Amendment Number:</b> ${amendmentNumber}
<LI><b>Amendment Date:</b> ${amendmentDate}
</UL>
</p>
<p><b>WARNING:</b>The amendment submitted has a Delayed Posting Indicator value different than that stored in CTRP.  Please note that as of ${changeDate}, the CTRP application no longer 
supports the capability of setting, or modifying, the value of this indicator by the submitter. This capability is now restricted to the CTRO. The application did NOT changed the indicator''s 
value for this trial, and has notified the CTRO of this action. The CTRO may contact the trial''s point of contact for additional information before modifying the value of the indicator.</p>
<p><b>NEXT STEPS:</b><br>
The Clinical Trials Reporting Office (CTRO) staff is reviewing the amended information to ensure that it meets all of the requirements for registration in the CTRP system. The CTRO will send you a 
separate email that indicates whether they have accepted or rejected your trial within two (2) business days.
</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>');  
    
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'trial.service.amend.subject','Delayed Posting Indicator value was modified - ${tableRows}');   
    
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'trial.service.amend.body','<p>Dear CTRO Staff,</p>
<p>An amendment for the trial below was submitted where the value of the Delayed Posting Indicator was changed:<p>
${tableRows}
<p>As of ${changeDate}, the CTRP application no longer supports the capability of setting, or modifying, the value of this indicator by the submitter. 
This capability is now restricted to the CTRO. The amendment was loaded into CTRP WITHOUT modifying the value of the indicator. Please review and update 
the value of the indicator accordingly.</p>

<p>The following warning message was sent to the submitter:</p>
Submitter: ${submitter_name} <br>
Submitting Organization: ${submitting_organization_name} <br>
Submission Date: ${submission_date}<br>
<p>WARNING: The amendment submitted has a Delayed Posting Indicator value different than that stored in CTRP.  Please note that as of ${changeDate}, the CTRP application no longer supports 
the capability of setting, or modifying, the value of this indicator by the submitter. This capability is now restricted to the CTRO. The application did NOT changed the indicator''s value 
for this trial, and has notified the CTRO of this action. The CTRO may contact the trial''s point of contact for additional information before modifying the value of the indicator.</p>

<p>Thank you,</p>
<p>This is an automatically generated message. Please do not reply.</p>');
    
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'proprietarytrial.service.create.register.body','<hr>
<p><b>Title: </b>${trialTitle}</p>
<table border="0">
<tr>
<td><b>Lead Organization Trial ID:</b></td>
<td>${leadOrgTrialIdentifier}</td>
</tr>
<tr>
<td><b>Lead Organization:</b></td>
<td>${leadOrgName}</td>
</tr>
<tr>
<td><b>CTRP-assigned Lead Organization ID:</b></td>
<td>${leadOrgID}</td>
</tr>
<tr>
<td><b>Submitting Organization Trial ID:</b></td>
<td>${subOrgTrialIdentifier}</td>
</tr>
<tr>
<td><b>Submitting Organization:</b></td>
<td>${subOrg}</td>
</tr>
<tr>
<td><b>NCI Trial ID:</b></td>
<td>${nciTrialIdentifier}</td>
</tr>
${nctIdentifierRow}
<tr>
<td><b>Submission Date:</b></td>
<td>${submissionDate}</td>
</tr>
</table>    
<hr>
<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>You have successfully created a record in the NCI Clinical Trials Reporting Program (CTRP) for the trial identified above.</p>
<p>The CTRP has assigned your trial the following unique NCI Trial Identification (Trial ID) number:<br>
<b>${nciTrialIdentifier}</b><br></br><br></br>
Please reference this number in all future correspondence with the Clinical Trials Reporting Office (CTRO).</p> 
<p>${errors}</p>
<p><b>WARNING:</b> The trial submitted has a Delayed Posting Indicator value of "Yes". Please note that as of ${changeDate}, the CTRP application no longer supports the 
capability of setting, or modifying, the value of this indicator by the submitter. This capability is now restricted to the CTRO. The application has defaulted the value 
of this indicator to "No" for this trial, and has notified the CTRO of this action. The CTRO may contact the trial’s point of contact for additional information before 
setting the value of the indicator to "Yes".</p>
<p><b>NEXT STEPS:</b><br>
The Clinical Trials Reporting Office (CTRO) staff is reviewing your trial to ensure that it meets all of the requirements for registration in the CTRP system. They will email you their findings within two (2) business days.
</p>
<p>In the meantime, if you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>');  

insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'trial.service.create.register.body','<hr>
<p><b>Title: </b>${trialTitle}</p>
<table border="0">
<tr>
<td><b>Lead Organization Trial ID:</b></td>
<td>${leadOrgTrialIdentifier}</td>
</tr>
<tr>
<td><b>Lead Organization:</b></td>
<td>${leadOrgName}</td>
</tr>
<tr>
<td><b>CTRP-assigned Lead Organization ID:</b></td>
<td>${leadOrgID}</td>
</tr>
<tr>
<td><b>NCI Trial ID:</b></td>
<td>${nciTrialIdentifier}</td>
</tr>
${nctIdentifierRow}
${otherIdentifiersRow}
<tr>
<td><b>Submission Date:</b></td>
<td>${submissionDate}</td>
</tr>
</table>    
<hr>
<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>You have successfully created a record in the NCI Clinical Trials Reporting Program (CTRP) for the trial identified above.</p>
<p>The CTRP has assigned your trial the following unique NCI Trial Identification (Trial ID) number:<br>
<b>${nciTrialIdentifier}</b><br></br><br></br>
Please reference this number in all future correspondence with the Clinical Trials Reporting Office (CTRO).</p> 
<p>${errors}</p>
<p><b>WARNING:</b> The trial submitted has a Delayed Posting Indicator value of "Yes". Please note that as of ${changeDate}, the CTRP application no longer supports the 
capability of setting, or modifying, the value of this indicator by the submitter. This capability is now restricted to the CTRO. The application has defaulted the value 
of this indicator to "No" for this trial, and has notified the CTRO of this action. The CTRO may contact the trial’s point of contact for additional information before 
setting the value of the indicator to "Yes".</p>
<p><b>NEXT STEPS:</b><br>
The Clinical Trials Reporting Office (CTRO) staff is reviewing your trial to ensure that it meets all of the requirements for registration in the CTRP system. They will email you their findings within two (2) business days.
</p>
<p>In the meantime, if you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>');

insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'trial.service.create.subject','Delayed Posting Indicator set to "Yes" - ${tableRows}');   
    
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'trial.service.create.body','<p>Dear CTRO Staff,</p>
<p>The trial below was submitted with the value for the Delayed Posting Indicator set to "Yes":<p>
${tableRows}
<p>As of ${changeDate}, the CTRP application no longer supports the capability of setting, or modifying, the value of this indicator by the submitter. This capability is now restricted to the 
CTRO. Therefore, the trial was registered in CTRP with the value of the Delayed Posting Indicator set to "No".Please review and update the value of the indicator accordingly this trial.</p>

<p>The following warning message was sent to the submitter:</p>
Submitter: ${submitter_name} <br>
Submitting Organization: ${submitting_organization_name} <br>
Submission Date: ${submission_date}<br>
<p><b>WARNING:</b> The trial submitted has a Delayed Posting Indicator value of "Yes". Please note that as of ${changeDate}, the CTRP application no longer supports the 
capability of setting, or modifying, the value of this indicator by the submitter. This capability is now restricted to the CTRO. The application has defaulted the value 
of this indicator to "No" for this trial, and has notified the CTRO of this action. The CTRO may contact the trial’s point of contact for additional information before 
setting the value of the indicator to "Yes".</p>
<p>Thank you,</p>
<p>This is an automatically generated message. Please do not reply.</p>');




  