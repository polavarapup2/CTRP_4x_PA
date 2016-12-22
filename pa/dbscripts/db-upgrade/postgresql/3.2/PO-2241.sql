CREATE OR REPLACE FUNCTION fix_trial_ownership() RETURNS void as $$
DECLARE 
    tr RECORD;
BEGIN
	FOR tr IN SELECT * FROM study_protocol where user_last_created is not null LOOP
	   IF (SELECT count(*) from study_owner WHERE study_id = tr.identifier) = 0
	       AND (SELECT count(ru.csm_user_id) from csm_user cu left outer join registry_user ru on cu.user_id = ru.csm_user_id where cu.login_name = tr.user_last_created) > 0 THEN
	       INSERT INTO study_owner (STUDY_ID, USER_ID) VALUES (tr.identifier, (SELECT ru.identifier FROM registry_user ru join csm_user cu on ru.csm_user_id = cu.user_id where cu.login_name = trim(tr.user_last_created)));
	   END IF;
	END LOOP;
END;
$$ LANGUAGE 'plpgsql';

select fix_trial_ownership();

drop function fix_trial_ownership();