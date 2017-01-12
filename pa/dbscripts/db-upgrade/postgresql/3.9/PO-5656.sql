alter table study_protocol add column ctro_override boolean;
update study_protocol set ctro_override = true;
update study_protocol set ctro_override = false where exists (
    SELECT ss.identifier, o.name FROM study_site ss
        INNER JOIN research_organization ro ON ro.identifier=ss.research_organization_identifier
        INNER JOIN organization o ON o.identifier=ro.organization_identifier
        WHERE ss.functional_code='SPONSOR' and ss.study_protocol_identifier=study_protocol.identifier
        and o.name='National Cancer Institute');
alter table study_protocol alter column ctro_override set default false;

alter table pa_properties alter column value set data type varchar(65536);
insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'ctep.ccr.trials', 
    '');
