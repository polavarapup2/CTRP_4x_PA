--PO-5961 : TSR report email with attachments

update pa_properties set value = 'NCI CTRP: Trial FILES ATTACHED for REVIEW for ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}' where name = 'tsr.subject';
update pa_properties set value = 'NCI CTRP: Trial FILES ATTACHED for REVIEW for ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}' where name = 'noxml.tsr.subject';
update pa_properties set value = 'NCI CTRP: Trial FILES ATTACHED for REVIEW for ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}' where name = 'tsr.proprietary.subject';
update pa_properties set value = 'NCI CTRP: Trial AMENDMENT TSR for REVIEW for ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}' where name = 'tsr.amend.subject';
update pa_properties set value = 'NCI CTRP: Trial AMENDMENT TSR for REVIEW for ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}' where name = 'noxml.tsr.amend.subject';


update pa_properties set value =
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
<p>Per your request, attached is the Trial Summary Report (TSR) file for the NCI Clinical Trials Reporting Program (CTRP) trial identified above.</p>
<p>The attached XML file, ${xmlFileName}, contains a subset of the information contained in the TSR, formatted for submission to ClinicalTrials.gov (if applicable). </p>
<p><b>NEXT STEPS:</b><br>
<LI>Review the attached Trial Summary Report (TSR), including all data fields, to ensure that the report is accurate.
<UL>
<LI>If you need to make any changes, enter them in the attached file and return the updated file by email as an attachment to the Clinical Trials Reporting Office (CTRO) at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes as appropriate.
<LI>If  the information is correct, reply to this email to confirm that the report is accurate. Be sure to include the NCI Trial ID, above, in all correspondence.
</UL>
<LI>Review and validate the XML file independently before you submit it to ClinicalTrials.gov (if applicable).
</p>
<p><b>Important!</b> If you do not respond to the CTRO within five (5) business days, CTRO staff will conclude that the information contained in this TSR is accurate.</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>'
where name = 'tsr.body';


update pa_properties set value =
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
<p>Per your request, attached is the Trial Summary Report (TSR) file for the NCI Clinical Trials Reporting Program (CTRP) trial identified above.</p>
<p><b>NEXT STEPS:</b><br>
<LI>Review the attached Trial Summary Report (TSR), including all data fields, to ensure that the report is accurate.
<UL>
<LI>If you need to make any changes, enter them in the attached file and return the updated file by email as an attachment to the Clinical Trials Reporting Office (CTRO) at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes as appropriate.
<LI>If  the information is correct, reply to this email to confirm that the report is accurate. Be sure to include the NCI Trial ID, above, in all correspondence.
</UL>
</p>
<p><b>Important!</b> If you do not respond to the CTRO within five (5) business days, CTRO staff will conclude that the information contained in this TSR is accurate.</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>'
where name = 'noxml.tsr.body';


update pa_properties set value =
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
<p>Per your request, attached is the Trial Summary Report (TSR) file for the NCI Clinical Trials Reporting Program (CTRP) trial identified above.</p>
<p><b>NEXT STEPS:</b><br>
<LI>Review the attached Trial Summary Report (TSR), including all data fields, to ensure that the report is accurate.
<UL>
<LI>If you need to make any changes, enter them in the attached file and return the updated file by email as an attachment to the Clinical Trials Reporting Office (CTRO) at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes as appropriate.
<LI>If  the information is correct, reply to this email to confirm that the report is accurate. Be sure to include the NCI Trial ID, above, in all correspondence.
</UL>
</p>
<p><b>Important!</b> If you do not respond to the CTRO within five (5) business days, CTRO staff will conclude that the information contained in this TSR is accurate.</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>'
where name = 'tsr.proprietary.body';


update pa_properties set value =
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
${nctIdentifierRow}
${ctepIdentifierRow}
${dcpIdentifierRow}
${otherIdentifiersRow}
<tr>
<td><b>Amendment Number:</b></td>
<td>${amendmentNumber}</td>
</tr>
<tr>
<td><b>Amendment Date:</b></td>
<td>${amendmentDate}</td>
</tr>
</table>    
<hr>
<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>The Clinical Trials Reporting Office (CTRO) has processed an amendment to the NCI Clinical Trials Reporting Program (CTRP) trial record identified above.</p>
<p>The attached XML file, ${xmlFileName}, contains a subset of the information contained in the TSR, formatted for submission to ClinicalTrials.gov (if applicable). </p>
<p><b>NEXT STEPS:</b><br>
<LI>Review the attached Trial Summary Report (TSR), including all data fields, to ensure that the report is accurate.
<UL>
<LI>If you need to make any changes, enter them in the attached file and return the updated file by email as an attachment to the Clinical Trials Reporting Office (CTRO) at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes as appropriate.
<LI>If  the information is correct, reply to this email to confirm that the report is accurate. Be sure to include the NCI Trial ID, above, in all correspondence.
</UL>
<LI>Review and validate the XML file independently before you submit it to ClinicalTrials.gov (if applicable).
</p>
<p><b>Important!</b> If you do not respond to the CTRO within five (5) business days, CTRO staff will conclude that the information contained in this TSR is accurate.</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>'
where name = 'tsr.amend.body';


update pa_properties set value =
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
${nctIdentifierRow}
${ctepIdentifierRow}
${dcpIdentifierRow}
${otherIdentifiersRow}
<tr>
<td><b>Amendment Number:</b></td>
<td>${amendmentNumber}</td>
</tr>
<tr>
<td><b>Amendment Date:</b></td>
<td>${amendmentDate}</td>
</tr>
</table>    
<hr>
<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>The Clinical Trials Reporting Office (CTRO) has processed an amendment to the NCI Clinical Trials Reporting Program (CTRP) trial record identified above.</p>
<p><b>NEXT STEPS:</b><br>
<LI>Review the attached Trial Summary Report (TSR), including all data fields, to ensure that the report is accurate.
<UL>
<LI>If you need to make any changes, enter them in the attached file and return the updated file by email as an attachment to the Clinical Trials Reporting Office (CTRO) at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes as appropriate.
<LI>If  the information is correct, reply to this email to confirm that the report is accurate. Be sure to include the NCI Trial ID, above, in all correspondence.
</UL>
</p>
<p><b>Important!</b> If you do not respond to the CTRO within five (5) business days, CTRO staff will conclude that the information contained in this TSR is accurate.</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>'
where name = 'noxml.tsr.amend.body';

