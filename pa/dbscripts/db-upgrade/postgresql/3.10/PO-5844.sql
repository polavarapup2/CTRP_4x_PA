alter table study_contact add column title varchar(200);

alter table study_protocol_stage add column responsible_title varchar(200);
alter table study_protocol_stage add column responsible_affil_id varchar(50);


CREATE OR REPLACE VIEW rv_principal_investigator_responsible_party AS
	SELECT sc.study_protocol_identifier,per.first_name,per.last_name
	FROM study_contact sc
	JOIN clinical_research_staff crs ON (crs.identifier = sc.clinical_research_staff_identifier)
	JOIN person per ON (crs.person_identifier = per.identifier)
	WHERE role_code = 'RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR'
	   OR role_code = 'RESPONSIBLE_PARTY_SPONSOR_INVESTIGATOR';
