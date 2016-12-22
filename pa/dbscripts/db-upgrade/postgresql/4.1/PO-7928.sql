--Author   : Reshma Koganti
--Date     : 8/26/2014        
--Jira#    : PO-7928 Point CTRP to different caDSR instances

insert into pa_properties values ((select max(identifier) + 1 from pa_properties), 'CDE_PUBLIC_ID', '5473');

insert into pa_properties (identifier, name, value) (select (select (max(identifier) + 1) as identifier from pa_properties), 
'Latest_Version_Indicator' as name, 'No' as value where not exists (select distinct('Yes') from csm_remote_group where grid_grouper_url like 
'https://cagrid-gridgrouper-prod.nci.nih.gov%'));

insert into pa_properties (identifier, name, value) (select (select (max(identifier) + 1) as identifier from pa_properties), 
'CDE_Version' as name, '8.0' as value where not exists (select distinct('9.0') from csm_remote_group where grid_grouper_url like 
'https://cagrid-gridgrouper-prod.nci.nih.gov%'));