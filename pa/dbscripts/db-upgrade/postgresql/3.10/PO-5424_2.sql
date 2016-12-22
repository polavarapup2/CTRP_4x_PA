CREATE OR REPLACE VIEW rv_study_resourcing AS 
 SELECT sr.study_protocol_identifier, sr.type_code
   FROM study_resourcing sr
  WHERE sr.summ_4_rept_indicator = true 
  and sr.identifier in (
        select max(sr2.identifier) as max from study_resourcing sr2 
        where sr.study_protocol_identifier = sr2.study_protocol_identifier
		and sr2.summ_4_rept_indicator = true);