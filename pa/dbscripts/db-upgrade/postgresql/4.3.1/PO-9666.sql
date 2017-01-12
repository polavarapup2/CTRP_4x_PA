delete from PA_PROPERTIES where name ='resultsUpdater.trials.scan.schedule';
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties), 'resultsUpdater.trials.scan.schedule','0 0 5 * * ?');   

--Update trial publish date email subject
delete from PA_PROPERTIES where name ='resultsUpdater.trials.job.email.subject';
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties), 'resultsUpdater.trials.job.email.subject',
'CTRP Nightly Job -- Update Trial Results Published Date');

--No trials updated email body
delete from PA_PROPERTIES where name ='resultsUpdater.trials.job.notupdated.email.body';
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),'resultsUpdater.trials.job.notupdated.email.body',
'<br>Dear CTRO:<br><br>
This nightly job ran successfully. No trials were updated.
<br><br>
This job checks the trial''s ClinicalTrials.gov record; if the trial''s results have been published there, and the 
CTRP "Trial Results Published Date" for that trial is empty,
the job will set the "Trial Results Published Date" to today''s date.<br><br>
Thank you, 
<br><br>
This is an automatically generated email message.');

--Trials upddated email
delete from PA_PROPERTIES where name ='resultsUpdater.trials.job.updated.email.body';
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),'resultsUpdater.trials.job.updated.email.body',
'<br>Dear CTRO:<br><br>
This nightly job ran successfully. It has set the "Trial Results Published Date" to today''s date for the following trial(s):
<br><br>
<table border="1">
<tr><td><b>NCI ID</b></td></tr>
<#list trialNciIdList as item>
<tr><td>${item}</td></tr>
</#list>
</table>
<br><br>
This job checks the trial''s ClinicalTrials.gov record; if the trial''s results have been published there, and the 
CTRP "Trial Results Published Date" for that trial is empty,
the job will set the "Trial Results Published Date" to today''s date.<br><br>
Thank you, 
<br><br>
This is an automatically generated email message.');



