--PO-5961 email template related changes. New entries required.

insert into pa_properties VALUES ((select max(identifier) + 1 from pa_properties),'nct.identifier.row','<tr><td><b>NCT ID:</b></td><td>${nctIdentifier}</td></tr>');
insert into pa_properties VALUES ((select max(identifier) + 1 from pa_properties),'other.identifiers.row','<tr><td><b>Other IDs:</b></td><td>${otherTrialIdentifiers}</td></tr>');
insert into pa_properties VALUES ((select max(identifier) + 1 from pa_properties),'ctep.identifier.row','<tr><td><b>CTEP ID:</b></td><td>${ctepTrialIdentifier}</td></tr>');
insert into pa_properties VALUES ((select max(identifier) + 1 from pa_properties),'dcp.identifier.row','<tr><td><b>DCP ID:</b></td><td>${dcpTrialIdentifier}</td></tr>');

--Trial Registration Email template changes.

update pa_properties set value = 'NCI CTRP: Trial RECORD CREATED for ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}' where name = 'trial.register.subject';

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
<b>${nciTrialIdentifier}</b><br> 
Please reference this number in all future correspondence with the Clinical Trials Reporting Office (CTRO).</p> 

<p>${errors}</p>
<p><b>NEXT STEPS:</b><br>
The Clinical Trials Reporting Office (CTRO) staff is reviewing your trial to ensure that it meets all of the requirements for registration in the CTRP system. They will email you their findings within two (2) business days.
</p>
<p>In the meantime, if you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>

<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>'
where name = 'trial.register.body';

--Abbreviated Trial Registration email template changes

update pa_properties set value = 'NCI CTRP: Trial RECORD CREATED for ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}' where name = 'proprietarytrial.register.subject';

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
<b>${nciTrialIdentifier}</b><br> 
Please reference this number in all future correspondence with the Clinical Trials Reporting Office (CTRO).</p> 
<p>${errors}</p>
<p><b>NEXT STEPS:</b><br>
The Clinical Trials Reporting Office (CTRO) staff is reviewing your trial to ensure that it meets all of the requirements for registration in the CTRP system. They will email you their findings within two (2) business days.
</p>
<p>In the meantime, if you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>'
where name = 'proprietarytrial.register.body';

--On Hold Email template changes.

update pa_properties set value = 'NCI CTRP: Trial PROCESSING ON HOLD for ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}' where name = 'trial.onhold.reminder.subject';

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
<p>Processing of the NCI Clinical Trials Reporting Program (CTRP) trial identified above is on hold as of ${HoldDate} for the following reason: <br>
${HoldReason}.
</p>
<p><b>NEXT STEPS:</b><br>
Please contact us at ncictro@mail.nih.gov at your earliest convenience, but no later than close of business on ${Deadline}. We are unable to resume processing your trial without further information from you.
</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>'
where name = 'trial.onhold.email.body';


--On Hold Trial reminder template changes. 

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
<p>We would like to remind you that processing of the NCI Clinical Trials Reporting Program (CTRP) trial identified above has been on hold as of ${HoldDate} for the following reason:<br>
${HoldReason}.
</p>
<p><b>IMPORTANT NEXT STEP:</b><br>
Please contact us at ncictro@mail.nih.gov at your earliest convenience, but no later than close of business on ${Deadline}. After that date, we will be unable to process your trial.
</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>'
where name = 'trial.onhold.reminder.body';

--Trial Rejection Email template changes.

update pa_properties set value = 'NCI CTRP: Trial REGISTRATION REJECTED for ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}' where name = 'rejection.subject';

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
<td>${receiptDate}</td>
</tr>
</table>    
<hr>
<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>The Clinical Trials Reporting Office (CTRO) staff cannot register the trial identified above in the NCI Clinical Trials Reporting Program (CTRP) for the following reason(s):<br>
${reasoncode}.
</p>
<p><b>NEXT STEPS:</b><br>
If you feel that this trial has been rejected in error, please contact us at ncictro@mail.nih.gov at your earliest convenience to resolve the issue.
</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>'
where name = 'rejection.body';

--Trial Accept Email template changes.

