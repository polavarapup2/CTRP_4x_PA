delete from study_owner;
insert into study_owner (study_id, user_id) 
	select sp.identifier, u.identifier from study_protocol sp 
  		join csm_user csm_u on csm_u.login_name = sp.user_last_created 
  		join registry_user u on csm_u.user_id = u.csm_user_id;
