--PO-8635 Migrate to the latest NCI LexEVS APIs
delete from pa_properties where name in ('ctrp.lexEVSURL','ctrp.lexAPIURL');
insert into pa_properties values ((select max(identifier) + 1 from pa_properties),'ctrp.lexEVSURL','http://lexevscts2.nci.nih.gov/lexevscts2/codesystem/NCI_Thesaurus/entity/');
insert into pa_properties values ((select max(identifier) + 1 from pa_properties),'ctrp.lexAPIURL','http://lexevsapi62.nci.nih.gov/lexevsapi62/GetXML?query=Entity[@_entityCode={CODE}]');