update pa_properties set value = 'Trial REGISTRATION ACCEPTED for ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}' where name = 'trial.accept.subject';

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
<td>${receiptDate}</td>
</tr>
</table>    
<hr>
<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>The NCI Clinical Trials Reporting Office (CTRO) has accepted the trial identified above for registration in the NCI Clinical Trials Reporting Program (CTRP).</p>
<p><b>NEXT STEPS:</b><br>
<LI><i>If you registered a Complete trial:</i>
<UL>
<LI><p>CTRO staff process your trial, including abstracting the protocol document, to produce a CTRP trial record within ten (10) business days*. </p>
<LI><p>CTRO staff email you a Trial Summary Report (TSR) for review. The XML file attached to that email contains data formatted for submission to ClinicalTrials.gov (if required). This file contains a subset of the information contained in the TSR. It is important that you review and validate the XML file independently.</p>
</UL>
<LI><i>If you registered an Abbreviated trial without submitting a protocol document:
CTRO staff process your trial and send you a Trial Summary Report (TSR) for review when finished.</i>
</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>
<p>
* The NCI Clinical Trials Reporting Office (CTRO) makes every effort to process and return a Trial Summary Report (TSR) and XML file for all abbreviated trials 
within a ten (10) day period. This ten day period begins after a complete submission, and therefore the submitter should be available during that time to 
resolve any discrepancies (for example, missing documentation, regulatory information, etc.)  Additionally, the potential variability of submission volume at 
any given time and/or complexity of a protocol can impact the processing time. If trial submission volume exceeds the CTRO''s 
capacity for processing, the CTRO will prioritize submissions based on submitter need. If a CTRP registrant requires expedited processing of a protocol submission,  
please contact the CTRO. Be sure to reference the NCI CTRP ID, and request priority processing.
</p>
'
where name = 'trial.accept.body';

--Trial Update Email template changes.

update pa_properties set value = 'NCI CTRP: Trial RECORD UPDATED for ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}' where name = 'trial.update.subject';

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
<td>${receiptDate}</td>
</tr>
</table>   
<hr>
<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>The NCI Clinical Trials Reporting Program (CTRP) received updates for the trial identified above.</p>
<p><b>Update Information:</b><br>
${updates}
</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>
'
where name = 'trial.update.body';

--Amendment Submission Email template changes.

update pa_properties set value = 'NCI CTRP: Trial AMENDMENT RECORD CREATED for ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}' where name = 'trial.amend.subject';

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
<p>The Clinical Trials Reporting Office (CTRO) has received the amendment you submitted for the trial identified above.</p>
<p><b>Amendment Information:</b>
<UL>
<LI><b>Amendment Number:</b> ${amendmentNumber}
<LI><b>Amendment Date:</b> ${amendmentDate}
</UL>
</p>
<p><b>NEXT STEPS:</b><br>
The Clinical Trials Reporting Office (CTRO) staff is reviewing the amended information to ensure that it meets all of the requirements for registration in the CTRP system. The CTRO will send you a separate email that indicates whether they have accepted or rejected your trial within two (2) business days.
</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>
'
where name = 'trial.amend.body';


--Amendment Submission Accept Email template changes.

update pa_properties set value = 'NCI CTRP: Trial AMENDMENT RECORD ACCEPTED for ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}' where name = 'trial.amend.accept.subject';

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
<p>Thank you for submitting your trial amendment to the NCI Clinical Trials Reporting Office (CTRO). The amendment to the trial record identified above has been accepted for processing.</p>
<p><b>NEXT STEPS:</b><br>
The Clinical Trials Reporting Office (CTRO) staff is abstracting the amendment that you submitted to update your trial record in the NCI Clinical Trials Reporting Program (CTRP). 
</p>
<p>
When finished, they will send you a new Trial Summary Report (TSR) that reflects the changes indicated in the trial amendment. The new XML file attached to that message contains data formatted for submission to ClinicalTrials.gov (if required). 
</p>
<p>It is important that you review and validate the XML file independently.</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>
'
where name = 'trial.amend.accept.body';

--Amendment Submission Reject Email template changes.

update pa_properties set value = 'NCI CTRP: Trial AMENDMENT RECORD REJECTED for ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}' where name = 'trial.amend.reject.subject';

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
<p>The Clinical Trials Reporting Office (CTRO) staff cannot amend the NCI Clinical Trials Reporting Program (CTRP) trial identified above for the following reason(s):<br>
${reasonForRejection}
</p>
<p><b>NEXT STEPS:</b><br>
Please contact us at ncictro@mail.nih.gov at your earliest convenience to resolve the issue. 
</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>
'
where name = 'trial.amend.reject.body';

