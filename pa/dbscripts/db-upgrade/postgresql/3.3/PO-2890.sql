delete from registry_user where csm_user_id not in (select user_id from csm_user);
