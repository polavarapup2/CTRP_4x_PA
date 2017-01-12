DELETE FROM pa_properties where name='ctgov.sync.fields_of_interest';
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 
    'ctgov.sync.fields_of_interest', 
    'studyProtocol.publicTitle;studyProtocol.officialTitle;studyProtocol.publicDescription;studyProtocol.scientificDescription;studyProtocol.keywordText;eligibilityCriteria;arms;sponsor;collaborators');
    
    
DELETE FROM pa_properties where name='ctgov.sync.fields_of_interest.key_to_label_mapping';    
INSERT INTO pa_properties (identifier,name,value) VALUES ((select max(identifier) + 1 from pa_properties)
    ,'ctgov.sync.fields_of_interest.key_to_label_mapping',
    'studyProtocol.publicTitle=Brief Title\nstudyProtocol.officialTitle=Official Title\nstudyProtocol.publicDescription=Brief Summary\nstudyProtocol.scientificDescription=Detailed Description\nstudyProtocol.keywordText=Keywords\neligibilityCriteria=Eligibility Criteria\narms=Arms\nsponsor=Sponsor\ncollaborators=Collaborators');
   
DELETE FROM pa_properties where name='ctgov.sync.fields_of_interest.key_to_sect_mapping';    
INSERT INTO pa_properties (identifier,name,value) VALUES ((select max(identifier) + 1 from pa_properties)
    ,'ctgov.sync.fields_of_interest.key_to_sect_mapping',
    '# If mapping not found, the field will be assumed from Admin section. Or say Admin explicitly here.\nstudyProtocol.scientificDescription=Scientific\neligibilityCriteria=Scientific\nstudyProtocol.publicTitle=Scientific\nstudyProtocol.publicDescription=Scientific\narms=Scientific');
       