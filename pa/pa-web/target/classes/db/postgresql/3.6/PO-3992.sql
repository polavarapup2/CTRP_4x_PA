update study_protocol_stage set site_recruitment_status='APPROVED' where site_recruitment_status = 'NOT_YET_RECRUITING';  
update study_protocol_stage set site_recruitment_status='ACTIVE' where site_recruitment_status = 'RECRUITING';
update study_protocol_stage set site_recruitment_status='TEMPORARILY_CLOSED_TO_ACCRUAL' where site_recruitment_status = 'SUSPENDED_RECRUITING';
update study_protocol_stage set site_recruitment_status='CLOSED_TO_ACCRUAL' where site_recruitment_status = 'ACTIVE_NOT_RECRUITING';
update study_protocol_stage set site_recruitment_status='ADMINISTRATIVELY_COMPLETE' where site_recruitment_status = 'TERMINATED_RECRUITING';
