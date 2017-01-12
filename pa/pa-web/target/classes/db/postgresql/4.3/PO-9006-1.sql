update csm_remote_group 
    set grid_grouper_url=(select grid_grouper_url from csm_remote_group where grid_grouper_url like 'https%' limit 1)
    where grid_grouper_url='@gridgrouper.url@';