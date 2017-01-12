update csm_remote_group set grid_grouper_url=replace(grid_grouper_url, 
    'https://cagrid-gridgrouper-qa.nci.nih.gov:8443/wsrf/services/cagrid/GridGrouper', 'https://cagrid2-gridgrouper-qa.nci.nih.gov:1521/gridgrouper');

update csm_remote_group set grid_grouper_url=replace(grid_grouper_url, 
    'https://cagrid-gridgrouper-stage.nci.nih.gov:8443/wsrf/services/cagrid/GridGrouper', 'https://cagrid2-gridgrouper-stage.nci.nih.gov:1521/gridgrouper');    
