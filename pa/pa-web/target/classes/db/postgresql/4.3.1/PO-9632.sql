update pa_properties set value='<hr>
<p><b>Title: </b>${trialTitle}</p>
${trialIdentifiers}
${otherIdentifiersRow}
<table border="0">
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
<p><b>NEXT STEPS:</b><br>
The Clinical Trials Reporting Office (CTRO) staff is reviewing your trial to ensure that it meets all of the requirements for registration in the CTRP system.
</p>
<p>In the meantime, if you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>' where name='trial.register.body';

update pa_properties  set value='<hr>
<p><b>Title: </b>${trialTitle}</p>
${trialIdentifiers}
<table border="0">
<tr>
<td><b>Submitting Organization Trial ID:</b></td>
<td>${subOrgTrialIdentifier}</td>
</tr>
<tr>
<td><b>Submitting Organization:</b></td>
<td>${subOrg}</td>
</tr>
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
<p><b>NEXT STEPS:</b><br>
The Clinical Trials Reporting Office (CTRO) staff is reviewing your trial to ensure that it meets all of the requirements for registration in the CTRP system.
</p>
<p>In the meantime, if you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>' where name='proprietarytrial.register.body';

update pa_properties  set value='<hr>
<p><b>Title: </b>${trialTitle}</p>
${trialIdentifiers}
<table border="0">
<tr>
<td><b>Submitting Organization Trial ID:</b></td>
<td>${subOrgTrialIdentifier}</td>
</tr>
<tr>
<td><b>Submitting Organization:</b></td>
<td>${subOrg}</td>
</tr>
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
of this indicator to "No" for this trial, and has notified the CTRO of this action. The CTRO may contact the trial�s point of contact for additional information before 
setting the value of the indicator to "Yes".</p>
<p><b>NEXT STEPS:</b><br>
The Clinical Trials Reporting Office (CTRO) staff is reviewing your trial to ensure that it meets all of the requirements for registration in the CTRP system.
</p>
<p>In the meantime, if you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>' where name='proprietarytrial.service.create.register.body';

update pa_properties  set value='<hr>
<p><b>Title: </b>${trialTitle}</p>
${trialIdentifiers}
${otherIdentifiersRow}
<table border="0">
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
of this indicator to "No" for this trial, and has notified the CTRO of this action. The CTRO may contact the trial�s point of contact for additional information before 
setting the value of the indicator to "Yes".</p>
<p><b>NEXT STEPS:</b><br>
The Clinical Trials Reporting Office (CTRO) staff is reviewing your trial to ensure that it meets all of the requirements for registration in the CTRP system.
</p>
<p>In the meantime, if you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>' where name='trial.service.create.register.body';
