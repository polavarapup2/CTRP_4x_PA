create or replace function avoid_duplicate_ps_insert() returns trigger as $avoid_duplicate_ps_insert$
DECLARE
 v_new_poid text :='';
 v_ss_id integer :=0;

BEGIN
 IF (TG_OP = 'INSERT' AND NEW.functional_code='TREATING_SITE' AND NEW.healthcare_facility_identifier is not null) THEN

        SELECT org.assigned_identifier into v_new_poid FROM study_site  ss 
	join study_protocol sp on ss.study_protocol_identifier = sp.identifier 
	join healthcare_facility hcf on hcf.identifier=ss.healthcare_facility_identifier
	join organization org on org.identifier =hcf.organization_identifier where 
	ss.functional_code='TREATING_SITE' and sp.status_code='ACTIVE' and ss.status_code<> 'INACTIVE' 
	and ss.study_protocol_identifier=NEW.study_protocol_identifier AND ss.identifier <> NEW.identifier AND ss.healthcare_facility_identifier in (select identifier from healthcare_facility  where 
	organization_identifier =(select organization_identifier from healthcare_facility 
	where identifier  = NEW.healthcare_facility_identifier)
	and status_code<>'NULLIFIED');	
	IF FOUND THEN
		delete from study_site where identifier=NEW.identifier;
		RAISE EXCEPTION 'Can not add duplicate Participating site';
	END IF;
  END IF;
return NEW;	
END;
$avoid_duplicate_ps_insert$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS study_site_avoid_duplicate_ps_insert_trigger ON study_site;
CREATE TRIGGER study_site_avoid_duplicate_ps_insert_trigger AFTER INSERT OR UPDATE
    ON study_site FOR EACH ROW
    EXECUTE PROCEDURE avoid_duplicate_ps_insert();



create or replace function avoid_duplicate_ps_update() returns trigger as $avoid_duplicate_ps_update$
DECLARE
 v_new_poid text :=''; 
 v_ss_id integer :=0;
BEGIN
 IF (TG_OP = 'UPDATE' and NEW.study_protocol_identifier = OLD.study_protocol_identifier AND 
         NEW.healthcare_facility_identifier <> OLD.healthcare_facility_identifier) THEN
        SELECT ss.identifier into v_ss_id FROM study_site  ss 
	join study_protocol sp on ss.study_protocol_identifier = sp.identifier 
	join healthcare_facility hcf on hcf.identifier=ss.healthcare_facility_identifier
	join organization org on org.identifier =hcf.organization_identifier where 
	ss.functional_code='TREATING_SITE' and sp.status_code='ACTIVE' and ss.status_code<> 'INACTIVE' 
	and ss.study_protocol_identifier=NEW.study_protocol_identifier AND ss.identifier <> NEW.identifier AND ss.healthcare_facility_identifier in 
	(select identifier from healthcare_facility  where 
	organization_identifier =(select organization_identifier from healthcare_facility 
	where identifier  = NEW.healthcare_facility_identifier)
	and status_code<>'NULLIFIED');
	IF FOUND THEN
	     RAISE EXCEPTION 'Can not add duplicate Participating site';
	END IF;
 END IF; 
return NEW;	
END;
$avoid_duplicate_ps_update$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS study_site_avoid_duplicate_ps_update_trigger ON study_site;
CREATE TRIGGER study_site_avoid_duplicate_ps_update_trigger BEFORE INSERT OR UPDATE
    ON study_site FOR EACH ROW
    EXECUTE PROCEDURE avoid_duplicate_ps_update();









