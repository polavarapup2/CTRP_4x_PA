INSERT INTO csm_group (group_name,group_desc,update_date,application_id) VALUES ('SecurityAdmin', 'Security administrators', now(),
    (select application_id from csm_application where application_name='pa'));

update csm_user set premgrt_login_name=login_name, login_name='cteprss'  where login_name like '/O=caBIG/OU=caGrid/OU=LOA1/OU=NCI/CN=cteprss';
update csm_user set premgrt_login_name=login_name, login_name='ctep-rss'  where login_name like '/O=caBIG/OU=caGrid/OU=Stage LOA1/OU=Dorian/CN=ctep-rss';
update csm_user set premgrt_login_name=login_name, login_name='ctep-rss'  where login_name like '/O=caBIG/OU=caGrid/OU=QA LOA1/OU=NCICB QA AuthnSvc IdP/CN=ctep-rss';

update study_checkout set user_identifier = lower(regexp_replace(user_identifier, '^.*?CN=', ''));
update study_checkout set checkin_user_identifier = lower(regexp_replace(checkin_user_identifier, '^.*?CN=', '')) 
    where checkin_user_identifier is not null;
    
    
alter table registry_user add column token varchar;  

-- self.registration.appsupport.email.subject
update pa_properties
    set value = 'CTRP: New Account Request'
    where name = 'self.registration.appsupport.email.subject';

-- self.registration.appsupport.email.body
update pa_properties
    set value = 
'Dear Sir or Madam,

We have received a request for a new user account in the  Clinical Trials Reporting Program (CTRP) Clinical Trials Registration application. 

To create the account, please follow the steps below:

1. Create an account in the NCI External LDAP. The user information is as follows:
    * First Name: {0}
    * Last Name: {1}
    * Affiliated Organization: {2}
    * Phone Number: {3}
    * Email: {4} 
2. Navigate to the following URL and follow the directions on the screen: {5} 
3. Inform the user that his/her account is ready.

If you have questions, please contact us at ncictro@mail.nih.gov.

Thank you,
NCI Clinical Trials Reporting Office'
    where name = 'self.registration.appsupport.email.body';
    
    
    
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties), 'self.registration.activation.subject',
'Your NCI CTRP Account is now active');    

insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties), 'self.registration.activation.body',
'Dear ${user.fullName},

Your NCI CTRP Account has been activated. You should be able to log in with your LDAP User ID:  ${user.csmUser.loginName}.

If you have questions, please contact us at ncictro@mail.nih.gov.

Thank you,
NCI Clinical Trials Reporting Office');
