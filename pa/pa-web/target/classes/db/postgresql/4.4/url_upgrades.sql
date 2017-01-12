update pa_properties set value='https://cadsrapi.nci.nih.gov/cadsrapi41' 
    where value='http://cadsrapi.nci.nih.gov/cadsrapi41';

update pa_properties set value='https://lexevs64cts2.nci.nih.gov/lexevscts2/codesystem/NCI_Thesaurus/entity/' 
    where value='http://lexevscts2.nci.nih.gov/lexevscts2/codesystem/NCI_Thesaurus/entity/';
    
update pa_properties set value='https://lexevsapi64.nci.nih.gov/lexevsapi64/GetXML?query=Entity[@_entityCode={CODE}]' 
    where value='http://lexevsapi62.nci.nih.gov/lexevsapi62/GetXML?query=Entity[@_entityCode={CODE}]';    