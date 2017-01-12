DELETE FROM pa_properties where name='closed_industrial_trial_statuses';
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'closed_industrial_trial_statuses',
'CLOSED_TO_ACCRUAL, CLOSED_TO_ACCRUAL_AND_INTERVENTION, ADMINISTRATIVELY_COMPLETE, COMPLETE');