--Added as Trial Owner Email template changes.

update pa_properties set value = 'NCI CTRP: Trial RECORD OWNER ADDED for ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}' where name = 'trial.ownership.add.email.subject';

update pa_properties set value =
'<hr>
<p><b>Title:</b>${trialTitle}</p>
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
</table>    
<hr>
<p>Date: ${CurrentDate}</p>
<p>Dear ${name},</p>
<p>The Clinical Trials Reporting Office (CTRO) has added you as an owner of the NCI Clinical Trials Reporting Program (CTRP) trial record identified above.</p>
<p>As an owner of this trial record, you can update or amend the trial in the CTRP Clinical Trials Registration application.</p>
<p><b>NEXT STEPS:</b><br>
If you do not want ownership of this trial, or if you have questions about this or other CTRP topics, please contact the CTRO at ncictro@mail.nih.gov. 
</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>
'
where name = 'trial.ownership.add.email.body';

--Removed as Trial Owner Email template changes.

update pa_properties set value = 'NCI CTRP: Trial RECORD OWNERSHIP CANCELLED for ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}' where name = 'trial.ownership.remove.email.subject';

update pa_properties set value =
'<hr>
<p><b>Title:</b>${trialTitle}</p>
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
</table>    
<hr>
<p>Date: ${CurrentDate}</p>
<p>Dear ${name},</p>
<p>The Clinical Trials Reporting Office (CTRO) cancelled your ownership of the NCI Clinical Trials Reporting Program (CTRP) trial record identified above.</p>
<p><b>NEXT STEPS:</b><br>
If you believe this is an error, or if you have additional questions about this or other CTRP topics, please contact the CTRO at ncictro@mail.nih.gov. 
</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>
'
where name = 'trial.ownership.remove.email.body';

--Accepted Admin Access Email template changes.

update pa_properties set value = 'NCI CTRP: Request for ADMINISTRATOR STATUS ${adminAccessRequestStatus}' where name = 'trial.admin.accept.subject';

update pa_properties set value =
'
<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>The Clinical Trials Reporting Office (CTRO) has accepted your request Administrator status in the NCI Clinical Trials Reporting Program (CTRP) for the organization, ${affliateOrgName}.</p>
<p><b>NEXT STEPS:</b><br>
You can access all trials for the ${affliateOrgName} organization, review and update access to trials owned by the organization, and assign new administrator status to other users from the organization.  
</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>
'
where name = 'trial.admin.accept.body';


--Rejected Admin Access Email template changes.

update pa_properties set value =
'
<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>The Clinical Trials Reporting Office (CTRO) has rejected your request for Administrator status in the NCI Clinical Trials Reporting Program (CTRP) for the organization, ${affliateOrgName}</p>
<p><b>Reason for rejection:</b><br>
${rejectReason}  
</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>
'
where name = 'trial.admin.reject.body';

--Forgot your username Email template changes.

update pa_properties set value = 'NCI CTRP: Request for CTRP Account USERNAME FULFILLED' where name = 'user.usernameSearch.subject';

update pa_properties set value =
'<p>Dear ${firstName} ${lastName},</p>
<p>Per your request, following is the NCI Clinical Trials Reporting Program (CTRP) account username associated with the email you provided:</p>
<p><b>Username: </b>${userNames}</p>  
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov or visit our website at http://www.cancer.gov/ncictrp.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>'
where name = 'user.usernameSearch.body';

--Create Biomarker Request Email template changes.

update pa_properties set value = 'NCI CTRP: CTRP REQUEST for NEW PERMISSIBLE VALUES' where name = 'CDE_MARKER_REQUEST_SUBJECT';

update pa_properties set value =
'<hr>
<table border="0">
<tr>
<td><b>NCI Trial ID:</b></td>
<td>${trialIdentifier}</td>
</tr>
</table>    
<hr>
<p>Date: ${CurrentDate}</p>
<p>The Clinical Trials Reporting Office (CTRO) requires a new permissible value to complete marker abstraction for ${trialIdentifier}.</p>
<p>
<b>Submitter: </b> ${submitterName}<br>
<b>Concept: </b>${markerName}<br>
<b>Found in HUGO ?: </b> ${foundInHugo}<br>
${hugoCodeClause}
${markerTextClause}
</p>
<p>If you need any further information to create the new permissible value, contact the CTRO at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>'
where name = 'CDE_MARKER_REQUEST_BODY';

