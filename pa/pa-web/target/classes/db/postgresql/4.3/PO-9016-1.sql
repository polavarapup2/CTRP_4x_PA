insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),'imap.server','helix.nih.gov');
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),'imap.port','993');
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),'imap.folder','inbox');
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),'ctgov.upload.errorEmail.account','CTGovUploadErrorEmailAccount');
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),'ctgov.upload.errorEmail.subject','PRS Protocol Upload Notification');
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),'ctgov.upload.errorEmailProcessing.schedule','0 0 6 * * ?');
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),'ctgov.upload.error.regex','Study Number \d+ \(([^\)]*)\)\s*ERROR:([^\n]*)');