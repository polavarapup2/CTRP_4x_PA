-- This will fix trials that have one or more active on-hold record and whose processing status is not On-Hold.
-- All on-hold records will be closed.

update study_onhold set offhold_date=current_date where study_onhold.identifier in (

select so.identifier from study_onhold so inner join study_protocol sp on so.study_protocol_identifier=sp.identifier inner join document_workflow_status dws on sp.identifier=dws.study_protocol_identifier
   where so.offhold_date is null and (select count(identifier) from study_onhold where study_onhold.study_protocol_identifier=sp.identifier and study_onhold.offhold_date is null)>0
   and dws.status_code!='ON_HOLD' and dws.identifier=(select max(identifier) from document_workflow_status where document_workflow_status.study_protocol_identifier=sp.identifier)

);