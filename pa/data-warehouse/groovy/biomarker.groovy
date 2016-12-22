import groovy.sql.Sql

def sql = """select marker.assay_purpose_code, marker.assay_purpose_other_text, marker.assay_type_code, marker.assay_type_other_text,
            marker.assay_use_code, marker.identifier, syncmarker.name, syncmarker.meaning, nci_id.extension, marker.status_code,
            marker.tissue_collection_method_code, marker.tissue_specimen_type_code, marker.hugo_biomarker_code, marker.evaluation_type_code,
            marker.evaluation_type_other_text, marker.specimen_type_other_text, syncmarker.nt_term_identifier
            from planned_activity pa
            inner join study_protocol as sp on sp.identifier = pa.study_protocol_identifier
            inner join planned_marker as marker on marker.identifier = pa.identifier
			inner join planned_marker_sync_cadsr as syncmarker on syncmarker.identifier = marker.pm_sync_identifier
            inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = pa.study_protocol_identifier
                        and nci_id.root = '2.16.840.1.113883.3.26.4.3'
            where sp.status_code = 'ACTIVE';"""
def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def biomarkers = destinationConnection.dataSet("STG_DW_STUDY_BIOMARKER");
sourceConnection.eachRow(sql) { row ->
    biomarkers.add(assay_purpose: row.assay_purpose_code, assay_purpose_description: row.assay_purpose_other_text, assay_type_code: row.assay_type_code,
            assay_type_description: row.assay_type_other_text, assay_use:row.assay_use_code, internal_system_id: row.identifier,
            long_name:row.meaning, name: row.name, nci_id: row.extension, status_code: row.status_code,
            tissue_collection_method_code: row.tissue_collection_method_code, tissue_specimen_type_code: row.tissue_specimen_type_code,
            hugo_biomarker_code: row.hugo_biomarker_code, evaluation_type_code: row.evaluation_type_code,
            evaluation_type_other_text: row.evaluation_type_other_text, specimen_type_other_text: row.specimen_type_other_text,
			nt_term_identifier: row.nt_term_identifier)
}

destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_USE='Correlative'    
	where ASSAY_USE='CORRELATIVE'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_USE='Integrated'    
	where ASSAY_USE='INTEGRATED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_USE='Integral'    
	where ASSAY_USE='INTEGRAL'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_USE='Research'    
	where ASSAY_USE='RESEARCH'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_USE='Unspecified'    
	where ASSAY_USE='UNSPECIFIED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_USE='Unclear'    
	where ASSAY_USE='UNCLEAR'""")


destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_TYPE_CODE='RT-PCR'    
	where ASSAY_TYPE_CODE='RT_PCR'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_TYPE_CODE='Ligand Binding Assay'    
	where ASSAY_TYPE_CODE='LIGAND_BINDING_ASSAY'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_TYPE_CODE='In Situ Hybridization'    
	where ASSAY_TYPE_CODE='IN_SITU_HYBRIDIZATION'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_TYPE_CODE='Microarray'    
	where ASSAY_TYPE_CODE='MICROARRAY'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_TYPE_CODE='Comparative Genomic Hybridization'    
	where ASSAY_TYPE_CODE='CGH'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_TYPE_CODE='Loss of Heterozygosity'    
	where ASSAY_TYPE_CODE='LOH'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_TYPE_CODE='Fluorescence in situ hybridization'    
	where ASSAY_TYPE_CODE='FISH'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_TYPE_CODE='Immunohistochemistry'    
	where ASSAY_TYPE_CODE='IMMUNOHISTOCHEMISTRY'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_TYPE_CODE='Serum ECD Test'    
	where ASSAY_TYPE_CODE='SERUM_ECD_TEST'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_TYPE_CODE='Unspecified'    
	where ASSAY_TYPE_CODE='UNSPECIFIED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_TYPE_CODE='Other'    
	where ASSAY_TYPE_CODE='OTHER'""")


destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_PURPOSE='Stratification Factor'    
	where ASSAY_PURPOSE='STRATIFICATION_FACTOR'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_PURPOSE='Treatment Assignment'    
	where ASSAY_PURPOSE='TREATMENT_ASSIGNMENT'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_PURPOSE='Eligibility Criterion'    
	where ASSAY_PURPOSE='ELIGIBILITY_CRITERION'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_PURPOSE='Research'    
	where ASSAY_PURPOSE='RESEARCH'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_PURPOSE='PD - Dose Adjustment'    
	where ASSAY_PURPOSE='PD'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_PURPOSE='Unspecified'    
	where ASSAY_PURPOSE='UNSPECIFIED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET ASSAY_PURPOSE='Other'    
	where ASSAY_PURPOSE='OTHER'""")

destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET TISSUE_COLLECTION_METHOD_CODE='Unspecified'    
	where TISSUE_COLLECTION_METHOD_CODE='UNSPECIFIED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET TISSUE_COLLECTION_METHOD_CODE='Voluntary'    
	where TISSUE_COLLECTION_METHOD_CODE='VOLUNTARY'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET TISSUE_COLLECTION_METHOD_CODE='Mandatory on Consent'    
	where TISSUE_COLLECTION_METHOD_CODE='MANDATORY_ON_CONSENT'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET TISSUE_COLLECTION_METHOD_CODE='Mandatory'    
	where TISSUE_COLLECTION_METHOD_CODE='MANDATORY'""")
	
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET TISSUE_SPECIMEN_TYPE_CODE='Unspecified'    
	where TISSUE_SPECIMEN_TYPE_CODE='UNSPECIFIED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET TISSUE_SPECIMEN_TYPE_CODE='Serum'    
	where TISSUE_SPECIMEN_TYPE_CODE='SERUM'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET TISSUE_SPECIMEN_TYPE_CODE='Plasma'    
	where TISSUE_SPECIMEN_TYPE_CODE='PLASMA'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET TISSUE_SPECIMEN_TYPE_CODE='Whole Blood'    
	where TISSUE_SPECIMEN_TYPE_CODE='WHOLE_BLOOD'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET TISSUE_SPECIMEN_TYPE_CODE='Tissue'    
	where TISSUE_SPECIMEN_TYPE_CODE='TISSUE'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET TISSUE_SPECIMEN_TYPE_CODE='Citrated Plasma'    
	where TISSUE_SPECIMEN_TYPE_CODE='CITRATED_PLASMA'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_BIOMARKER SET TISSUE_SPECIMEN_TYPE_CODE='Other Fluid'    
	where TISSUE_SPECIMEN_TYPE_CODE='OTHER_FLUID'""")

sourceConnection.close()
destinationConnection.close()
	
	