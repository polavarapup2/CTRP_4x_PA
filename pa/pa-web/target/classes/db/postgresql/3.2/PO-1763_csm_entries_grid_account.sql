-- create a grid user for web authentication against grid.
INSERT INTO CSM_USER (LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD, UPDATE_DATE) VALUES ('/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=abstractor','PA','Abstractor','', current_date);
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = '/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=abstractor'), (select group_id from csm_group where group_name = 'Abstractor'));