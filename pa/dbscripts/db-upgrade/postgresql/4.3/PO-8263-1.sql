DROP TRIGGER IF EXISTS study_milestone_set_active_trigger ON study_milestone;
DROP TRIGGER IF EXISTS study_milestone_set_admin_trigger ON study_milestone;
DROP TRIGGER IF EXISTS study_milestone_set_last_trigger ON study_milestone;
DROP TRIGGER IF EXISTS study_milestone_set_other_trigger ON study_milestone;
DROP TRIGGER IF EXISTS study_milestone_set_scientific_trigger ON study_milestone;
DROP TRIGGER IF EXISTS study_overall_status_set_current_trigger ON study_overall_status;

DROP INDEX IF EXISTS sm_active;
DROP INDEX IF EXISTS sm_admin;
DROP INDEX IF EXISTS sm_last;
DROP INDEX IF EXISTS sm_other;
DROP INDEX IF EXISTS sm_scientific;
DROP INDEX IF EXISTS sos_current;
DROP INDEX IF EXISTS sm_milestone_code;
DROP INDEX IF EXISTS sm_milestone_date;
DROP INDEX IF EXISTS STUDY_MILESTONE_STUDY_PROTOCOL_IDX;


alter table study_milestone drop column if exists active cascade;
alter table study_milestone add column active boolean;

alter table study_milestone drop column if exists admin cascade;
alter table study_milestone add column admin boolean;

alter table study_milestone drop column if exists last cascade;
alter table study_milestone add column last boolean;

alter table study_milestone drop column if exists other cascade;
alter table study_milestone add column other boolean;

alter table study_milestone drop column if exists scientific cascade;
alter table study_milestone add column scientific boolean;

alter table study_overall_status drop column if exists current cascade;
alter table study_overall_status add column current boolean;

-- Active flag.
CREATE OR REPLACE FUNCTION set_active_study_milestone() RETURNS TRIGGER AS $t$
    BEGIN       
        IF (TG_OP = 'DELETE') THEN
            update study_milestone sm1 set active = false where sm1.study_protocol_identifier=OLD.study_protocol_identifier;
			update study_milestone sm1 set active = true WHERE sm1.study_protocol_identifier=OLD.study_protocol_identifier AND 
			    sm1.identifier = (
			                      SELECT
			                        sm2.identifier
			                      FROM study_milestone sm2
			                      WHERE
			                      (
			                         (OLD.study_protocol_identifier = sm2.study_protocol_identifier)
			                         AND
			                         (
			                            (
			                               sm2.milestone_code
			                            )
			                            ::text <> ALL
			                            (
			                               ARRAY[('SUBMISSION_TERMINATED'::character varying)::text,
			                               ('SUBMISSION_REACTIVATED'::character varying)::text]
			                            )
			                         )
			                      )
			                      ORDER BY sm2.milestone_date DESC, sm2.identifier DESC LIMIT 1
			                   );   
			RETURN null;
        ELSIF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN 
            update study_milestone sm1 set active = false where sm1.study_protocol_identifier = NEW.study_protocol_identifier;
            update study_milestone sm1 set active = true  WHERE sm1.study_protocol_identifier = NEW.study_protocol_identifier AND 
                sm1.identifier = (
                                  SELECT
                                    sm2.identifier
                                  FROM study_milestone sm2
                                  WHERE
                                  (
                                     (NEW.study_protocol_identifier = sm2.study_protocol_identifier)
                                     AND
                                     (
                                        (
                                           sm2.milestone_code
                                        )
                                        ::text <> ALL
                                        (
                                           ARRAY[('SUBMISSION_TERMINATED'::character varying)::text,
                                           ('SUBMISSION_REACTIVATED'::character varying)::text]
                                        )
                                     )
                                  )
                                  ORDER BY sm2.milestone_date DESC, sm2.identifier DESC LIMIT 1
                               );    
            RETURN NULL; 
        END IF;
        RETURN NULL; 
    END;
$t$ LANGUAGE plpgsql;
    
CREATE OR REPLACE VIEW rv_active_milestone AS 
 SELECT sm1.study_protocol_identifier, sm1.milestone_code, sm1.milestone_date
   FROM study_milestone sm1
  WHERE sm1.active=true;    
  
  
