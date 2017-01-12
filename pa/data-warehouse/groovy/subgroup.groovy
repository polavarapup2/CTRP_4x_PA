import groovy.sql.Sql

def sql = """select gr.description,
			 gr.group_number_text as group_code,
			 nci_id.extension 
			 from stratum_group gr
             inner join study_otheridentifiers as nci_id 
             	on nci_id.study_protocol_id = gr.study_protocol_identifier
             	and nci_id.root = '2.16.840.1.113883.3.26.4.3'"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'], 
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def subgroups = destinationConnection.dataSet("STG_DW_STUDY_SUBGROUP")

sourceConnection.eachRow(sql) { row ->
    subgroups.add(
    	description: row.description,
    	group_code: row.group_code, 
    	nci_id: row.extension)
}

sourceConnection.close()
destinationConnection.close()