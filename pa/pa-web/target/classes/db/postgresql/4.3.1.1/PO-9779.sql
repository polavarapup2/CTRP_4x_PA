-- Columns
alter table study_protocol add column nci_id varchar; 
alter table study_protocol add column dcp_id varchar;
alter table study_protocol add column ccr_id varchar;
alter table study_protocol add column nct_id varchar;
alter table study_protocol add column ctep_id varchar;
alter table study_protocol add column lead_org_id varchar;

-- NCI ID
CREATE OR REPLACE FUNCTION sync_trial_nci_id() RETURNS TRIGGER AS $t$
    DECLARE    
    v_study_protocol_id integer := 0;
    v_id text :='';
    BEGIN      
	    
	    IF (TG_OP = 'DELETE') THEN
	       v_study_protocol_id = OLD.study_protocol_id;
        ELSIF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN 	       
	     v_study_protocol_id = NEW.study_protocol_id;
	    END IF;
	    
        IF ((TG_OP = 'DELETE' AND OLD.study_protocol_id is not null AND OLD.root='2.16.840.1.113883.3.26.4.3') 
            OR ((TG_OP = 'INSERT' OR TG_OP = 'UPDATE') AND NEW.study_protocol_id is not null AND NEW.root='2.16.840.1.113883.3.26.4.3')) THEN            
            
            select extension into v_id from rv_trial_id_nci where study_protocol_id=v_study_protocol_id;
            update study_protocol set nci_id = v_id where identifier=v_study_protocol_id;
                   
            RETURN null;        
        END IF;
        RETURN NULL; 
    END;
$t$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS sync_trial_nci_id_trigger ON STUDY_OTHERIDENTIFIERS;
CREATE TRIGGER sync_trial_nci_id_trigger AFTER INSERT OR UPDATE OR DELETE
   ON STUDY_OTHERIDENTIFIERS FOR EACH ROW    
    WHEN (pg_trigger_depth() = 0)
    EXECUTE PROCEDURE sync_trial_nci_id(); 
    

UPDATE STUDY_OTHERIDENTIFIERS SET root='2.16.840.1.113883.3.26.4.3' where root='2.16.840.1.113883.3.26.4.3';

-- DCP, CTEP, CCR, NCT IDs
CREATE OR REPLACE FUNCTION sync_trial_identifier_assigner_ids() RETURNS TRIGGER AS $t$
    DECLARE    
    v_study_protocol_id integer := 0;
    v_id text :='';
    BEGIN      
        
        IF (TG_OP = 'DELETE') THEN
           v_study_protocol_id = OLD.study_protocol_identifier;
        ELSIF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN          
         v_study_protocol_id = NEW.study_protocol_identifier;
        END IF;
        
        IF ((TG_OP = 'DELETE' AND OLD.functional_code='IDENTIFIER_ASSIGNER' AND OLD.research_organization_identifier is not null) 
            OR ((TG_OP = 'INSERT' OR TG_OP = 'UPDATE') AND NEW.functional_code='IDENTIFIER_ASSIGNER' AND NEW.research_organization_identifier is not null)) THEN            
            
            update study_protocol set dcp_id = 
                (select local_sp_indentifier from rv_dcp_id where study_protocol_identifier=v_study_protocol_id limit 1)
                where identifier=v_study_protocol_id;
                
            update study_protocol set ccr_id = 
                (select local_sp_indentifier from rv_ccr_id where study_protocol_identifier=v_study_protocol_id limit 1)
                where identifier=v_study_protocol_id;
                
            update study_protocol set nct_id = 
                (select local_sp_indentifier from rv_trial_id_nct where study_protocol_identifier=v_study_protocol_id limit 1)
                where identifier=v_study_protocol_id;                
                
            update study_protocol set ctep_id = 
                (select local_sp_indentifier from rv_ctep_id where study_protocol_identifier=v_study_protocol_id limit 1)
                where identifier=v_study_protocol_id;                

                
            RETURN null;        
        END IF;
        RETURN NULL; 
    END;
$t$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS sync_trial_identifier_assigner_ids_trigger ON STUDY_SITE;
CREATE TRIGGER sync_trial_identifier_assigner_ids_trigger AFTER INSERT OR UPDATE OR DELETE
   ON STUDY_SITE FOR EACH ROW    
    WHEN (pg_trigger_depth() = 0)
    EXECUTE PROCEDURE sync_trial_identifier_assigner_ids(); 
    

UPDATE STUDY_SITE SET functional_code='IDENTIFIER_ASSIGNER' where functional_code='IDENTIFIER_ASSIGNER';

-- Lead Org ID
CREATE OR REPLACE FUNCTION sync_trial_lead_org_id() RETURNS TRIGGER AS $t$
    DECLARE    
    v_study_protocol_id integer := 0;
    v_id text :='';
    BEGIN      
        
        IF (TG_OP = 'DELETE') THEN
           v_study_protocol_id = OLD.study_protocol_identifier;
        ELSIF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN          
         v_study_protocol_id = NEW.study_protocol_identifier;
        END IF;
        
        IF ((TG_OP = 'DELETE' AND OLD.functional_code='LEAD_ORGANIZATION') 
            OR ((TG_OP = 'INSERT' OR TG_OP = 'UPDATE') AND NEW.functional_code='LEAD_ORGANIZATION' )) THEN            
            
            update study_protocol set lead_org_id = 
                (select local_sp_indentifier from rv_lead_organization where study_protocol_identifier=v_study_protocol_id limit 1)
                where identifier=v_study_protocol_id;
                
            RETURN null;        
        END IF;
        RETURN NULL; 
    END;
$t$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS sync_trial_lead_org_id_trigger ON STUDY_SITE;
CREATE TRIGGER sync_trial_lead_org_id_trigger AFTER INSERT OR UPDATE OR DELETE
   ON STUDY_SITE FOR EACH ROW    
    WHEN (pg_trigger_depth() = 0)
    EXECUTE PROCEDURE sync_trial_lead_org_id(); 
    

UPDATE STUDY_SITE SET functional_code='LEAD_ORGANIZATION' where functional_code='LEAD_ORGANIZATION';

