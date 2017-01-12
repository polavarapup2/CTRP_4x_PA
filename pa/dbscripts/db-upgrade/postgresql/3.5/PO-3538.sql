alter table STUDY_CHECKOUT add column CHECKOUT_TYPE varchar(200);

update STUDY_CHECKOUT set CHECKOUT_TYPE = 'ADMINISTRATIVE';
insert into STUDY_CHECKOUT(identifier, study_protocol_identifier, user_identifier, date_last_created, date_last_updated, user_last_created_id, user_last_updated_id, checkout_type)
(select nextval('hibernate_sequence'), study_protocol_identifier, user_identifier, date_last_created, date_last_updated, user_last_created_id, user_last_updated_id, 'SCIENTIFIC' 
 from STUDY_CHECKOUT);

delete from STUDY_CHECKOUT 
where checkout_type = 'ADMINISTRATIVE' and 
      user_identifier not in(select u.login_name 
                             from csm_user u, csm_user_group ug, csm_group g 
                             where ug.user_id = u.user_id and ug.group_id = g.group_id and g.group_name in ('AdminAbstractor','SuAbstractor'));
delete from STUDY_CHECKOUT 
where checkout_type = 'SCIENTIFIC' and 
      user_identifier not in(select u.login_name 
                             from csm_user u, csm_user_group ug, csm_group g 
                             where ug.user_id = u.user_id and ug.group_id = g.group_id and g.group_name in ('ScientificAbstractor','SuAbstractor'));                             
alter table STUDY_CHECKOUT alter column CHECKOUT_TYPE set not null;