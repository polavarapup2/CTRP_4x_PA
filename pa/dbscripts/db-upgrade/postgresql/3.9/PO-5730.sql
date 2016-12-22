--Author   : Reshma Koganti
--Date     : 12/19/2012        
--Jira#    : PO-5730 Biomarker - behavior as a result of synching attributes with caDSR
--Comments : Adding CaDSR_ID Column to Bio Marker attribute table .  

alter table assay_type add column CADSR_ID integer;
alter table biomarker_purpose add column CADSR_ID integer;
alter table biomarker_use add column CADSR_ID integer;
alter table evaluation_type add column CADSR_ID integer;
alter table specimen_collection add column CADSR_ID integer;
alter table specimen_type add column CADSR_ID integer;