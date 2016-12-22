CREATE TYPE roles AS (group_name character varying(255), roles character varying(255));

CREATE OR REPLACE FUNCTION getRoles(login_name character varying(255)) RETURNS setof roles AS '
DECLARE
BEGIN
    IF (SELECT COUNT(*) FROM csm_group cg, csm_user_group cug, csm_user cu WHERE cg.group_id = cug.group_id AND cug.user_id = cu.user_id AND cu.login_name = login_name) > 0 THEN 
        RETURN QUERY SELECT cg.group_name, CAST (''Roles'' AS character varying(255)) FROM csm_group cg, csm_user_group cug, csm_user cu WHERE cg.group_id = cug.group_id AND cug.user_id = cu.user_id AND cu.login_name = login_name;
        RETURN;
    END IF;
    RETURN QUERY SELECT CAST (''Unregistered'' AS character varying(255)), CAST (''Roles'' AS character varying(255));
    RETURN;
END;
' LANGUAGE plpgsql;

