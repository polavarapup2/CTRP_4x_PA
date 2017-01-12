--Author   : Reshma Koganti
--Date     : 8/19/2014        
--Jira#    : To update cadsr email list in pa_properties based on environments.

update pa_properties set value='reshma.koganti@semanticbits.com'where name ='CADSR_SYNC_JOB_EMAIl_LIST'
AND not exists (select * from csm_remote_group where grid_grouper_url like 'https://cagrid-gridgrouper-prod.nci.nih.gov%');

update pa_properties set value='reshma.koganti@semanticbits.com'where name ='CADSR_SYNC_JOB_FROM_ADDRESS'
AND not exists (select * from csm_remote_group where grid_grouper_url like 'https://cagrid-gridgrouper-prod.nci.nih.gov%');

