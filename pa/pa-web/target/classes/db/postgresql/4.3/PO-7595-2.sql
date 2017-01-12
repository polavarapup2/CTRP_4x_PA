DELETE FROM PA_PROPERTIES WHERE NAME='regweb.reportview.mail.from';
DELETE FROM PA_PROPERTIES WHERE NAME='regweb.reportview.mail.to';
DELETE FROM PA_PROPERTIES WHERE NAME='regweb.reportview.mail.subject';
DELETE FROM PA_PROPERTIES WHERE NAME='regweb.reportview.mail.body';
DELETE FROM PA_PROPERTIES WHERE NAME='regweb.reportview.dt4.ldapgroup';
UPDATE PA_PROPERTIES SET VALUE='Data Table 4:ROLE_DT4_CC_PSUSER|organization_1' WHERE NAME='regweb.reportview.availableReports';
INSERT INTO PA_PROPERTIES VALUES ((select max(identifier) + 1 from pa_properties),'jasper.rest.http.timeout.millis','10000');