CREATE OR REPLACE FUNCTION change_loginnames_to_userids() RETURNS void as $$
DECLARE 
     tr RECORD;
BEGIN
     FOR tr in SELECT * FROM information_schema.tables WHERE table_type = 'BASE TABLE' AND table_schema NOT IN ('pg_catalog', 'information_schema') LOOP
     
     IF (SELECT count(*) FROM information_schema.columns WHERE table_name = tr.table_name and column_name = 'user_last_created') > 0 THEN
        EXECUTE 'ALTER TABLE ' || tr.table_name || ' ADD COLUMN user_last_created_id INTEGER' || ',' ||
                ' ADD CONSTRAINT FK_' || tr.table_name || '_CREATED_CSM_USER FOREIGN KEY (USER_LAST_CREATED_ID) REFERENCES CSM_USER (USER_ID);'; 
        EXECUTE 'UPDATE ' || tr.table_name || ' s set user_last_created_id = u.user_id from csm_user u where s.user_last_created = u.login_name and s.user_last_created is not null;';
        EXECUTE 'ALTER TABLE ' || tr.table_name || ' DROP COLUMN user_last_created;';
     END IF;
     IF (SELECT count(*) FROM information_schema.columns WHERE table_name = tr.table_name and column_name = 'user_last_updated') > 0 THEN
        EXECUTE 'ALTER TABLE ' || tr.table_name || ' ADD COLUMN user_last_updated_id INTEGER' || ',' || 
                ' ADD CONSTRAINT FK_' || tr.table_name || '_UPDATED_CSM_USER FOREIGN KEY (USER_LAST_UPDATED_ID) REFERENCES CSM_USER (USER_ID);';
        EXECUTE 'UPDATE ' || tr.table_name || ' s set user_last_updated_id = u.user_id from csm_user u where s.user_last_updated = u.login_name and s.user_last_updated is not null;';
        EXECUTE 'ALTER TABLE ' || tr.table_name || ' DROP COLUMN user_last_updated;';
     END IF;
     END LOOP;
END;
$$ LANGUAGE 'plpgsql';

select change_loginnames_to_userids();
drop function change_loginnames_to_userids();

