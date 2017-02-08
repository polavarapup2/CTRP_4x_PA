INSERT INTO pa_properties (identifier,name,value) VALUES ((select max(identifier) + 1 from pa_properties)
    ,'ctgov.sync.fields_of_interest.key_to_sect_mapping',
    '# If mapping not found, the field will be assumed from Admin section. Or say Admin explicitly here.\nstudyProtocol.scientificDescription=Scientific\neligibilityCriteria=Scientific');
    
alter table ctgovimport_log add column admin bool;
alter table ctgovimport_log add column scientific bool;
update ctgovimport_log set scientific = false;
update ctgovimport_log set admin = false;