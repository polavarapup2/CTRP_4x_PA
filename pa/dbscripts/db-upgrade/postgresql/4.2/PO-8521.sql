DELETE FROM pa_properties where name='trial.status.transition.errors.subject';
DELETE FROM pa_properties where name='trial.status.transition.errors.body';

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 
    'trial.status.transition.errors.subject','Trial ${nciTrialIdentifier} was checked out for Administrative Abstraction');
    
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 
    'trial.status.transition.errors.body','Dear ${name}, 
<br/><br/>
The system found trial status transition errors for trial ${nciTrialIdentifier} when the Scientific Abstractor ${sciAbsName} checked the trial in for Scientific Abstraction.
<br/><br/>
The system has automatically checked out this trial for Administrative Abstraction under your name. Please log in, correct the status transition error(s), and check the trial in.
<br/><br/>
Thank you.
<br/><br/>
This is an automated message sent by the CTRP system. Please do not reply.
');