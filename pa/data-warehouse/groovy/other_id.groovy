import groovy.sql.Sql
def sql = """SELECT
				oid.identifier_name,
				oid.extension,
				nci_id.extension as nci
                FROM STUDY_OTHERIDENTIFIERS oid
                inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = oid.study_protocol_id
                    and nci_id.root = '2.16.840.1.113883.3.26.4.3' 
                inner join study_protocol sp on nci_id.study_protocol_id=sp.identifier and sp.status_code='ACTIVE'
                where oid.identifier_name NOT LIKE 'NCI study protocol entity identifier'
                    """                   

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def oids = destinationConnection.dataSet("STG_DW_STUDY_OTHER_IDENTIFIER");

sourceConnection.eachRow(sql) { row ->
    oids.add(
            nci_id: row.nci,
            name: row.identifier_name,
            value: row.extension
            )
            }
            

sourceConnection.close()
destinationConnection.close()