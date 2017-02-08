ALTER TABLE STUDY_SITE ADD COLUMN PROGRAM_CODE VARCHAR(200) ;

insert into pa_properties(IDENTIFIER,name,value) VALUES(23,'trial.update.subject',
'NCI Clinical Trials Reporting Program (CTRP) Trial Update Acknowledgement');

insert into pa_properties(IDENTIFIER,name,value) VALUES(24,'trial.update.body',
'You have successfully submitted the update to the trial with NCI Trial Identifier ${nciTrialIdentifier}

Your submission will now be processed by the staff of the CTRO. Upon completion, the CTRO will e-mail you a Trial Summary Report listing key data elements that have been abstracted from the trial protocol.  At that time, the CTRO will request that you review the Trial Summary Report for accuracy and respond with any changes that may be required. 
 
If you have questions on this or other CTRP topics, please contact us at ncictrp@mail.nih.gov
 
Thank you for registering your update with NCI Clinical Trials Reporting Program (CTRP).
');



