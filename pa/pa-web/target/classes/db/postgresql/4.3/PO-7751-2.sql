UPDATE pa_properties set value='<hr>
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
The Clinical Trials Reporting Office (CTRO) staff is reviewing your trial to ensure that it meets all of the requirements for registration in the CTRP system. They will email you their findings within two (2) business days.
</p>
<p>In the meantime, if you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>' 
where name='trial.register.body';

UPDATE pa_properties set value='<hr>
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
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>' where name='trial.amend.body';


UPDATE pa_properties set value='<hr>
<p><b>Title: </b>${trialTitle}</p>
${trialIdentifiers}
${otherIdentifiersRow}
<table border="0">
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
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>' where name='trial.amend.accept.body';


UPDATE pa_properties set value='<hr>
<p><b>Title: </b>${trialTitle}</p>
${trialIdentifiers}
${otherIdentifiersRow}
<table border="0">
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
${trialIdentifiers}
${otherIdentifiersRow}
<table border="0">
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
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>' where name='tsr.amend.body';

UPDATE pa_properties set value='<hr>
<p><b>Title: </b>${trialTitle}</p>
${trialIdentifiers}
${otherIdentifiersRow}
<table border="0">
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
${trialIdentifiers}
${otherIdentifiersRow}
<table border="0">
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
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>' where name='trial.update.body';

UPDATE pa_properties set value='<hr>
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
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>' where name='tsr.proprietary.body';

update pa_properties
set value = 'Date: ${CurrentDate}

${trialIdentifiers}

Dear ${SubmitterName},

The ownership of trial record ${nciTrialIdentifier} has been transferred from ${oldOwner} to ${newOwner}. 
The new owner is now able to amend and update the trial using any of the "My Trials" features in the Clinical Trials Reporting Program (CTRP) Registration application. 

If you feel that there has been an error in trial ownership transfer, or if you have additional questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for your participation in  the NCI Clinical Trials Reporting Program.'
where name = 'trial.ownership.body';

UPDATE pa_properties set value='<hr>
<p><b>Title: </b>${trialTitle}</p>
${trialIdentifiers}
<table border="0">
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
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>' where name='xml.body';

update pa_properties
set value = '<hr>
<br>
Date: ${CurrentDate}
<br>
<br />
${trialIdentifiers}
<hr>
Dear ${SubmitterName},
<br>
<br>
The NCI Clinical Trials Reporting Office (CTRO) has processed the following trial:
<br>
<br>
* NCI Trial Identifier: ${nciTrialIdentifier}
<br>
<br>
* Lead Organization Trial Identifier: ${leadOrgTrialIdentifier}
<br>
<br>   
* Title: ${trialTitle}
<br>
<br>
* Submission Date: ${receiptDate}.
<br>
<br>
Please review the attached Trial Summary Report (TSR), including all data fields, to ensure that the report is accurate.
<br>
<br>
* If you need to make any changes, enter them in the attached file and return the updated file by email as an attachment to the Clinical Trials Reporting Office (CTRO) at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes as appropriate.
<br>
<br>
* If  the information is correct, reply to this email to confirm that the report is accurate. Be sure to include the NCI Trial ID, as above, in all correspondence. 
<br>
<br>
Important! If the CTRO does not receive a response from you within five (5) business days, CTRO staff will conclude that the information contained in this TSR is accurate.
<br>
<br>
If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.
<br>
<br>
Thank you for your participation in the NCI Clinical Trials Reporting Program.'
where name = 'noxml.tsr.body';

UPDATE pa_properties set value='<hr>
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
The Clinical Trials Reporting Office (CTRO) staff is reviewing your trial to ensure that it meets all of the requirements for registration in the CTRP system. They will email you their findings within two (2) business days.
</p>
<p>In the meantime, if you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>' where name='proprietarytrial.register.body';

update pa_properties set value = '<hr>
${trialIdentifiers}
<hr>
<p>Date: ${CurrentDate}</p>
<p>The Clinical Trials Reporting Office (CTRO) requires a new permissible value to complete marker abstraction for trial ${trialIdentifier}.</p>
<p>
<b>Concept:</b> ${markerName}<br>
<b>Found in HUGO ?:</b> ${foundInHugo}<br>
${hugoCodeClause}<br>
${markerTextClause}<br>
<b>Submitter:</b> ${submitterName}<br>
</p>
<p>If you need any further information to create the new permissible value, contact the CTRO at ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>'
where name = 'CDE_MARKER_REQUEST_BODY';

UPDATE pa_properties set value='<hr>
${trialIdentifiers}
<table border="0">
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

UPDATE pa_properties set value='<hr>
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
${trialIdentifiers}
${otherIdentifiersRow}
<table border="0">
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
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>' where name='trial.onhold.termination.body';

UPDATE pa_properties set value='<hr>
<p><b>Title: </b>${trialTitle}</p>
${trialIdentifiers}
<hr>
<p>Date: ${CurrentDate}</p>
<p>Dear ${name},</p>
<p>The Clinical Trials Reporting Office (CTRO) has added you as an owner of the NCI Clinical Trials Reporting Program (CTRP) trial record identified above.</p>
<p>As an owner of this trial record, you can update or amend the trial in the CTRP Clinical Trials Registration application.</p>
<p><b>NEXT STEPS:</b><br>
If you do not want ownership of this trial, or if you have questions about this or other CTRP topics, please contact the CTRO at ncictro@mail.nih.gov. 
</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>' where name='trial.ownership.add.email.body';

