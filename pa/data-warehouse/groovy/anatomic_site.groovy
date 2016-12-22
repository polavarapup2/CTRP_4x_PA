import groovy.sql.Sql
def sql = """select asite.display_name, nci_id.extension from study_anatomic_site sas
            inner join study_protocol as sp on sp.identifier = sas.study_protocol_identifier
            inner join anatomic_sites as asite on asite.identifier = sas.anatomic_sites_identifier
            inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sas.study_protocol_identifier and nci_id.root = '2.16.840.1.113883.3.26.4.3'
            where sp.status_code = 'ACTIVE'"""
def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'], 
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def anatomic_sites = destinationConnection.dataSet("STG_DW_STUDY_ANATOMIC_SITE")
sourceConnection.eachRow(sql) { row ->
    anatomic_sites.add(anatomic_site_name: row.display_name, nci_id: row.extension)
}

sourceConnection.close()
destinationConnection.close()