delete FROM study_site ss
WHERE ss.FUNCTIONAL_CODE = 'TREATING_SITE'
AND ss.identifier <> (select min(ss2.identifier) from study_site ss2
WHERE ss2.study_protocol_identifier = ss.study_protocol_identifier
and ss2.healthcare_facility_identifier = ss.healthcare_facility_identifier
and ss2.FUNCTIONAL_CODE = 'TREATING_SITE');

ALTER TABLE study_site ADD CONSTRAINT study_site_study_protocol_healthcare_facility_identifier_uq UNIQUE ("study_protocol_identifier", "healthcare_facility_identifier");