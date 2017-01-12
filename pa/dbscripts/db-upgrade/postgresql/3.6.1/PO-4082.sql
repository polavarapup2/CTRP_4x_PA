insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'appsupport.mailTo', 'ncicbmb@example.com');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'self.registration.appsupport.email.subject', 
'CTRP: New Account Request');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'self.registration.appsupport.email.body', 
'We have received a new account request for the CTRP registration application. 
Please create an account in the NCI external LDAP. Here is the user information:
First Name: {0}
Last Name: {1}
Affiliated Organization: {2}
Phone Number: {3}
email: {4}

When complete, please redirect the user to this URL so she can activate her account:
{5}

Thank you.
');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'self.registration.pleaseWait.email.subject', 
'New NCI CTRP Account Request');

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'self.registration.pleaseWait.email.body', 
'Dear {0} {1},

Thank you for requesting an account for the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP) system.
 
You will receive an email notification with instructions for activating your account.
 
Please allow two business days for processing your request.

If you have questions, please contact us at ncictro@mail.nih.gov

Thank you,
 
NCI Clinical Trials Reporting Office
http://www.cancer.gov/ncictrp
ncictro@mail.nih.gov
');
