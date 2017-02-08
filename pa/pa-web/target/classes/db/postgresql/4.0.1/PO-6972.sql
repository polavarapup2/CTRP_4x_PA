--Author   : Reshma Koganti
--Date     : 3/03/2014        
--Jira#    : PO-6972 
--           Rename CT.Gov or CTGov to ClinicalTrials.gov in all CTRP Application pages wherever applicable 

Update csm_user set first_name ='ClinicalTrials.gov Import' where login_name='ctgovimport';

UPDATE registry_user set first_name ='ClinicalTrials.gov Import' where csm_user_id 
IN (select user_id from csm_user where csm_user.login_name='ctgovimport' );