-- Admin flag                   
CREATE OR REPLACE FUNCTION set_admin_study_milestone() RETURNS TRIGGER AS $t$
    BEGIN       
        IF (TG_OP = 'DELETE') THEN
            update study_milestone sm1 set admin = false where sm1.study_protocol_identifier=OLD.study_protocol_identifier;
            update study_milestone sm1 set admin = true WHERE sm1.study_protocol_identifier=OLD.study_protocol_identifier AND 
                (
				   (
				      sm1.milestone_date IN
				      (
				         SELECT
				         max(sm2.milestone_date) AS max
				         FROM study_milestone sm2
				         WHERE
				         (
				            (sm1.study_protocol_identifier = sm2.study_protocol_identifier)
				            AND
				            (
				               (
				                  (
				                     sm2.milestone_code
				                  )
				                  ::text = ANY
				                  (
				                     ARRAY[('ADMINISTRATIVE_PROCESSING_START_DATE'::character varying)::text,
				                     ('ADMINISTRATIVE_PROCESSING_COMPLETED_DATE'::character varying)::text,
				                     ('ADMINISTRATIVE_READY_FOR_QC'::character varying)::text,
				                     ('ADMINISTRATIVE_QC_START'::character varying)::text,
				                     ('ADMINISTRATIVE_QC_COMPLETE'::character varying)::text]
				                  )
				               )
				               OR
				               (
				                  (
				                     sm2.milestone_code
				                  )
				                  ::text = ANY
				                  (
				                     ARRAY[('READY_FOR_TSR'::character varying)::text,
				                     ('TRIAL_SUMMARY_REPORT'::character varying)::text,
				                     ('INITIAL_ABSTRACTION_VERIFY'::character varying)::text,
				                     ('TRIAL_SUMMARY_FEEDBACK'::character varying)::text,
				                     ('ONGOING_ABSTRACTION_VERIFICATION'::character varying)::text,
				                     ('LATE_REJECTION_DATE'::character varying)::text]
				                  )
				               )
				            )
				         )
				      )
				   )
				   AND
				   (
				      (
				         sm1.milestone_code
				      )
				      ::text = ANY
				      (
				         ARRAY[('ADMINISTRATIVE_PROCESSING_START_DATE'::character varying)::text,
				         ('ADMINISTRATIVE_PROCESSING_COMPLETED_DATE'::character varying)::text,
				         ('ADMINISTRATIVE_READY_FOR_QC'::character varying)::text,
				         ('ADMINISTRATIVE_QC_START'::character varying)::text,
				         ('ADMINISTRATIVE_QC_COMPLETE'::character varying)::text]
				      )
				   )
				);   
            RETURN null;
            
        ELSIF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN 
            update study_milestone sm1 set admin = false where sm1.study_protocol_identifier = NEW.study_protocol_identifier;
            update study_milestone sm1 set admin = true  WHERE sm1.study_protocol_identifier = NEW.study_protocol_identifier AND 
                (
					   (
					      sm1.milestone_date IN
					      (
					         SELECT
					         max(sm2.milestone_date) AS max
					         FROM study_milestone sm2
					         WHERE
					         (
					            (sm1.study_protocol_identifier = sm2.study_protocol_identifier)
					            AND
					            (
					               (
					                  (
					                     sm2.milestone_code
					                  )
					                  ::text = ANY
					                  (
					                     ARRAY[('ADMINISTRATIVE_PROCESSING_START_DATE'::character varying)::text,
					                     ('ADMINISTRATIVE_PROCESSING_COMPLETED_DATE'::character varying)::text,
					                     ('ADMINISTRATIVE_READY_FOR_QC'::character varying)::text,
					                     ('ADMINISTRATIVE_QC_START'::character varying)::text,
					                     ('ADMINISTRATIVE_QC_COMPLETE'::character varying)::text]
					                  )
					               )
					               OR
					               (
					                  (
					                     sm2.milestone_code
					                  )
					                  ::text = ANY
					                  (
					                     ARRAY[('READY_FOR_TSR'::character varying)::text,
					                     ('TRIAL_SUMMARY_REPORT'::character varying)::text,
					                     ('INITIAL_ABSTRACTION_VERIFY'::character varying)::text,
					                     ('TRIAL_SUMMARY_FEEDBACK'::character varying)::text,
					                     ('ONGOING_ABSTRACTION_VERIFICATION'::character varying)::text,
					                     ('LATE_REJECTION_DATE'::character varying)::text]
					                  )
					               )
					            )
					         )
					      )
					   )
					   AND
					   (
					      (
					         sm1.milestone_code
					      )
					      ::text = ANY
					      (
					         ARRAY[('ADMINISTRATIVE_PROCESSING_START_DATE'::character varying)::text,
					         ('ADMINISTRATIVE_PROCESSING_COMPLETED_DATE'::character varying)::text,
					         ('ADMINISTRATIVE_READY_FOR_QC'::character varying)::text,
					         ('ADMINISTRATIVE_QC_START'::character varying)::text,
					         ('ADMINISTRATIVE_QC_COMPLETE'::character varying)::text]
					      )
					   )
					);    
            RETURN NULL; 
        END IF;
        RETURN NULL; 
    END;
