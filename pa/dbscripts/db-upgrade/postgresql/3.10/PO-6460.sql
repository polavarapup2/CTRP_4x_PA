-- PO-6460 : Changing the milestone_code from TRIAL_SUMMARY_SENT to TRIAL_SUMMARY_REPORT
update study_milestone set milestone_code = 'TRIAL_SUMMARY_REPORT' where milestone_code = 'TRIAL_SUMMARY_SENT';