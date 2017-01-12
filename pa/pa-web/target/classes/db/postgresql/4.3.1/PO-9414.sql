INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'ctrp.job.failure.recipients',
'example@example.com');

INSERT INTO pa_properties values((select max(identifier) + 1 from pa_properties),
    'ctrp.job.failure.body','Dear Sir or Madam,
    <br />
    <br />
This is to notify that CTRP nightly job <strong>${jobName}</strong> failed its processing at <strong>${timeStamp}</strong> with the following error message:
<br />
<br />
<pre>
${stackTrace}
</pre>
<br/>
<br/>
Please consult the server logs for more details.');


INSERT INTO pa_properties values((select max(identifier) + 1 from pa_properties),
    'ctrp.job.failure.subject','CTRP nightly job failure: ${jobName}');