$t$ LANGUAGE plpgsql;
  
    
CREATE OR REPLACE VIEW rv_admin_milestone AS 
	 SELECT	sm1.study_protocol_identifier, sm1.milestone_code, sm1.milestone_date FROM study_milestone sm1	WHERE sm1.admin=true;

	 
-- Last flag.                   
CREATE OR REPLACE FUNCTION set_last_study_milestone() RETURNS TRIGGER AS $t$
    BEGIN       
        IF (TG_OP = 'DELETE') THEN
            update study_milestone sm1 set last = false where sm1.study_protocol_identifier=OLD.study_protocol_identifier;
            update study_milestone sm1 set last = true WHERE sm1.study_protocol_identifier=OLD.study_protocol_identifier AND 
                    (
					   sm1.identifier IN
						   (
						      SELECT
						      sm2.identifier
						      FROM study_milestone sm2
						      WHERE (sm1.study_protocol_identifier = sm2.study_protocol_identifier)
						      ORDER BY sm2.milestone_date DESC, sm2.identifier DESC LIMIT 1
						   )
						);   
            RETURN null;
        ELSIF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN 
            update study_milestone sm1 set last = false where sm1.study_protocol_identifier = NEW.study_protocol_identifier;
            update study_milestone sm1 set last = true  WHERE sm1.study_protocol_identifier = NEW.study_protocol_identifier AND 
                    (
                       sm1.identifier IN
                           (
                              SELECT
                              sm2.identifier
                              FROM study_milestone sm2
                              WHERE (sm1.study_protocol_identifier = sm2.study_protocol_identifier)
                              ORDER BY sm2.milestone_date DESC, sm2.identifier DESC LIMIT 1
                           )
                        );    
            RETURN NULL; 
        END IF;
        RETURN NULL; 
    END;
$t$ LANGUAGE plpgsql;
  
    
CREATE OR REPLACE VIEW rv_last_milestone AS 
 SELECT sm1.study_protocol_identifier, sm1.milestone_code, sm1.milestone_date FROM study_milestone sm1 WHERE sm1.last=true;
 
 
