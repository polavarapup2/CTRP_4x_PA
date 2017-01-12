INSERT INTO PA_PROPERTIES VALUES ((select max(identifier) + 1 from pa_properties),'regweb.reportview.availableReports','Data Table 4:ROLE_DT4_CC_PSUSER');
INSERT INTO PA_PROPERTIES VALUES ((select max(identifier) + 1 from pa_properties),'regweb.reportview.mail.from','sample@example.com');
INSERT INTO PA_PROPERTIES VALUES ((select max(identifier) + 1 from pa_properties),'regweb.reportview.mail.to','ncicbiit@example.com');
INSERT INTO PA_PROPERTIES VALUES ((select max(identifier) + 1 from pa_properties),'regweb.reportview.mail.subject','Request to add CTRP user to the {LDAP_NAME} LDAP group');
INSERT INTO PA_PROPERTIES VALUES ((select max(identifier) + 1 from pa_properties),'regweb.reportview.mail.body','<b>CBIIT App Support Team:</b><br/><p>Please add the following CTRP User to the {LDAP_NAME} LDAP group. This user has been authorized from within the CTRP application to use the CTRP Reports module. However, the user must first be added by App Support to this LDAP group.</p><b><u>Information about the User</u></b><br/><b>User''s Full Name:</b> {USER_FIRST_LAST_NAME}<br/><b>User''s CTRP username:</b> {USERNAME}<br/><b>User''s Organization:</b> {USER_ORGNAME}<br/><b>User''s NIH Email Address:</b> {USER_EMAIL_ID}<br/><br/><br/><i>Information about this Request: </i> <br/>This request was generated by the CTRP system. If you have questions about this request, please send a note to the CTRP technical support list at ncictrp-techsupport@mail.nih.gov');
INSERT INTO PA_PROPERTIES VALUES ((select max(identifier) + 1 from pa_properties),'regweb.reportview.dt4.ldapgroup','GP-CFW_CTRP_CC-REPORTVIEWER');

INSERT INTO PA_PROPERTIES VALUES ((select max(identifier) + 1 from pa_properties),'jasper.base.user.rest.url','https://trials-dev.nci.nih.gov/reports/rest/user');
INSERT INTO PA_PROPERTIES VALUES ((select max(identifier) + 1 from pa_properties),'jasper.admin.username','jasperadmin');
INSERT INTO PA_PROPERTIES VALUES ((select max(identifier) + 1 from pa_properties),'jasper.admin.password','jasperadmin');