alter table study_overall_status add column deleted bool NOT NULL DEFAULT false;

CREATE OR REPLACE VIEW rv_sos_current AS
	SELECT sos1.status_code, sos1.study_protocol_identifier
	FROM study_overall_status sos1
	WHERE 
	(
	   sos1.identifier IN
	   (
	      SELECT max(sos2.identifier) AS max
	       FROM study_overall_status sos2
	       WHERE (sos1.study_protocol_identifier = sos2.study_protocol_identifier) and sos2.deleted=false
	   )
	);