UPDATE pa_properties set value='<hr>
<p><b>Title: </b>${trialTitle}</p>
${trialIdentifiers}
<hr>
<p>Date: ${CurrentDate}</p>
<p>Dear ${name},</p>
<p>The Clinical Trials Reporting Office (CTRO) cancelled your ownership of the NCI Clinical Trials Reporting Program (CTRP) trial record identified above.</p>
<p><b>NEXT STEPS:</b><br>
If you believe this is an error, or if you have additional questions about this or other CTRP topics, please contact the CTRO at ncictro@mail.nih.gov. 
</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>' where name='trial.ownership.remove.email.body';


update pa_properties set value =
'<hr>
${trialIdentifiers}
<table border="0">    
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
<p>Thank you for submitting your accruals to the NCI Clinical Trials
    Reporting Program (CTRP).</p>
<p>The CTRP processed your file successfully.</p>
<p>
    <b>Number of Subjects Registered: </b> ${count}
</p>
<p>${errorsDesc}</p>
<ul>${errors}</ul>

${preprocessingErrors}

<p>If you have questions about this or other CTRP topics, please
    contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the
    Clinical Trials Reporting Program.</p>'
where name = 'accrual.confirmation.body';

update pa_properties set value ='<hr>
${trialIdentifiers}
<table border="0">
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
<p>Accrual counts for the following Study Site(s) were updated successfully as follows:<br> 
${studySiteCounts}
</p>
<p>${errorsDesc}</p>
<ul>${errors}</ul>
<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>' where name='accrual.industrialTrial.body';

UPDATE pa_properties set value='<hr>
<p><b>Title: </b>${trialTitle}</p>
${trialIdentifiers}
<table border="0">
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


UPDATE pa_properties set value ='<hr>

${trialIdentifiers}

<hr>
<table border="0">
    <tr>
        <td><b>Trial Title:</b></td>
        <td>${trial.officialTitle}</td>
    </tr>    
    <tr>
        <td><b>Previous Trial Status:</b></td>
        <td>${data.previousTrialStatus.statusCode.code}</td>
    </tr>
    <tr>
        <td><b>New Trial Status:</b></td>
        <td>${data.newTrialStatus.statusCode.code}</td>
    </tr>
</table>
<hr>
<p>Date: ${date}</p>
<p>Dear ${recipient.fullName},</p>
<p>The Status on the above trial has been changed and as a result
    the following Open Participating Sites have been closed:</p>

<table border="0">
    <#list data.siteData as site>
    <tr>
        <td align="right">Site Name:</td>
        <td align="left">${site.name}</td>
    </tr>
    <tr>
        <td align="right">Previous Site Status:</td>
        <td align="left">${site.previousTrialStatus.code}</td>
    </tr>

    <tr>
        <td align="right">New Site Status:</td>
        <td align="left">${site.newStatus.code}</td>
    </tr>
    <tr>
        <td align="right">Missing Status(es) or Errors:</td>
        <td align="left">${site.errors}</td>
    </tr>
    <tr>
        <td colspan="2">&nbsp;</td>
    </tr>
    </#list>
</table>

<p>
    <b>NEXT STEPS:</b><br> Please login to the Clinical Trials
    Reporting Program Registry application and provide any missing site
    status information listed above.
</p>
<p>If you have any questions or concerns regarding this change,
    please contact the Clinical Trials Reporting Office (CTRO) staff at
    ncictro@mail.nih.gov.</p>
<p>Thank you for ensuring accurate Trial and Participating Site
    Statuses and Dates in the Clinical Trials Reporting Program.</p>' where name = 'site.status.change.notification.body';
    
    
update PA_PROPERTIES set value = '<hr>
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
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>' where name = 'trial.reg.service.amend.warning.body';  


update PA_PROPERTIES set value = '<hr>
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
of this indicator to "No" for this trial, and has notified the CTRO of this action. The CTRO may contact the trial’s point of contact for additional information before 
setting the value of the indicator to "Yes".</p>
<p><b>NEXT STEPS:</b><br>
The Clinical Trials Reporting Office (CTRO) staff is reviewing your trial to ensure that it meets all of the requirements for registration in the CTRP system. They will email you their findings within two (2) business days.
</p>
<p>In the meantime, if you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>' where name = 'proprietarytrial.service.create.register.body';

update PA_PROPERTIES set value = '<hr>
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
of this indicator to "No" for this trial, and has notified the CTRO of this action. The CTRO may contact the trial’s point of contact for additional information before 
setting the value of the indicator to "Yes".</p>
<p><b>NEXT STEPS:</b><br>
The Clinical Trials Reporting Office (CTRO) staff is reviewing your trial to ensure that it meets all of the requirements for registration in the CTRP system. They will email you their findings within two (2) business days.
</p>
<p>In the meantime, if you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>' where name = 'trial.service.create.register.body';

