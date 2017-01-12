--PO-6244 : Notify the DCP PIO when a study’s NCT ID value changes. 


insert into pa_properties VALUES ((select max(identifier) + 1 from pa_properties),'dcp.user','DCP_DCIM2');

insert into pa_properties VALUES ((select max(identifier) + 1 from pa_properties),'nct.change.notification.subject','NCI CTRP: NCT ID CHANGED for ${nciTrialIdentifier}, ${dcpTrialIdentifier}');

insert into pa_properties VALUES ((select max(identifier) + 1 from pa_properties),'nct.change.notification.body',
'<hr>
<p><b>Title:</b>${trialTitle}</p>
    
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
<td><b>NCI Trial ID:</b></td>
<td>${nciTrialIdentifier}</td>
</tr>
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
<p>The ClinicalTrials.gov Identifier (NCT ID) for the NCI Clinical Trials Reporting Program (CTRP) trial identified above has changed.</p>
<table border="0">
<tr>
<td><b>Previous NCT ID:</b></td>
<td>${previousNCTIdentifier}</td>
</tr>
<tr>
<td><b>New NCT ID:</b></td>
<td>${newNCTIdentifier}</td>
</tr>
</table>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for your participation in the NCI Clinical Trials Reporting Program.</p>'
);
