UPDATE document
SET type_code = upper('Protocol_Document')
where type_code = 'Protocol_Document';

UPDATE document
SET type_code = upper('Irb_Approval_Document')
where type_code = 'Irb_Approval_Document';

UPDATE document
SET type_code = upper('Participating_sites')
where type_code = 'Participating_sites';

UPDATE document
SET type_code = upper('Informed_Consent_Document')
where type_code = 'Informed_Consent_Document';

UPDATE document
SET type_code = upper('Other')
where type_code = 'Other';