--Adding test registry users
insert into csm_user(login_name, first_name, last_name, PASSWORD) values ('abstractor-ci', '', '', 'BtM2GNbiAxg=');
insert into registry_user(identifier, first_name, last_name, address_line, city, state, postal_code, country, phone , affiliate_org, csm_user_id, affiliated_org_id, affiliated_org_user_type, email_address)
    values(nextval('hibernate_sequence'), 'Abstractor', 'User', '2115 E. Jefferson St.', 'North Bethesda', 'Maryland', '20852', 'USA', '123-456-7890', 
        'National Cancer Institute Division of Cancer Prevention', (select user_id from csm_user where login_name = 'abstractor-ci'), 3, 'ADMIN', 'abstractor-ci@example.com');
        
insert into csm_user(login_name, first_name, last_name, PASSWORD) values ('submitter-ci', '', '', 'BtM2GNbiAxg=');
insert into registry_user(identifier, first_name, last_name, address_line, city, state, postal_code, country, phone , affiliate_org, csm_user_id, affiliated_org_id, affiliated_org_user_type, email_address)
    values(nextval('hibernate_sequence'), 'Submitter', 'CI', '2115 E. Jefferson St.', 'North Bethesda', 'Maryland', '20852', 'USA', '123-456-7890', 
        'National Cancer Institute Division of Cancer Prevention', (select user_id from csm_user where login_name = 'submitter-ci'), 3, 'MEMBER', 'submitter-ci@example.com');
        
insert into csm_user(login_name, first_name, last_name, PASSWORD) values ('ctrpsubstractor', '', '', 'BtM2GNbiAxg=');
insert into registry_user(identifier, first_name, last_name, address_line, city, state, postal_code, country, phone , affiliate_org, csm_user_id, affiliated_org_id, affiliated_org_user_type, email_address)
    values(nextval('hibernate_sequence'), 'ctrpsubstractor', 'CI', '2115 E. Jefferson St.', 'North Bethesda', 'Maryland', '20852', 'USA', '123-456-7890', 
        'National Cancer Institute Division of Cancer Prevention', (select user_id from csm_user where login_name = 'ctrpsubstractor'), 3, 'MEMBER', 'ctrpsubstractor-ci@example.com');        

insert into csm_user(login_name, first_name, last_name, PASSWORD) values ('scientific-ci', '', '', 'BtM2GNbiAxg=');
insert into csm_user(login_name, first_name, last_name, PASSWORD) values ('admin-ci', '', '', 'BtM2GNbiAxg=');
insert into csm_user(login_name, first_name, last_name, PASSWORD) values ('curator', '', '', 'BtM2GNbiAxg=');
insert into csm_user(login_name, first_name, last_name, PASSWORD) values ('results-abstractor', '', '', 'BtM2GNbiAxg=');
insert into csm_user(login_name, first_name, last_name, PASSWORD) values ('multiroleuser', '', '', 'BtM2GNbiAxg=');
        
DELETE FROM csm_user where login_name='/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=abstractor';
DELETE FROM csm_user where login_name='/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=submitter';
        
DELETE FROM csm_remote_group;
INSERT INTO CSM_GROUP (GROUP_NAME, GROUP_DESC, APPLICATION_ID, UPDATE_DATE) VALUES ('test', 'test', (select application_id from csm_application where application_name = 'pa'),current_date);
INSERT INTO csm_remote_group (group_id,application_id,grid_grouper_url,grid_grouper_group_name) VALUES ((select group_id from csm_group where group_name='test'),(select application_id from csm_application where application_name='pa'),'https://cagrid2-gridgrouper-qa:8443/wsrf/services/cagrid/GridGrouper','Organization:CBIIT:CTRP-COPPA:PA:SuAbstractor');

INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'abstractor-ci'), (select group_id from csm_group where group_name = 'RegAdmin'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'abstractor-ci'), (select group_id from csm_group where group_name = 'Submitter'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'abstractor-ci'), (select group_id from csm_group where group_name = 'AdminAbstractor'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'abstractor-ci'), (select group_id from csm_group where group_name = 'ScientificAbstractor'));

INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'submitter-ci'), (select group_id from csm_group where group_name = 'Submitter'));

INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'ctrpsubstractor'), (select group_id from csm_group where group_name = 'SuAbstractor'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'ctrpsubstractor'), (select group_id from csm_group where group_name = 'Submitter'));

INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'admin-ci'), (select group_id from csm_group where group_name = 'Submitter'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'admin-ci'), (select group_id from csm_group where group_name = 'AdminAbstractor'));



INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'scientific-ci'), (select group_id from csm_group where group_name = 'Submitter'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'scientific-ci'), (select group_id from csm_group where group_name = 'ScientificAbstractor'));


INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'curator'), (select group_id from csm_group where group_name = 'test'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'curator'), (select group_id from csm_group where group_name = 'client'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'results-abstractor'), (select group_id from csm_group where group_name = 'ResultsAbstractor'));

INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'multiroleuser'), (select group_id from csm_group where group_name = 'ResultsAbstractor'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'multiroleuser'), (select group_id from csm_group where group_name = 'SuAbstractor'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'multiroleuser'), (select group_id from csm_group where group_name = 'AdminAbstractor'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'multiroleuser'), (select group_id from csm_group where group_name = 'ScientificAbstractor'));


UPDATE pa_properties SET value='example@example.com' WHERE value in ('@abstraction.script.mailTo@','@cde.request.to.email@','@ctrp.support.email@','@mail.from@');



        
