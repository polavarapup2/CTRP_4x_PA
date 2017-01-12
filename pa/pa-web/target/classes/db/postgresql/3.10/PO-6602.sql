UPDATE pa_properties SET value = '<hr>
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
* The NCI Clinical Trials Reporting Office (CTRO) makes every effort to process and return a Trial Summary Report (TSR) and XML file for all abbreviated trials 
within a ten (10) day period. This ten day period begins after a complete submission, and therefore the submitter should be available during that time to 
resolve any discrepancies (for example, missing documentation, regulatory information, etc.)  Additionally, the potential variability of submission volume at 
any given time and/or complexity of a protocol can impact the processing time. If trial submission volume exceeds the CTRO''s 
capacity for processing, the CTRO will prioritize submissions based on submitter need. If a CTRP registrant requires expedited processing of a protocol submission,  
please contact the CTRO. Be sure to reference the NCI CTRP ID, and request priority processing.
</p>' WHERE name = 'trial.accept.body';