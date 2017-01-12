--Author   : Reshma Koganti
--Date     : 8/20/2014        
--Jira#    : PO-7902 Biomarkers - Change the pointer for Primary term in our coding logic to PV field in caDSR 

ALTER TABLE PLANNED_MARKER_SYNC_CADSR ADD COLUMN pv_name varchar(2000);