-- The following entries creates a super admin application incase you decide 
-- to use this database to run UPT also. In that case you need to provide
-- the project login id and name for the super admin.
-- However in incase you are using this database just to host the application's
-- authorization schema, these enteries are not used and hence they can be left as
-- it is.
--
-- Password is changeme
INSERT INTO CSM_APPLICATION(
APPLICATION_NAME, APPLICATION_DESCRIPTION, DECLARATIVE_FLAG, ACTIVE_FLAG,UPDATE_DATE)
VALUES ( 'csmupt', 'CSM UPT Super Admin Application', '0','0',current_date)
;
INSERT INTO CSM_USER(
LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD,UPDATE_DATE)
VALUES ( 'csmadmin', 'CSM', 'Admin','zJPWCwDeSgG8j2uyHEABIQ==',current_date)
;
INSERT INTO CSM_PROTECTION_ELEMENT(PROTECTION_ELEMENT_NAME, PROTECTION_ELEMENT_DESCRIPTION, OBJECT_ID, APPLICATION_ID,UPDATE_DATE)
VALUES ('csmupt', 'CSM UPT Super Admin Application Protection Element', 'csmupt', 1,current_date)
;
INSERT INTO CSM_USER_PE(
PROTECTION_ELEMENT_ID, USER_ID, UPDATE_DATE)
VALUES ( 1,1,current_date)
;
COMMIT
;
--  
--  The following entry is for your application. 
--  Replace <<application_context_name>> with your application name.
-- 
--  password is ctods
INSERT INTO CSM_APPLICATION(
APPLICATION_NAME, APPLICATION_DESCRIPTION, DECLARATIVE_FLAG, ACTIVE_FLAG,UPDATE_DATE,
DATABASE_URL, DATABASE_USER_NAME,DATABASE_PASSWORD, DATABASE_DIALECT, DATABASE_DRIVER)
VALUES ('pa','pa','0','0',current_date, 'jdbc:postgresql://localhost:5432/ctods_pa', 'pauser',
'BmJz1l2ghRU=', 'org.hibernate.dialect.PostgreSQLDialect', 'org.postgresql.Driver' )
;
INSERT INTO CSM_PROTECTION_ELEMENT(
PROTECTION_ELEMENT_NAME, PROTECTION_ELEMENT_DESCRIPTION, OBJECT_ID, APPLICATION_ID,UPDATE_DATE)
VALUES ( 'pa','PA Admin Application Protection Element','pa',1,current_date)
;
COMMIT
;
-- 
--  The following entries are Common Set of Privileges
-- 
INSERT INTO CSM_PRIVILEGE(PRIVILEGE_NAME, PRIVILEGE_DESCRIPTION,UPDATE_DATE)
VALUES('CREATE','This privilege grants permission to a user to create an entity. This entity can be an object, a database entry, or a resource such as a network connection', current_date)
;
INSERT INTO CSM_PRIVILEGE(PRIVILEGE_NAME, PRIVILEGE_DESCRIPTION,UPDATE_DATE)
VALUES('ACCESS','This privilege allows a user to access a particular resource.  Examples of resources include a network or database connection, socket, module of the application, or even the application itself', current_date)
;
INSERT INTO CSM_PRIVILEGE(PRIVILEGE_NAME, PRIVILEGE_DESCRIPTION,UPDATE_DATE)
VALUES('READ','This privilege permits the user to read data from a file, URL, database, an object, etc. This can be used at an entity level signifying that the user is allowed to read data about a particular entry', current_date)
;
INSERT INTO CSM_PRIVILEGE(PRIVILEGE_NAME, PRIVILEGE_DESCRIPTION,UPDATE_DATE)
VALUES('WRITE','This privilege allows a user to write data to a file, URL, database, an object, etc. This can be used at an entity level signifying that the user is allowed to write data about a particular entity', current_date)
;
INSERT INTO CSM_PRIVILEGE(PRIVILEGE_NAME, PRIVILEGE_DESCRIPTION,UPDATE_DATE)
VALUES('UPDATE','This privilege grants permission at an entity level and signifies that the user is allowed to update data for a particular entity. Entities may include an object, object attribute, database row etc', current_date)
;
INSERT INTO CSM_PRIVILEGE(PRIVILEGE_NAME, PRIVILEGE_DESCRIPTION,UPDATE_DATE)
VALUES('DELETE','This privilege permits a user to delete a logical entity. This entity can be an object, a database entry, a resource such as a network connection, etc', current_date)
;
INSERT INTO CSM_PRIVILEGE(PRIVILEGE_NAME, PRIVILEGE_DESCRIPTION,UPDATE_DATE)
VALUES('EXECUTE','This privilege allows a user to execute a particular resource. The resource can be a method, function, behavior of the application, URL, button etc', current_date)
;
-- Password is pass
INSERT INTO CSM_USER(LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD, UPDATE_DATE) VALUES ('abstractor', 'Test', 'Abstractor','BtM2GNbiAxg=',current_date)
;
INSERT INTO CSM_USER_PE(PROTECTION_ELEMENT_ID, USER_ID, UPDATE_DATE) VALUES (2, 2,current_date)
;
INSERT INTO CSM_GROUP (GROUP_NAME, GROUP_DESC, APPLICATION_ID, UPDATE_DATE) VALUES ('Abstractor', 'Abstractor group - security role', (select application_id from csm_application where application_name = 'pa'),current_date)
;
INSERT INTO CSM_GROUP (GROUP_NAME, GROUP_DESC, APPLICATION_ID, UPDATE_DATE) VALUES ('Submitter', 'Trial Submitter group - security role', (select application_id from csm_application where application_name = 'pa'),current_date)
;
INSERT INTO CSM_GROUP (GROUP_NAME, GROUP_DESC, APPLICATION_ID, UPDATE_DATE) VALUES ('RegAdmin', 'Registry Administrator - security role', (select application_id from csm_application where application_name = 'pa'),current_date)
;
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES (2, 1)
;
-- Password is pass
INSERT INTO CSM_USER(LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD, UPDATE_DATE) VALUES ('subscriber', 'Test', 'Subscriber','BtM2GNbiAxg=',current_date)
;
INSERT INTO CSM_USER_PE(PROTECTION_ELEMENT_ID, USER_ID, UPDATE_DATE) VALUES ((select protection_element_id from csm_protection_element where protection_element_name = 'pa'), (select user_id from csm_user where login_name = 'subscriber'),current_date)
;
INSERT INTO CSM_GROUP (GROUP_NAME, GROUP_DESC, APPLICATION_ID, UPDATE_DATE) VALUES ('Subscriber', 'Topic Subscriber group - security role', (select application_id from csm_application where application_name = 'pa'),current_date)
;
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'subscriber'), (select group_id from csm_group where group_name = 'Subscriber'))
;
-- Password is pass
INSERT INTO CSM_USER(LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD, UPDATE_DATE) VALUES ('publisher', 'Test', 'Publisher','BtM2GNbiAxg=',current_date)
;
INSERT INTO CSM_USER_PE(PROTECTION_ELEMENT_ID, USER_ID, UPDATE_DATE) VALUES ((select protection_element_id from csm_protection_element where protection_element_name = 'pa'), (select user_id from csm_user where login_name = 'publisher'),current_date)
;
INSERT INTO CSM_GROUP (GROUP_NAME, GROUP_DESC, APPLICATION_ID, UPDATE_DATE) VALUES ('Publisher', 'Topic Publisher group - security role', (select application_id from csm_application where application_name = 'pa'),current_date)
;
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'publisher'), (select group_id from csm_group where group_name = 'Publisher'))
;
-- Password is pass
INSERT INTO CSM_USER(LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD, UPDATE_DATE) VALUES ('ejbclient', 'Test', 'EJBClient','BtM2GNbiAxg=',current_date)
;
INSERT INTO CSM_USER_PE(PROTECTION_ELEMENT_ID, USER_ID, UPDATE_DATE) VALUES ((select protection_element_id from csm_protection_element where protection_element_name = 'pa'), (select user_id from csm_user where login_name = 'ejbclient'),current_date)
;
INSERT INTO CSM_GROUP (GROUP_NAME, GROUP_DESC, APPLICATION_ID, UPDATE_DATE) VALUES ('client', 'remote client group - security role', (select application_id from csm_application where application_name = 'pa'),current_date)
;
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'ejbclient'), (select group_id from csm_group where group_name = 'client'))
;
-- password is pass
INSERT INTO CSM_USER(LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD, UPDATE_DATE) VALUES ('firebird-nci', 'Firebird', 'Nci','BtM2GNbiAxg=',current_date)
;
INSERT INTO CSM_USER_PE(PROTECTION_ELEMENT_ID, USER_ID, UPDATE_DATE) VALUES ((select protection_element_id from csm_protection_element where protection_element_name = 'pa'), (select user_id from csm_user where login_name = 'firebird-nci'),current_date)
;
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'firebird-nci'), (select group_id from csm_group where group_name = 'client'))
;
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'firebird-nci'), (select group_id from csm_group where group_name = 'Subscriber'))
;