-- Other flag.                   
CREATE OR REPLACE FUNCTION set_other_study_milestone() RETURNS TRIGGER AS $t$
    BEGIN       
        IF (TG_OP = 'DELETE') THEN
            update study_milestone sm1 set other = false where sm1.study_protocol_identifier=OLD.study_protocol_identifier;
            update study_milestone sm1 set other = true WHERE sm1.study_protocol_identifier=OLD.study_protocol_identifier AND 
                (
				   sm1.identifier IN
				   (
				      SELECT
				      sm2.identifier
				      FROM study_milestone sm2
				      WHERE
				      (
				         (sm1.study_protocol_identifier = sm2.study_protocol_identifier)
				         AND
				         (
				            (
				               sm1.milestone_code
				            )
				            ::text <> ALL
				            (
				               ARRAY[('ADMINISTRATIVE_PROCESSING_START_DATE'::character varying)::text,
				               ('ADMINISTRATIVE_PROCESSING_COMPLETED_DATE'::character varying)::text,
				               ('ADMINISTRATIVE_READY_FOR_QC'::character varying)::text,
				               ('ADMINISTRATIVE_QC_START'::character varying)::text,
				               ('ADMINISTRATIVE_QC_COMPLETE'::character varying)::text,
				               ('SCIENTIFIC_PROCESSING_START_DATE'::character varying)::text,
				               ('SCIENTIFIC_PROCESSING_COMPLETED_DATE'::character varying)::text,
				               ('SCIENTIFIC_READY_FOR_QC'::character varying)::text,
				               ('SCIENTIFIC_QC_START'::character varying)::text,
				               ('SCIENTIFIC_QC_COMPLETE'::character varying)::text]
				            )
				         )
				      )
				      ORDER BY sm2.milestone_date DESC, sm2.identifier DESC LIMIT 1
				   )
				);
				
            RETURN null;
        ELSIF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN 
            update study_milestone sm1 set other = false where sm1.study_protocol_identifier = NEW.study_protocol_identifier;
            update study_milestone sm1 set other = true  WHERE sm1.study_protocol_identifier = NEW.study_protocol_identifier AND 
                (
                   sm1.identifier IN
                   (
                      SELECT
                      sm2.identifier
                      FROM study_milestone sm2
                      WHERE
                      (
                         (sm1.study_protocol_identifier = sm2.study_protocol_identifier)
                         AND
                         (
                            (
                               sm1.milestone_code
                            )
                            ::text <> ALL
                            (
                               ARRAY[('ADMINISTRATIVE_PROCESSING_START_DATE'::character varying)::text,
                               ('ADMINISTRATIVE_PROCESSING_COMPLETED_DATE'::character varying)::text,
                               ('ADMINISTRATIVE_READY_FOR_QC'::character varying)::text,
                               ('ADMINISTRATIVE_QC_START'::character varying)::text,
                               ('ADMINISTRATIVE_QC_COMPLETE'::character varying)::text,
                               ('SCIENTIFIC_PROCESSING_START_DATE'::character varying)::text,
                               ('SCIENTIFIC_PROCESSING_COMPLETED_DATE'::character varying)::text,
                               ('SCIENTIFIC_READY_FOR_QC'::character varying)::text,
                               ('SCIENTIFIC_QC_START'::character varying)::text,
                               ('SCIENTIFIC_QC_COMPLETE'::character varying)::text]
                            )
                         )
                      )
                      ORDER BY sm2.milestone_date DESC, sm2.identifier DESC LIMIT 1
                   )
                );   
 
            RETURN NULL; 
        END IF;
        RETURN NULL; 
    END;
$t$ LANGUAGE plpgsql;
   
    
CREATE OR REPLACE VIEW rv_other_milestone AS 
 SELECT sm1.study_protocol_identifier, sm1.milestone_code, sm1.milestone_date FROM study_milestone sm1 WHERE sm1.other=true;   

 
