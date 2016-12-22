import groovy.sql.Sql

def sql =
    """SELECT
            sp.amendment_date,
            sp.amendment_number, 
            sp.amendment_reason_code,
            sp.identifier as system_id,
			sp.submission_number,
			submitter.first_name || ' ' || submitter.last_name as submitter,
            nci_id.extension,		
            org.name
            from STUDY_PROTOCOL sp
            inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
            and nci_id.root = '2.16.840.1.113883.3.26.4.3'
            left outer join registry_user as submitter on submitter.csm_user_id = sp.user_last_created_id
            left outer join organization as org on submitter.affiliated_org_id = org.assigned_identifier::integer
            where sp.amendment_date is not null"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
        properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
        properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def amendments = destinationConnection.dataSet("STG_DW_STUDY_AMENDMENT")

sourceConnection.eachRow(sql) { row ->
    destinationConnection.withTransaction {
        try {
            amendments.add(
            amendment_date: row.amendment_date,
            number: row.amendment_number, 
            internal_system_id: row.system_id,
            submission_number: row.submission_number, 
            nci_id: row.extension,
            submitter_name: row.submitter,
            associated_organization: row.name)
        } catch (Exception e) {
            println "Error adding row : " + row + " - " + e.getMessage(); 
        }
    }
}

sourceConnection.close()
destinationConnection.close()