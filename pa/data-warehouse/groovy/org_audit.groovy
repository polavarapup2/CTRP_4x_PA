import groovy.sql.Sql

def sourceConnection = Sql.newInstance(properties['datawarehouse.po.jdbc.url'], properties['datawarehouse.po.db.username'], 
    properties['datawarehouse.po.db.password'], properties['datawarehouse.po.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def audits = destinationConnection.dataSet("STG_DW_ORGANIZATION_AUDIT")

def maxId = 0;
try {
    def maxRow = destinationConnection.firstRow("select max(internal_system_id) from dw_organization_audit");
    if (maxRow[0] != null) {
        maxId = maxRow[0];
    }
} catch (Exception e) {
    println "Error reading maximum current value from dw_organization_audit... rebuilding table.";
    destinationConnection.executeUpdate("DROP TABLE IF EXISTS DW_ORGANIZATION_AUDIT");
    destinationConnection.executeUpdate("ALTER TABLE STG_DW_ORGANIZATION_AUDIT RENAME TO DW_ORGANIZATION_AUDIT");
    
    def file = new File("..//pa//data-warehouse//sql//org_audit.sql");
    if (!file.exists()) {
        file = new File("sql//org_audit.sql");
		if (!file.exists()) {
			file = new File("pa//data-warehouse//sql//org_audit.sql");
		}
    }
    destinationConnection.executeUpdate(file.getText());
}

def sql = """select 
			 audit.id as audit_id,
			 org.name,
			 org.id,
			 audit.username,
			 audit.createddate,
			 ctepid.assigned_identifier_extension as ctep_id 
			 from Auditlogrecord audit
			 join organization org on org.id = audit.entityid
			     and audit.id > ${maxId}
			 left outer join identifiedorganization ctepid on ctepid.player_id = org.id and ctepid.assigned_identifier_root = '2.16.840.1.113883.3.26.6.2'
			 where audit.entityname = 'Organization'
			"""

sourceConnection.eachRow(sql) { row ->
    audits.add(
        internal_system_id: row.audit_id,
    	ctep_id: row.ctep_id,
    	name: row.name,
    	po_id: row.id,
    	date: row.createddate,
    	username: row.username
	)
}

sourceConnection.close()
destinationConnection.close()