-- Scientific flag.
CREATE OR REPLACE FUNCTION set_scientific_study_milestone() RETURNS TRIGGER AS $t$
    BEGIN       
        IF (TG_OP = 'DELETE') THEN
            update study_milestone sm1 set scientific = false where sm1.study_protocol_identifier=OLD.study_protocol_identifier;
            update study_milestone sm1 set scientific = true WHERE sm1.study_protocol_identifier=OLD.study_protocol_identifier AND 
                (
				   (
				      sm1.milestone_date IN
				      (
				         SELECT
				         max(sm2.milestone_date) AS max
				         FROM study_milestone sm2
				         WHERE
				         (
				            (sm1.study_protocol_identifier = sm2.study_protocol_identifier)
				            AND
				            (
				               (
				                  (
				                     sm2.milestone_code
				                  )
				                  ::text = ANY
				                  (
				                     ARRAY[('SCIENTIFIC_PROCESSING_START_DATE'::character varying)::text,
				                     ('SCIENTIFIC_PROCESSING_COMPLETED_DATE'::character varying)::text,
				                     ('SCIENTIFIC_READY_FOR_QC'::character varying)::text,
				                     ('SCIENTIFIC_QC_START'::character varying)::text,
				                     ('SCIENTIFIC_QC_COMPLETE'::character varying)::text]
				                  )
				               )
				               OR
				               (
				                  (
				                     sm2.milestone_code
				                  )
				                  ::text = ANY
				                  (
				                     ARRAY[('READY_FOR_TSR'::character varying)::text,
				                     ('TRIAL_SUMMARY_REPORT'::character varying)::text,
				                     ('INITIAL_ABSTRACTION_VERIFY'::character varying)::text,
				                     ('TRIAL_SUMMARY_FEEDBACK'::character varying)::text,
				                     ('ONGOING_ABSTRACTION_VERIFICATION'::character varying)::text,
				                     ('LATE_REJECTION_DATE'::character varying)::text]
				                  )
				               )
				            )
				         )
				      )
				   )
				   AND
				   (
				      (
				         sm1.milestone_code
				      )
				      ::text = ANY
				      (
				         ARRAY[('SCIENTIFIC_PROCESSING_START_DATE'::character varying)::text,
				         ('SCIENTIFIC_PROCESSING_COMPLETED_DATE'::character varying)::text,
				         ('SCIENTIFIC_READY_FOR_QC'::character varying)::text,
				         ('SCIENTIFIC_QC_START'::character varying)::text,
				         ('SCIENTIFIC_QC_COMPLETE'::character varying)::text]
				      )
				   )
				);   
				
            RETURN null;
        ELSIF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN 
            update study_milestone sm1 set scientific = false where sm1.study_protocol_identifier = NEW.study_protocol_identifier;
            update study_milestone sm1 set scientific = true  WHERE sm1.study_protocol_identifier = NEW.study_protocol_identifier AND 
            (
				   (
				      sm1.milestone_date IN
				      (
				         SELECT
				         max(sm2.milestone_date) AS max
				         FROM study_milestone sm2
				         WHERE
				         (
				            (sm1.study_protocol_identifier = sm2.study_protocol_identifier)
				            AND
				            (
				               (
				                  (
				                     sm2.milestone_code
				                  )
				                  ::text = ANY
				                  (
				                     ARRAY[('SCIENTIFIC_PROCESSING_START_DATE'::character varying)::text,
				                     ('SCIENTIFIC_PROCESSING_COMPLETED_DATE'::character varying)::text,
				                     ('SCIENTIFIC_READY_FOR_QC'::character varying)::text,
				                     ('SCIENTIFIC_QC_START'::character varying)::text,
				                     ('SCIENTIFIC_QC_COMPLETE'::character varying)::text]
				                  )
				               )
				               OR
				               (
				                  (
				                     sm2.milestone_code
				                  )
				                  ::text = ANY
				                  (
				                     ARRAY[('READY_FOR_TSR'::character varying)::text,
				                     ('TRIAL_SUMMARY_REPORT'::character varying)::text,
				                     ('INITIAL_ABSTRACTION_VERIFY'::character varying)::text,
				                     ('TRIAL_SUMMARY_FEEDBACK'::character varying)::text,
				                     ('ONGOING_ABSTRACTION_VERIFICATION'::character varying)::text,
				                     ('LATE_REJECTION_DATE'::character varying)::text]
				                  )
				               )
				            )
				         )
				      )
				   )
				   AND
				   (
				      (
				         sm1.milestone_code
				      )
				      ::text = ANY
				      (
				         ARRAY[('SCIENTIFIC_PROCESSING_START_DATE'::character varying)::text,
				         ('SCIENTIFIC_PROCESSING_COMPLETED_DATE'::character varying)::text,
				         ('SCIENTIFIC_READY_FOR_QC'::character varying)::text,
				         ('SCIENTIFIC_QC_START'::character varying)::text,
				         ('SCIENTIFIC_QC_COMPLETE'::character varying)::text]
				      )
				   )
				)
				;    
            RETURN NULL; 
        END IF;
        RETURN NULL; 
    END;
$t$ LANGUAGE plpgsql;
                    
    
CREATE OR REPLACE VIEW rv_scientific_milestone AS 
 SELECT sm1.study_protocol_identifier, sm1.milestone_code, sm1.milestone_date
   FROM study_milestone sm1
  WHERE sm1.scientific=true;    

  
