--Author   : Reshma Koganti
--Date     : 1/21/2014        
--Jira#    : PO-6883 PA: Abstraction validation fails for few trials in Stage and Production 

update study_site_accrual_status set status_code = 'COMPLETED' where status_code = 'COMPLETE'; 

update study_recruitment_status set status_code = 'COMPLETED' where status_code = 'COMPLETE'; 