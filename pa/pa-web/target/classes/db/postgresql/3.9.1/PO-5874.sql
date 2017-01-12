--Author   : Reshma Koganti
--Date     : 03/07/2013       
--Jira#    : PO-5874 Biomarkers - Limit the set of marker attribute values displayed according to Christy's proposed set 
--Comments :  insert values in to pa_properties for the CDE version for BioMarker Attributes. 
 
 INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'CDE_version_assay','4.0');
 INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'CDE_version_use','1.0');
 INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'CDE_version_purpose','1.0');
 INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'CDE_version_specimen','1.0');
 INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'CDE_version_sp_col','1.0');
 INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'CDE_version_eval','1.0');
 