--Author   : Reshma Koganti
--Date     : 10/05/2012        
--Jira#    : PO-5761Biomarker - Start capturing caDSR public IDs in CTRP db 
--Comments : drop the columns name and long_name from the planned_marker table

ALTER TABLE planned_marker DROP COLUMN name;
ALTER TABLE planned_marker DROP COLUMN long_name;