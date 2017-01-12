--Author   : Reshma Koganti
--Date     : 11/26/2012        
--Jira#    : PO-5652 Biomarkers - Remove 'Specimen Collection' attribute
--Comments : To drop a not null constraint use for tissue_collection_method_code. 

ALTER TABLE PLANNED_MARKER ALTER COLUMN tissue_collection_method_code drop not NULL; 