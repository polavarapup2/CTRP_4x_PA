--dropping the trigger should have been done as part of PO-1687_migration_to_4.1
DROP TRIGGER set_csm_user_pe_update_date ON csm_user_pe;

INSERT INTO CSM_USER(LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD, UPDATE_DATE) VALUES ('unspecifieduser', 'Unspecified', 'User','BtM2GNbiAxg=',current_date);

INSERT INTO CSM_USER_PE(PROTECTION_ELEMENT_ID, USER_ID) VALUES ((select protection_element_id from csm_protection_element where protection_element_name = 'pa' and application_id  in (select application_id from csm_application where application_name='csmupt'))
, (select user_id from csm_user where login_name = 'unspecifieduser'));