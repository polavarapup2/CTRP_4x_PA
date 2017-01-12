DELETE FROM pa_properties where name='ctgov.sync.fields_of_interest';

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 
    'ctgov.sync.fields_of_interest', 
    'studyProtocol.scientificDescription;studyProtocol.keywordText;eligibilityCriteria;sponsor;collaborators;studyProtocol.studyResourcings.{? #this.summary4ReportedResourceIndicator==true}.{organizationIdentifier}');