insert into pa_properties(IDENTIFIER,name,value) VALUES(39,'xml.subject',
'NCI Clinical Trials Reporting Program (CTRP) Trial Summary Report');

insert into pa_properties(IDENTIFIER,name,value) VALUES(40,'xml.body',
'${CurrentDate}

Dear ${SubmitterName},

XML file and Trial Summary Report for the following trial:
${leadOrgTrialId}, ${trialTitle}, received by the NCI Clinical Trial Reporting Office (CTRO) on ${receiptDate} and assigned NCI Trial ID ${nciTrialID}, are emailed on your request. 

If you have questions on this or other CTRP topics, please contact us at ncictro@mail.nih.gov.

Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).
');