update pa_properties set value = '<b>HUGO Marker Code: </b> ${hugoCode}<br>'
where name = 'CDE_MARKER_REQUEST_HUGO_CLAUSE';

update pa_properties set value = '<b>Marker Text: </b> ${markerText}'
where name = 'CDE_MARKER_REQUEST_MARKER_TEXT_CLAUSE';

--Unidentified Owner Email to Mismatch Users Email template changes.

update pa_properties set value = 'NCI CTRP: CTRP ACCOUNT STATUS: TRIAL OWNERSHIP ERROR' where name = 'trial.register.mismatchedUser.email.subject';

update pa_properties set value =
'<hr>
<table border="0">
<tr>
<td><b>NCI Trial ID:</b></td>
<td>${nciTrialIdentifier}</td>
</tr>
<tr>
<td><b>Registered By:</b></td>
<td>${SubmitterName}</td>
</tr>
</table>    
<hr>

<p>Date: ${CurrentDate}</p>

<p>Dear ${SubmitterName},</p>

<p>The NCI Clinical Trials Reporting Program (CTRP) system identified you as having registered the new trial identified above.</p>
<p>However, your email address, ${emailAddress}, is not associated with a registered CTRP user.  Therefore the CTRP was not able to assign you trial record ownership.</p>

<p><b>NEXT STEPS:</b><br>
Log in to NCI CTRP Registration and use the My Account feature to ensure that the email address associated with your username is correct.
After correcting account errors, or if you have trouble creating or modifying your CTRP account, please contact the NCI Clinical Trials Reporting Office at ncictro@mail.nih.gov to resolve this problem. 
</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>'
where name = 'trial.register.mismatchedUser.email.body';

--Unidentified Owner Email to CTRO template changes.

update pa_properties set value = 'NCI CTRP: CTRP Trial Record Ownership Assignment: EMAIL ADDRESS ERROR' where name = 'trial.register.unidentifiableOwner.email.subject';

update pa_properties set value =
'<hr>
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
<td><b>Registered By:</b></td>
<td>${SubmitterName}</td>
</tr>
</table>    
<hr>

<p>Date: ${CurrentDate}</p>
<p>Dear Clinical Trials Reporting Office staff,</p>
<p>${SubmitterName} has submitted the new trial ${nciTrialIdentifier} for registration with the Clinical Trials Reporting Program (CTRP) and has assigned trial record ownership to one or more CTRP users.</p>
<p>However, the CTRP system was not able to assign trial record ownership because the intended owner''s email address, as submitted by ${SubmitterName}, is not associated with a registered CTRP user.</p>
<p><b>Note:</b>The submitter may have assigned trial record ownership to more than one user.<p>
<p>The unregistered email address is as follows:<br>${badEmail}</p>
<p><b>NEXT STEPS:</b><br>
Please contact ${SubmitterName} or a representative from the submitter''s affiliated organization to identify the correct owner. 
</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>'
where name = 'trial.register.unidentifiableOwner.email.body';

--TSR Report with XML email template changes

update pa_properties set value = 'NCI CTRP: Trial FILES ATTACHED for REVIEW for ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}' where name = 'xml.subject';

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
<p>The attached XML file, ${fileName}, contains a subset of the information contained in the TSR, formatted for submission to ClinicalTrials.gov (if applicable).</p>
<p><b>NEXT STEPS:</b>
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
where name = 'xml.body';


--Registry Batch Upload email template changes.

update pa_properties set value = 'NCI CTRP: Trial Registration BATCH SUBMISSION CONFIRMED' where name = 'trial.batchUpload.subject';

update pa_properties set value =
'<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>Thank you for submitting your trials to the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP) using the batch upload feature.</p>
<p>You can review the Summary of Your Submission below.</p>
<p><u><b>SUMMARY OF SUBMISSION</b></u></p>'
where name = 'trial.batchUpload.bodyHeader';


update pa_properties set value = 
'
<table border="0">
<tr>
<td><b>Number of trials submitted: </b></td>
<td>${totalCount}</td>
</tr>
<tr>
<td><b>Number of trials registered successfully: </b></td>
<td>${successCount}</td>
</tr>
<tr>
<td><b>Number of trials not registered due to failure: </b></td>
<td>${failedCount}</td>
</tr>
</table>'    
where name = 'trial.batchUpload.body';

