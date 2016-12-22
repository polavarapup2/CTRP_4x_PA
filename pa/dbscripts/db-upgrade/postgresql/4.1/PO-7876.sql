--Author   : Reshma Koganti
--Date     : 8/14/2014        
--Jira#    : PO-7876 Biomarkers - if no connection with caDSR API then DO NOT change status to INACTIVE in CTRP


INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 
'CADSR_SYNC_JOB_ERROR_SUBJECT', 'NCI CTRP: caDSR Biomarker SYNCHRONIZATION FAILURE');
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 
'CADSR_SYNC_JOB_ERROR_BODY', ' 
<hr>
<p>Date: ${CurrentDate}</p>
<p>Dear CTRO STAFF,</p>
<p>Unfortunately, the connection from the CTRP system to the caDSR system failed on ${CurrentDate} during the nightly biomarker auto-synchronization process. This can occur, for example, if the caDSR system was down during that time.
<br>
</p>
<p><b>NEXT STEPS:</b><br>
The CTRP engineering team is investigating this issue.
If you were unable to locate a biomarker that exists in the caDSR system, but not in the CTRP system, please try your search again after the next nightly synchronization. 
</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>');
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 
'CADSR_SYNC_JOB_EMAIl_LIST', 'ctrp-team@semanticbits.com, ncictrp-techsupport@mail.nih.gov');
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 
'CADSR_SYNC_JOB_FROM_ADDRESS',  'ncictro@mail.nih.gov');