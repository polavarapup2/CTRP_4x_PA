insert into pa_properties(IDENTIFIER,name,value) VALUES(25,'tsr.proprietary.subject',
'NCI Clinical Trials Reporting Program (CTRP) Trial Summary Report');

insert into pa_properties(IDENTIFIER,name,value) VALUES(26,'tsr.proprietary.body',
'${CurrentDate}

Dear ${SubmitterName}

Your Trial, ${leadOrgTrialId}, ${trialTitle}, received by the NCI Clinical Trial Reporting Office (CTRO) on ${receiptDate} and assigned NCI Trial ID ${nciTrialID}, has been processed by the CTRO.

Please review the attached Trial Summary Report (${fileName}), including all data fields, to ensure that they accurately reflect the trial. Please mark any changes that are required in the attached file and return the updated file by e-mail to the CTRO at ncictro@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes, as appropriate. If there are no changes, please respond to this email confirming that the report accurately reflects your trial. Please include the NCI Trial ID, as above, in all correspondence. If a response is NOT received within five (5) business days, CTRO will conclude that the information contained in this Trial Summary Report is acceptable. 

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).
');