import groovy.sql.Sql

def sql = """
    select 
    	ss.functional_code,
    	ss.status_code,
    	org.name,
    	nci_id.extension
    from study_site ss
        inner join study_protocol as sp on ss.study_protocol_identifier = sp.identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = ss.study_protocol_identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
        left outer join Research_organization as ro on RO.identifier = ss.research_organization_identifier
        left outer join organization as org on org.identifier = RO.organization_identifier
    where ss.functional_code in ('AGENT_SOURCE', 'FUNDING_SOURCE', 'LABORATORY')
      and sp.status_code = 'ACTIVE'
    """

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def collabs = destinationConnection.dataSet("STG_DW_STUDY_COLLABORATOR")

sourceConnection.eachRow(sql) { row ->
        collabs.add(
        	name: row.name,
            status: row.status_code,
            functional_role: row.functional_code,
            nci_id: row.extension
            )
    }

sourceConnection.close()
destinationConnection.close()