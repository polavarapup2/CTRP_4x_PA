CREATE OR REPLACE FUNCTION generate_pm_sync_ids()
  RETURNS void AS
$BODY$
DECLARE
    v_sync_id integer := 0;
    v_marker_name text := '';
    v_status_code text := '' ;
    v_pm_id integer := 0;
    v_long_name text :='';
    v_description text := '' ;
    v_max_id integer :=0;
    v_status_code_sync text := '';
    
    curs_marker_id CURSOR IS
    SELECT identifier,name,long_name FROM planned_marker WHERE pm_sync_identifier IS NULL and status_code ='ACTIVE';

    curs_pending_marker_id CURSOR IS
    SELECT identifier,name,long_name,status_code FROM planned_marker WHERE pm_sync_identifier IS NULL and status_code ='PENDING';

BEGIN
        OPEN curs_marker_id;
        LOOP
        FETCH curs_marker_id INTO v_pm_id, v_marker_name,v_long_name;
            EXIT WHEN NOT FOUND;
        SELECT IDENTIFIER,  status_code into v_sync_id,v_status_code_sync  FROM planned_marker_sync_cadsr WHERE NAME = v_marker_name;
            IF NOT FOUND THEN
                SELECT text_description INTO v_description FROM  planned_activity WHERE identifier = v_pm_id ;
                INSERT INTO PLANNED_MARKER_SYNC_CADSR (NAME, MEANING, DESCRIPTION, caDSRId, STATUS_CODE) VALUES (v_marker_name,v_long_name,v_description,null,'INACTIVE');      
                SELECT MAX(identifier) INTO v_max_id from planned_marker_sync_cadsr;        
                UPDATE planned_marker SET pm_sync_identifier=v_max_id, status_code = 'DELECTED_IN_CADSR' where identifier = v_pm_id;       
            ELSE
                IF v_status_code_sync = 'INACTIVE' THEN
                    update planned_marker set pm_sync_identifier = v_sync_id, status_code = 'DELECTED_IN_CADSR' where identifier = v_pm_id;
                ELSE
                    update planned_marker set pm_sync_identifier = v_sync_id where identifier = v_pm_id;        
                END IF;
            END IF;   
        END LOOP;
        CLOSE curs_marker_id;

        OPEN curs_pending_marker_id;
        LOOP
        FETCH curs_pending_marker_id INTO v_pm_id, v_marker_name,v_long_name,v_status_code;
            EXIT WHEN NOT FOUND;
        SELECT IDENTIFIER, status_code into v_sync_id, v_status_code_sync FROM planned_marker_sync_cadsr WHERE NAME = v_marker_name;
        
        IF NOT FOUND THEN
            SELECT text_description INTO v_description FROM  planned_activity WHERE identifier = v_pm_id ;
            
            INSERT INTO PLANNED_MARKER_SYNC_CADSR (NAME, MEANING, DESCRIPTION, caDSRId, STATUS_CODE) VALUES (v_marker_name,v_long_name,v_description,null,'PENDING');
            
            SELECT MAX(identifier) INTO v_max_id from planned_marker_sync_cadsr;
            
            UPDATE planned_marker SET pm_sync_identifier=v_max_id where identifier = v_pm_id;     
        ELSE  
            IF v_status_code_sync = 'INACTIVE' THEN
                update planned_marker set pm_sync_identifier = v_sync_id,status_code = 'DELECTED_IN_CADSR' where identifier = v_pm_id;
            ELSE
                update planned_marker set pm_sync_identifier = v_sync_id,status_code = v_status_code_sync where identifier = v_pm_id;
            END IF;            
        END IF;   
        END LOOP;
        CLOSE curs_pending_marker_id;
END;
$BODY$
  LANGUAGE plpgsql;
  
SELECT generate_pm_sync_ids();