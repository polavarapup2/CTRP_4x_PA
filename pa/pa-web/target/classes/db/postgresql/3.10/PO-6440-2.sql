update ctgovimport_log set review_required = false;

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 
    'ctgov.sync.fields_of_interest', 
    'studyProtocol.scientificDescription;studyProtocol.keywordText;eligibilityCriteria');