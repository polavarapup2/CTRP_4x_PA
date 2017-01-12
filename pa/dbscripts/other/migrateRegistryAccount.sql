CREATE OR REPLACE FUNCTION migrate_registry_account(old_login_name varchar, new_login_name varchar) RETURNS void as $$
DECLARE 
        tr RECORD;
BEGIN
     update csm_user set login_name = new_login_name, password = '', update_date = now() where login_name = old_login_name;
     FOR tr in SELECT * FROM information_schema.tables WHERE table_type = 'BASE TABLE' AND table_schema NOT IN ('pg_catalog', 'information_schema') LOOP
     
     IF (SELECT count(*) FROM information_schema.columns WHERE table_name = tr.table_name and column_name = 'user_last_created') > 0 THEN
        EXECUTE 'update ' || tr.table_name || ' set user_last_created=' || quote_literal(new_login_name) || ', date_last_updated = now() where user_last_created=' || quote_literal(old_login_name) || ';';
        EXECUTE 'update ' || tr.table_name || ' set user_last_updated=' || quote_literal(new_login_name) || ', date_last_updated = now() where user_last_updated=' || quote_literal(old_login_name) || ';';
     END IF;
END LOOP;   
     update study_checkout set user_identifier = new_login_name where user_identifier = old_login_name;
END 
$$ LANGUAGE 'plpgsql';

--To migrate an account, peform the following call.
--select migrate_registry_account('OLD_ACCOUNT_NAME', '/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=NEW_LOGIN_NAME');

