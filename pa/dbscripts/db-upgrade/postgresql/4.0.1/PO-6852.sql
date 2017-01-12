--Author   : Dirk Walter
--Date     : 3/18/2014        
--Jira#    : PO-6852
--      Fix rv_active_milestone and rv_other_milestone


CREATE OR REPLACE VIEW rv_active_milestone AS 
 SELECT sm1.study_protocol_identifier, sm1.milestone_code, sm1.milestone_date
   FROM study_milestone sm1
  WHERE (sm1.identifier IN ( SELECT sm2.identifier
           FROM study_milestone sm2
          WHERE sm1.study_protocol_identifier = sm2.study_protocol_identifier AND (sm2.milestone_code::text <> ALL (ARRAY['SUBMISSION_TERMINATED'::character varying::text, 'SUBMISSION_REACTIVATED'::character varying::text]))
          ORDER BY sm2.milestone_date DESC, sm2.identifier DESC
         LIMIT 1));


CREATE OR REPLACE VIEW rv_other_milestone AS 
 SELECT sm1.study_protocol_identifier, sm1.milestone_code, sm1.milestone_date
   FROM study_milestone sm1
  WHERE (sm1.identifier IN ( SELECT sm2.identifier
           FROM study_milestone sm2
          WHERE sm1.study_protocol_identifier = sm2.study_protocol_identifier AND (sm1.milestone_code::text <> ALL (ARRAY['ADMINISTRATIVE_PROCESSING_START_DATE'::character varying::text, 'ADMINISTRATIVE_PROCESSING_COMPLETED_DATE'::character varying::text, 'ADMINISTRATIVE_READY_FOR_QC'::character varying::text, 'ADMINISTRATIVE_QC_START'::character varying::text, 'ADMINISTRATIVE_QC_COMPLETE'::character varying::text, 'SCIENTIFIC_PROCESSING_START_DATE'::character varying::text, 'SCIENTIFIC_PROCESSING_COMPLETED_DATE'::character varying::text, 'SCIENTIFIC_READY_FOR_QC'::character varying::text, 'SCIENTIFIC_QC_START'::character varying::text, 'SCIENTIFIC_QC_COMPLETE'::character varying::text]))
          ORDER BY sm2.milestone_date DESC, sm2.identifier DESC
         LIMIT 1));