-- SOS Current flag.
CREATE OR REPLACE FUNCTION set_current_study_overall_status() RETURNS TRIGGER AS $t$
    BEGIN       
        IF (TG_OP = 'DELETE') THEN
            update study_overall_status sos1 set current = false where sos1.study_protocol_identifier=OLD.study_protocol_identifier;
            update study_overall_status sos1 set current = true WHERE sos1.study_protocol_identifier=OLD.study_protocol_identifier AND 
                (
                   sos1.identifier =
                   (
                      SELECT
                      sos2.identifier 
                      FROM study_overall_status sos2
                      WHERE
                      (
                         (sos1.study_protocol_identifier = sos2.study_protocol_identifier)
                         AND (sos2.deleted = false)
                      )
                      ORDER BY sos2.status_date DESC, sos2.identifier DESC LIMIT 1
                   )
            );  
            RETURN null;
            
        ELSIF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN 
            update study_overall_status sos1 set current = false where sos1.study_protocol_identifier = NEW.study_protocol_identifier;
            update study_overall_status sos1 set current = true  WHERE sos1.study_protocol_identifier = NEW.study_protocol_identifier AND 
                (
                   sos1.identifier =
                   (
                      SELECT
                      sos2.identifier 
                      FROM study_overall_status sos2
                      WHERE
                      (
                         (sos1.study_protocol_identifier = sos2.study_protocol_identifier)
                         AND (sos2.deleted = false)
                      )
                      ORDER BY sos2.status_date DESC, sos2.identifier DESC LIMIT 1
                   )
            );    
            RETURN NULL; 
        END IF;
        RETURN NULL; 
    END;
$t$ LANGUAGE plpgsql;

    
CREATE OR REPLACE VIEW rv_sos_current AS 
 SELECT sos1.status_code, sos1.study_protocol_identifier FROM study_overall_status sos1 WHERE sos1.current=true;   

 
-- Create triggers
CREATE TRIGGER study_milestone_set_active_trigger AFTER INSERT OR UPDATE OR DELETE
   ON study_milestone FOR EACH ROW    
    WHEN (pg_trigger_depth() = 0)
    EXECUTE PROCEDURE set_active_study_milestone();
    
CREATE TRIGGER study_milestone_set_admin_trigger AFTER INSERT OR UPDATE OR DELETE
    ON study_milestone FOR EACH ROW    
    WHEN (pg_trigger_depth() = 0)
    EXECUTE PROCEDURE set_admin_study_milestone();    
    
CREATE TRIGGER study_milestone_set_last_trigger AFTER INSERT OR UPDATE OR DELETE
    ON study_milestone FOR EACH ROW    
    WHEN (pg_trigger_depth() = 0)
    EXECUTE PROCEDURE set_last_study_milestone();    
    
CREATE TRIGGER study_milestone_set_other_trigger AFTER INSERT OR UPDATE OR DELETE
    ON study_milestone FOR EACH ROW    
    WHEN (pg_trigger_depth() = 0)
    EXECUTE PROCEDURE set_other_study_milestone();
    
CREATE TRIGGER study_milestone_set_scientific_trigger AFTER INSERT OR UPDATE OR DELETE
    ON study_milestone FOR EACH ROW    
    WHEN (pg_trigger_depth() = 0)
    EXECUTE PROCEDURE set_scientific_study_milestone();
    
CREATE TRIGGER study_overall_status_set_current_trigger AFTER INSERT OR UPDATE OR DELETE
    ON study_overall_status FOR EACH ROW    
    WHEN (pg_trigger_depth() = 0)
    EXECUTE PROCEDURE set_current_study_overall_status();    

-- Some indexes.    
create index sm_milestone_date on study_milestone (milestone_date);
create index sm_milestone_code on study_milestone (milestone_code);
CREATE INDEX STUDY_MILESTONE_STUDY_PROTOCOL_IDX on study_milestone (STUDY_PROTOCOL_IDENTIFIER);
    
    
-- Invoke the triggers to populate flags.
update study_milestone sm1 set active = false where sm1.identifier in (select max(identifier) from study_milestone group by  study_protocol_identifier);
update study_overall_status sos1 set current = false where sos1.identifier in (select max(identifier) from study_overall_status group by  study_protocol_identifier);
