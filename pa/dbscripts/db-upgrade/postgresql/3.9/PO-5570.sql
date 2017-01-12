--Author   : Reshma Koganti
--Date     : 10/10/2012        
--Jira#    : PO-5570 PA: Possible to Exceed Max Field Length for Biomarkers with Mult-select option
--Comments : Alter to the Size of the columns in the planned marker table 


alter table planned_marker alter column assay_type_code type character varying(400);

alter table planned_marker alter column tissue_specimen_type_code type character varying(400);