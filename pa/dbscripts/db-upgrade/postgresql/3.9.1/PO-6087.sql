CREATE OR REPLACE VIEW rv_trial_id_cdr AS
    SELECT soi1.study_protocol_id, soi1.extension
FROM study_otheridentifiers soi1 WHERE (substr((soi1.extension), 1, 3) = 'CDR') 
AND soi1.extension=(
SELECT soi2.extension from study_otheridentifiers soi2
WHERE (substr((soi2.extension), 1, 3) = 'CDR') and soi2.study_protocol_id=soi1.study_protocol_id LIMIT 1
);
