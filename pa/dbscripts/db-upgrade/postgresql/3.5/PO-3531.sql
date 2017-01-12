insert into study_milestone(identifier, comment_text, milestone_code, milestone_date, study_protocol_identifier, date_last_created, date_last_updated, user_last_created_id, user_last_updated_id)
(select nextval('hibernate_sequence'), comment_text, 'ADMINISTRATIVE_READY_FOR_QC', milestone_date, study_protocol_identifier, date_last_created, date_last_updated, user_last_created_id, user_last_updated_id
 from study_milestone where milestone_code = 'READY_FOR_QC');
 
 update study_milestone set milestone_code = 'SCIENTIFIC_READY_FOR_QC' where milestone_code = 'READY_FOR_QC';

insert into study_milestone(identifier, comment_text, milestone_code, milestone_date, study_protocol_identifier, date_last_created, date_last_updated, user_last_created_id, user_last_updated_id)
(select nextval('hibernate_sequence'), comment_text, 'ADMINISTRATIVE_QC_START', milestone_date, study_protocol_identifier, date_last_created, date_last_updated, user_last_created_id, user_last_updated_id
 from study_milestone where milestone_code = 'QC_START');
 
update study_milestone set milestone_code = 'SCIENTIFIC_QC_START' where milestone_code = 'QC_START';

insert into study_milestone(identifier, comment_text, milestone_code, milestone_date, study_protocol_identifier, date_last_created, date_last_updated, user_last_created_id, user_last_updated_id)
(select nextval('hibernate_sequence'), comment_text, 'ADMINISTRATIVE_QC_COMPLETE', milestone_date, study_protocol_identifier, date_last_created, date_last_updated, user_last_created_id, user_last_updated_id
 from study_milestone where milestone_code = 'QC_COMPLETE');
 
 insert into study_milestone(identifier, comment_text, milestone_code, milestone_date, study_protocol_identifier, date_last_created, date_last_updated, user_last_created_id, user_last_updated_id)
(select nextval('hibernate_sequence'), comment_text, 'READY_FOR_TSR', milestone_date, study_protocol_identifier, date_last_created, date_last_updated, user_last_created_id, user_last_updated_id
 from study_milestone where milestone_code = 'QC_COMPLETE');
 
update study_milestone set milestone_code = 'SCIENTIFIC_QC_COMPLETE' where milestone_code = 'QC_COMPLETE';