-- Password is pass
INSERT INTO CSM_USER(LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD, UPDATE_DATE) VALUES ('submitter', 'Test', 'Submitter','BtM2GNbiAxg=',current_date)
;
INSERT INTO CSM_USER_PE(PROTECTION_ELEMENT_ID, USER_ID, UPDATE_DATE) VALUES ((select protection_element_id from csm_protection_element where protection_element_name = 'pa'), (select user_id from csm_user where login_name = 'submitter'),current_date)
;
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'submitter'), (select group_id from csm_group where group_name = 'Submitter'))
;
-- grid user for abstractor
INSERT INTO CSM_USER(LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD, UPDATE_DATE) VALUES ('/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=abstractor', 'Test', 'PA Abstractor','',current_date)
;
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = '/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=abstractor'), (select group_id from csm_group where group_name = 'Abstractor'))
;
-- grid user for submitter
INSERT INTO CSM_USER(LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD, UPDATE_DATE) VALUES ('/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=submitter', 'Test', 'PA Submitter','',current_date)
;
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = '/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=submitter'), (select group_id from csm_group where group_name = 'Submitter'))
;

--Remote Groups for PA in the dev environment.
insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'Abstractor'), (select application_id from csm_application where application_name = 'pa'), 'https://grouper.training.cagrid.org:8443/wsrf/services/cagrid/GridGrouper', 'COPPA:PA:Abstractor');
insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'Submitter'), (select application_id from csm_application where application_name = 'pa'), 'https://grouper.training.cagrid.org:8443/wsrf/services/cagrid/GridGrouper', 'COPPA:PA:Submitter');
insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'RegAdmin'), (select application_id from csm_application where application_name = 'pa'), 'https://grouper.training.cagrid.org:8443/wsrf/services/cagrid/GridGrouper', 'COPPA:PA:RegAdmin');
--The below commands are commented out due to the fact that they are added later in the upgrade process. If you need to add these remote group mappings, do so manually.
--insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'gridClient'), (select application_id from csm_application where application_name = 'pa'), 'https://grouper.training.cagrid.org:8443/wsrf/services/cagrid/GridGrouper', 'COPPA:PA:GridClient');
--insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'Outcomes'), (select application_id from csm_application where application_name = 'pa'), 'https://grouper.training.cagrid.org:8443/wsrf/services/cagrid/GridGrouper', 'COPPA:PA:Outcomes');
COMMIT
;