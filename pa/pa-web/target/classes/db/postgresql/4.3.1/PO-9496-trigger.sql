update study_protocol set DELAYED_POSTING_INDICATOR = false where DELAYED_POSTING_INDICATOR is null;


create or replace function set_delayed_flag_to_false() returns trigger as $set_delayed_flag_to_false$
BEGIN
 IF (NEW.DELAYED_POSTING_INDICATOR is  null) THEN
 update study_protocol set DELAYED_POSTING_INDICATOR = false where identifier =NEW.identifier;
  END IF;
return NEW; 
END;
$set_delayed_flag_to_false$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS set_delayed_flag_to_false_insert_update_trigger ON study_protocol;
CREATE TRIGGER set_delayed_flag_to_false_insert_update_trigger AFTER INSERT OR UPDATE
    ON study_protocol FOR EACH ROW
    WHEN (pg_trigger_depth() = 0)
    EXECUTE PROCEDURE set_delayed_flag_to_false();