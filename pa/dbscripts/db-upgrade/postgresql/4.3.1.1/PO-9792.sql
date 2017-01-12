alter table study_overall_status drop column if exists position_in_history cascade;
alter table study_overall_status add position_in_history integer;

CREATE OR REPLACE FUNCTION study_overall_status_set_position_in_history() RETURNS TRIGGER AS $t$
   DECLARE
   
    v_study_protocol_id bigint := 0;  
    v_sos_id bigint := 0;
    v_position_in_history integer := 0 ;
    
    curs_sos_history CURSOR (sp_id bigint) IS
        SELECT identifier FROM
            study_overall_status where  study_protocol_identifier = sp_id  AND deleted = false
            ORDER BY status_date ASC, identifier ASC;           
        
    BEGIN       
	    
        IF (TG_OP = 'DELETE') THEN
           v_study_protocol_id = OLD.study_protocol_identifier;
        ELSIF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN          
         v_study_protocol_id = NEW.study_protocol_identifier;
        END IF;
	    
        OPEN curs_sos_history(v_study_protocol_id);
        LOOP FETCH curs_sos_history INTO v_sos_id;
            EXIT WHEN NOT FOUND;            
            update study_overall_status set position_in_history = v_position_in_history where identifier = v_sos_id;
            v_position_in_history = v_position_in_history + 1;            
        END LOOP; 
        CLOSE curs_sos_history;
        
        UPDATE study_overall_status SET position_in_history = null where study_protocol_identifier = v_study_protocol_id and deleted=true;
        
        RETURN NULL; 
    END;
$t$ LANGUAGE plpgsql;


DROP TRIGGER IF EXISTS study_overall_status_set_position_in_history_trigger ON study_overall_status;
CREATE TRIGGER study_overall_status_set_position_in_history_trigger AFTER INSERT OR UPDATE OR DELETE
   ON study_overall_status FOR EACH ROW    
   WHEN (pg_trigger_depth() = 0)
   EXECUTE PROCEDURE study_overall_status_set_position_in_history();
    
UPDATE study_overall_status SET current=true where current=true;