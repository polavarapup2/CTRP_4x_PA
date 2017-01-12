UPDATE pa_properties set value='<hr>
<table border="0">
<tr>
<td><b>NCI Trial ID:</b></td>
<td>${nciTrialIdentifier}</td>
</tr>
<tr>
<td><b>Batch File Name:</b></td>
<td>${fileName}</td>
</tr>
<tr>
<td><b>Submission Date:</b></td>
<td>${submissionDate}</td>
</tr>
</table>    
<hr>

<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>Thank you for submitting your accruals to the NCI Clinical Trials Reporting Program (CTRP).</p>
<p>Unfortunately, the submission failed due to the following errors:</p>
<ul>${errors}</ul>
<p><b>NEXT STEPS:</b><br>Please correct the errors identified above, and re-submit your batch file.</p> 
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>' where name='accrual.error.body';

UPDATE pa_properties set value='<hr>
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
<p>The Clinical Trials Reporting Office (CTRO) staff cannot amend the NCI Clinical Trials Reporting Program (CTRP) trial identified above for the following reason(s):<br>
<i>${reasonForRejection}</i>
</p>
<p><b>NEXT STEPS:</b><br>
Please contact us at ncictro@mail.nih.gov at your earliest convenience to resolve the issue. 
</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>' where name='trial.amend.reject.body';

UPDATE pa_properties set value='<hr>
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
<p>We would like to remind you that processing of the NCI Clinical Trials Reporting Program (CTRP) trial identified above has been on hold as of ${HoldDate} for the following reason:<br>
<i>${HoldReason}.</i>
</p>
<p><b>IMPORTANT NEXT STEP:</b><br>
Please contact us at ncictro@mail.nih.gov at your earliest convenience, but no later than close of business on ${Deadline}. After that date, we will be unable to process your trial.
</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>' where name='trial.onhold.reminder.body';

UPDATE pa_properties set value='<hr>
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
<p>Processing of the NCI Clinical Trials Reporting Program (CTRP) trial identified above is on hold as of ${HoldDate} for the following reason: <br>
<i>${HoldReason}.</i>
</p>
<p><b>NEXT STEPS:</b><br>
Please contact us at ncictro@mail.nih.gov at your earliest convenience, but no later than close of business on ${Deadline}. We are unable to resume processing your trial without further information from you.
</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>' where name='trial.onhold.email.body';

UPDATE pa_properties set value='
<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>The Clinical Trials Reporting Office (CTRO) has accepted your request for Administrator status in the NCI Clinical Trials Reporting Program (CTRP) for the organization, ${affliateOrgName}.</p>
<p><b>NEXT STEPS:</b><br>
You can access all trials for the ${affliateOrgName} organization, review and update access to trials owned by the organization, and assign new administrator status to other users from the organization.  
</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>
' where name='trial.admin.accept.body';

UPDATE pa_properties set value='<b>Marker Text: </b> ${markerText}<br>' where name='CDE_MARKER_REQUEST_MARKER_TEXT_CLAUSE';

UPDATE pa_properties set value='<hr>
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
<td><b>Submission Date:</b></td>
<td>${submissionDate}</td>
</tr>
</table>    
<hr>
<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>You have saved a draft of the trial identified above for submission to the NCI Clinical Trials Reporting Program (CTRP).</p>
<p>The CTRP has assigned your draft a unique temporary identifier for tracking purposes.</p> 
<p><b>NEXT STEPS:</b><br>
To retrieve and complete your submission, use the "Search Saved Drafts" feature on the "Search Trials" page in the CTRP Registration application.
</p>
<p>Clinical Trials Reporting Office (CTRO) staff will not access or process your trial until you have completed the submission.</p>
<p><b>Important!</b> You can save your draft  for a maximum of 30 days.</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>' where name='trial.partial.register.body';

UPDATE pa_properties set value='<hr>
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
<td>${receiptDate}</td>
</tr>
</table>    
<hr>
<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>The NCI Clinical Trials Reporting Office (CTRO) has accepted the trial identified above for registration in the NCI Clinical Trials Reporting Program (CTRP).</p>
<p><b>NEXT STEPS:</b><br>
<UL>
<LI><p>CTRO staff process your trial, including abstracting the protocol document, to produce a CTRP trial record within ten (10) business days*. </p></LI>
<LI><p>CTRO staff email you a Trial Summary Report (TSR) for review. The XML file attached to that email contains data formatted for submission to ClinicalTrials.gov (if required). This file contains a subset of the information contained in the TSR. It is important that you review and validate the XML file independently.</p></LI>
</UL>
</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>
<p>
* The NCI Clinical Trials Reporting Office (CTRO) makes every effort to process and return a Trial Summary Report (TSR) and XML file for all trials 
within a ten (10) day period. This ten day period begins after a complete submission, and therefore the submitter should be available during that time to 
resolve any discrepancies (for example, missing documentation, regulatory information, etc.)  Additionally, the potential variability of submission volume at 
any given time and/or complexity of a protocol can impact the processing time. If trial submission volume exceeds the CTRO''s 
capacity for processing, the CTRO will prioritize submissions based on submitter need. If a CTRP registrant requires expedited processing of a protocol submission,  
please contact the CTRO. Be sure to reference the NCI CTRP ID, and request priority processing.
</p>' where name='trial.accept.body';

UPDATE pa_properties set value='<hr>
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
<td>${receiptDate}</td>
</tr>
</table>    
<hr>
<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>The Clinical Trials Reporting Office (CTRO) staff cannot register the trial identified above in the NCI Clinical Trials Reporting Program (CTRP) for the following reason(s):<br>
<i>${reasoncode}.</i>
</p>
<p><b>NEXT STEPS:</b><br>
If you feel that this trial has been rejected in error, please contact us at ncictro@mail.nih.gov at your earliest convenience to resolve the issue.
</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>' where name='rejection.body';

 UPDATE pa_properties set value='<hr>
<table border="0">
<tr>
<td><b>NCI Trial ID:</b></td>
<td>${nciTrialIdentifier}</td>
</tr>
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
<td><b>Registered By:</b></td>
<td>${SubmitterName}</td>
</tr>
</table>    
<hr>

<p>Date: ${CurrentDate}</p>
<p>Dear Clinical Trials Reporting Office staff,</p>
<p>${SubmitterName} has submitted the new trial ${nciTrialIdentifier} for registration with the Clinical Trials Reporting Program (CTRP) and has assigned trial record ownership to one or more CTRP users.</p>
<p>However, the CTRP system was not able to assign trial record ownership because the intended owner''s email address, as submitted by ${SubmitterName}, is not associated with a registered CTRP user.</p>
<p><b>Note: </b>The submitter may have assigned trial record ownership to more than one user.<p>
<p>The unregistered email address is as follows:<br>${badEmail}</p>
<p><b>NEXT STEPS:</b><br>
Please contact ${SubmitterName} or a representative from the submitter''s affiliated organization to identify the correct owner. 
</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>' where name='trial.register.unidentifiableOwner.email.body';