update pa_properties set value =
'<p><b>Note:</b> The number of submissions that failed can be misleading because it may include a number of empty lines in the file you submitted.</p>
<p>The progress report attached to this email contains the following information:
<ul>
<li>Loading status for each trial record in your data file
<li>Record identifier captured from the first column in the data file 
<li>NCI trial identifier  for each trial successfully recorded
<li>Error message for each record that failed registration
</ul>
</p>
<p><b>Note:</b> If the CTRP system cannot process your files, you will not receive a progress report, and the Summary of Submission Report will contain error messages.</p> 
<p><b>NEXT STEPS:</b><br>
If you have received error messages, check your trial data file to ensure that you have used valid values. Validate the trial information using the current specification.
</p>'
where name = 'trial.batchUpload.reportMsg';

update pa_properties set value = 
'<p>If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for using the NCI Clinical Trials Reporting Program.</p>'
where name = 'trial.batchUpload.bodyFooter';


update pa_properties set value = 
'<p><b>Note:</b> If the CTRP system cannot process your files, you will not receive a progress report, and the Summary of Submission Report will contain error messages.
If you have received error messages, check your trial data file to ensure that you have used valid values. Validate the trial information using the specification for the release ${ReleaseNumber}.</p>'
where name = 'trial.batchUpload.errorMsg';

--Trial Partial Submission email template changes

update pa_properties set value = 'NCI CTRP: Trial RECORD SAVED as DRAFT for ${leadOrgTrialIdentifier}' where name = 'trial.partial.register.subject';

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
To retrieve and complete your submission, use the "Search Saved Draft" feature on the "Search Trials" page in the CTRP Registration application.
</p>
<p>Clinical Trials Reporting Office (CTRO) staff will not access or process your trial until you have completed the submission.</p>
<p><b>Important!</b> You can save your draft  for a maximum of 30 days.</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>'
where name = 'trial.partial.register.body';

--Accrual Batch Upload Confirmation Report email template changes

update pa_properties set value = 'NCI CTRP: Accrual SUBMISSION SUCCESSFUL for ${nciTrialIdentifier}' where name = 'accrual.confirmation.subject';

update pa_properties set value =
'<hr>
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
<p>The CTRP processed your file successfully.</p> 
<p><b>Number of Subjects Registered: </b> ${count}</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>'
where name = 'accrual.confirmation.body';


--Accrual Batch Upload Industrial Trial Confirmation Report

update pa_properties set value =
'<hr>
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
<p>Thank you for submitting your accrual counts to the NCI Clinical Trials Reporting Program (CTRP).</p>
<p>The CTRP updated accrual counts for the following Study Sites successfully:<br> 
${studySiteCounts}
</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>'
where name = 'accrual.industrialTrial.body';


--Change Code email template

update pa_properties set value =
'<hr>
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
<p>Thank you for submitting your accruals to the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP).</p>
<p>You entered the Change Code "2", indicating that the file did not require processing. The batch file has been stored for future reference.</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>'
where name = 'accrual.changeCode.body';

--Accrual Batch Upload Error Report email template changes

update pa_properties set value = 'NCI CTRP: Accrual SUBMISSION ERRORS for ${nciTrialIdentifier}' where name = 'accrual.error.subject';

update pa_properties set value =
'<hr>
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
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>'
where name = 'accrual.error.body';

--Trial Termination Email template changes.

update pa_properties set value = 'NCI CTRP: Trial PROCESSING TERMINATED for ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}' where name = 'trial.onhold.termination.subject';

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
<td>${receiptDate}</td>
</tr>
</table>    
<hr>

<p>Date: ${CurrentDate}</p>
<p>Dear Clinical Trials Reporting Office staff,</p>
<p>The NCI Clinical Trials Reporting Program (CTRP) has stopped processing the trial identified above due to lack of response, within the allotted response period, 
to multiple requests for information from the submitter and trial record owner(s).</p>
<p>As a result, the CTRP system has taken the following steps:
<ul>
<li>Closed the trial''s On Hold state as of today''s date
<li>Recorded the termination date in the CTRP system
</ul>
</p>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>'
where name = 'trial.onhold.termination.body';



