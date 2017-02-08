CREATE OR REPLACE FUNCTION migrate_recruiting_statuses() RETURNS void as $$
    DECLARE
        rr RECORD;
        studyStatus VARCHAR(200);
        newStatus VARCHAR(200);
    BEGIN
	    --Handle migrating paritipating site statuses.
	    FOR rr in SELECT * from study_site_accrual_status LOOP
	       newStatus := '';
	       IF rr.status_code = 'NOT_YET_RECRUITING' THEN
	           select into studyStatus sos.status_code from study_overall_status sos where sos.study_protocol_identifier 
	               = (select ss.study_protocol_identifier from study_site ss where ss.identifier = rr.study_site_identifier) order by sos.date_last_updated desc limit 1;
	           IF studyStatus = 'IN_REVIEW' THEN
	               newStatus := 'IN_REVIEW';
	           ELSE
	               newStatus := 'APPROVED';
	           END IF;
	       ELSEIF rr.status_code = 'RECRUITING' THEN
	           newStatus := 'ACTIVE';
	       ELSEIF rr.status_code = 'SUSPENDED_RECRUITING' THEN
	           newStatus := 'TEMPORARILY_CLOSED_TO_ACCRUAL';
	       ELSEIF rr.status_code = 'ACTIVE_NOT_RECRUITING' THEN
	           newStatus := 'CLOSED_TO_ACCRUAL';
	       ELSEIF rr.status_code = 'TERMINATED_RECRUITING' THEN
	           newStatus := 'ADMINISTRATIVELY_COMPLETE';
	       END IF;
	       
	       IF newStatus != '' THEN
    	       UPDATE study_site_accrual_status set status_code = newStatus where identifier = rr.identifier;
    	   END IF;
	    END LOOP;	
	    --Handle study recruitment status migration.
	    FOR rr in SELECT * from study_recruitment_status LOOP
	       newStatus := '';
           IF rr.status_code = 'NOT_YET_RECRUITING' THEN
               select into studyStatus sos.status_code from study_overall_status sos where sos.study_protocol_identifier = rr.study_protocol_identifier order by sos.date_last_updated desc limit 1;
               IF studyStatus = 'IN_REVIEW' THEN
                   newStatus := 'IN_REVIEW';
               ELSE
                   newStatus := 'APPROVED';
               END IF;
           ELSEIF rr.status_code = 'RECRUITING_ACTIVE' THEN
               newStatus := 'ACTIVE';
           ELSEIF rr.status_code = 'SUSPENDED' THEN
               newStatus := 'TEMPORARILY_CLOSED_TO_ACCRUAL';
           ELSEIF rr.status_code = 'NOT_RECRUITING' THEN
               newStatus := 'CLOSED_TO_ACCRUAL';
           ELSEIF rr.status_code = 'TERMINATED' THEN
               newStatus := 'ADMINISTRATIVELY_COMPLETE';
           END IF;
           
           IF newStatus != '' THEN
               UPDATE study_recruitment_status set status_code = newStatus where identifier = rr.identifier;
           END IF;
        END LOOP;
    END;
$$ LANGUAGE 'plpgsql';

select migrate_recruiting_statuses();
drop function migrate_recruiting_statuses();