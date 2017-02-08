update pa_properties set value = 'We have received a new account request for the CTRP registration application. 
Please follow these steps:

Step 1.  
Please create an account in the NCI external LDAP. Here is the user information:
First Name: {0}
Last Name: {1}
Affiliated Organization: {2}
Phone Number: {3}
email: {4}

Step 2A: Add the NCI account to the Grid by logging the account into the Grid Portal OR logging the account into GAARDS.
Step 2B:Add the NCI account to the Submitters group.

Step 3.
Wait 5 minutes. This will allows the CTRP system to sync and create a local record. 

Step 4. 
Go to this URL
{5}
You will receive confirmation that the user is successfully activated

Step 5. 
Inform the user that his/her account is ready.

Thank you.'
where name = 'self.registration.appsupport.email.body';