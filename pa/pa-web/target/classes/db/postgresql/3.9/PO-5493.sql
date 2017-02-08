--Author   : Reshma Koganti
--Date     : 10/05/2012        
--Jira#    : PO-5493 Change existing Biomarker menus according to Christy Nichols proposal
--Comments : Adding Columns to Planned marker table i.e  "Evaluation Type" and "Evaluation Type Other text"  and "Specimen type other text".  


alter table planned_marker add column Evaluation_Type_Code character varying(400);

alter table planned_marker add column EVALUATION_TYPE_OTHER_TEXT character varying(200);

alter table planned_marker add column SPECIMEN_TYPE_OTHER_TEXT character varying(200);