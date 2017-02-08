insert into pa_properties(IDENTIFIER,name,value) VALUES(33,'CDE_REQUEST_TO_EMAIL_TEXT',
'The CTRO has identified a need for a new CDE to help us build structured eligibility criteria for Protocol <NCI-xxxxxx>  
 The concept is <xxxxx>.
 
 Here is the text of the eligibility criteria as written in the Protocol:
 <xxxxxxx>
 Please let us know if you need any further information to build the new CDE.
 
 Thanks,
 CTRO 
 ');
 update pa_properties set value='New CDE Request' where identifier=30;