CREATE OR REPLACE VIEW rv_last_milestone AS
SELECT study_protocol_identifier, milestone_code, milestone_date   
FROM study_milestone sm1
WHERE identifier IN (
  SELECT identifier
  FROM study_milestone sm2
  WHERE sm1.study_protocol_identifier = sm2.study_protocol_identifier ORDER BY sm2.milestone_date desc, sm2.identifier desc LIMIT 1);   