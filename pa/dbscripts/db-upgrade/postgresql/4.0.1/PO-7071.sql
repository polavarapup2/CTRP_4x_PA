--Author   : Reshma Koganti
--Date     : 3/14/2014        
--Jira#    : PO-7071 Rename Trial Data verification email parameter "N_value" to 
--           something meaningful in pa_properties table 

update pa_properties set name='group1TrialsVerificationFrequency' where name='N_value';