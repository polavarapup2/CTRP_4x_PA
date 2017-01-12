UPDATE study_protocol SET primary_purpose_code='SCREENING' where primary_purpose_code = 'EARLY_DETECTION';

UPDATE study_protocol_stage SET primary_purpose_code='SCREENING' where primary_purpose_code = 'EARLY_DETECTION';