CREATE TABLE study_otheridentifiers_dups (
    study_protocol_id bigint NOT NULL,
    null_flavor character varying(255),
    displayable boolean,
    extension character varying(255),
    identifier_name character varying(255),
    reliability character varying(255),
    root character varying(255),
    scope character varying(255)
);
insert into study_otheridentifiers_dups(study_protocol_id, null_flavor, displayable, extension, identifier_name, reliability, root, scope)
(select distinct study_protocol_id, null_flavor, displayable, extension, identifier_name, reliability, root, scope
from study_otheridentifiers
where (study_protocol_id,root,extension) in (select study_protocol_id,root,extension 
                                             from study_otheridentifiers
                                             group by study_protocol_id,root,extension 
                                             having count(*)>1));
delete from study_otheridentifiers
where (study_protocol_id,root,extension) in (select study_protocol_id,root,extension from study_otheridentifiers_dups);

insert into study_otheridentifiers(study_protocol_id, null_flavor, displayable, extension, identifier_name, reliability, root, scope)
(select study_protocol_id, null, null, extension, max(identifier_name), null, root, null
from study_otheridentifiers_dups group by study_protocol_id, root, extension);

drop table study_otheridentifiers_dups;

ALTER TABLE study_otheridentifiers ADD CONSTRAINT study_otheridentifiers_study_protocol_id_key UNIQUE (study_protocol_id,root,extension);
drop index if exists study_otherid_spid_idx;
drop index if exists study_otheridentifiers_pkey;

                                             