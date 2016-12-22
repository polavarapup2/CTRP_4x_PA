CREATE OR REPLACE VIEW rv_admin_milestone AS 
SELECT sm1.study_protocol_identifier, sm1.milestone_code, sm1.milestone_date
   FROM study_milestone sm1
  WHERE (sm1.milestone_date IN 
  ( 
    SELECT max(sm2.milestone_date) AS max
    FROM study_milestone sm2
    WHERE sm1.study_protocol_identifier = sm2.study_protocol_identifier  AND
    ((sm2.milestone_code::text = ANY (ARRAY['ADMINISTRATIVE_PROCESSING_START_DATE'::character varying::text, 
         'ADMINISTRATIVE_PROCESSING_COMPLETED_DATE'::character varying::text, 'ADMINISTRATIVE_READY_FOR_QC'::character varying::text, 
         'ADMINISTRATIVE_QC_START'::character varying::text, 'ADMINISTRATIVE_QC_COMPLETE'::character varying::text]))
         OR           
          (sm2.milestone_code::text = ANY(
           ARRAY['READY_FOR_TSR'::character varying::text, 'TRIAL_SUMMARY_REPORT'::character varying::text,
         'INITIAL_ABSTRACTION_VERIFY'::character varying::text, 'TRIAL_SUMMARY_FEEDBACK'::character varying::text, 
         'ONGOING_ABSTRACTION_VERIFICATION'::character varying::text, 'LATE_REJECTION_DATE'::character varying::text
            ]))) 
  )
  )AND 
         (sm1.milestone_code::text = ANY (ARRAY['ADMINISTRATIVE_PROCESSING_START_DATE'::character varying::text, 
         'ADMINISTRATIVE_PROCESSING_COMPLETED_DATE'::character varying::text, 'ADMINISTRATIVE_READY_FOR_QC'::character varying::text, 
         'ADMINISTRATIVE_QC_START'::character varying::text, 'ADMINISTRATIVE_QC_COMPLETE'::character varying::text])) ;
         
         CREATE OR REPLACE VIEW rv_scientific_milestone AS 
 SELECT sm1.study_protocol_identifier, sm1.milestone_code, sm1.milestone_date
 FROM study_milestone sm1
 WHERE (sm1.milestone_date IN 
       (SELECT max(sm2.milestone_date) AS max
        FROM study_milestone sm2
        WHERE sm1.study_protocol_identifier = sm2.study_protocol_identifier  AND
        ((sm2.milestone_code::text = ANY (ARRAY['SCIENTIFIC_PROCESSING_START_DATE'::character varying::text, 
            'SCIENTIFIC_PROCESSING_COMPLETED_DATE'::character varying::text, 'SCIENTIFIC_READY_FOR_QC'::character varying::text,
            'SCIENTIFIC_QC_START'::character varying::text, 'SCIENTIFIC_QC_COMPLETE'::character varying::text]))
       OR           
       (sm2.milestone_code::text = ANY(
           ARRAY['READY_FOR_TSR'::character varying::text, 'TRIAL_SUMMARY_REPORT'::character varying::text,
           'INITIAL_ABSTRACTION_VERIFY'::character varying::text, 'TRIAL_SUMMARY_FEEDBACK'::character varying::text, 
           'ONGOING_ABSTRACTION_VERIFICATION'::character varying::text, 'LATE_REJECTION_DATE'::character varying::text])))
       ))
 AND 
 (sm1.milestone_code::text = ANY (ARRAY['SCIENTIFIC_PROCESSING_START_DATE'::character varying::text, 
     'SCIENTIFIC_PROCESSING_COMPLETED_DATE'::character varying::text, 'SCIENTIFIC_READY_FOR_QC'::character varying::text,
     'SCIENTIFIC_QC_START'::character varying::text, 'SCIENTIFIC_QC_COMPLETE'::character varying::text]));