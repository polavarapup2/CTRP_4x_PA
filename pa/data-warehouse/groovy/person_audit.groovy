import groovy.sql.Sql

def sourceConnection = Sql.newInstance(properties['datawarehouse.po.jdbc.url'], properties['datawarehouse.po.db.username'], 
    properties['datawarehouse.po.db.password'], properties['datawarehouse.po.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def audits = destinationConnection.dataSet("STG_DW_PERSON_AUDIT")

def maxId = 0;
try {
    def maxRow = destinationConnection.firstRow("select max(internal_system_id) from dw_person_audit");
    if (maxRow[0] != null) {
        maxId = maxRow[0];
    }
} catch (Exception e) {
    println "Error reading maximum current value from dw_person_audit... rebuilding table.";
    destinationConnection.executeUpdate("DROP TABLE IF EXISTS DW_PERSON_AUDIT");
    destinationConnection.executeUpdate("ALTER TABLE STG_DW_PERSON_AUDIT RENAME TO DW_PERSON_AUDIT");
    def file = new File("..//pa//data-warehouse//sql//person_audit.sql");
    if (!file.exists()) {
        file = new File("sql//person_audit.sql");
		if (!file.exists()) {
		        file = new File("pa//data-warehouse//sql//person_audit.sql");
	        }
    }
    destinationConnection.executeUpdate(file.getText());
}

def sql = """select 
			 audit.id as audit_id,
			 per.firstname,
			 per.lastname,	
			 per.id,
			 audit.username,
			 audit.createddate,
			 ctepid.assigned_identifier_extension as ctep_id
			 from Auditlogrecord audit
			 join person per on per.id = audit.entityid
			     and audit.id > ${maxId}
			 left outer join identifiedperson ctepid on ctepid.player_id = per.id and ctepid.assigned_identifier_root = 'Cancer Therapy Evaluation Program Person Identifier'
			 where audit.entityname = 'Person'
			"""

sourceConnection.eachRow(sql) { row ->
    audits.add(
        internal_system_id: row.audit_id,
    	ctep_id: row.ctep_id,
    	name: row.firstname + " " + row.lastname,
    	po_id: row.id,
    	date: row.createddate,
    	username: row.username
	)
}


def usql = """
    UPDATE stg_dw_person_audit a
    SET last_name = b.last_name,
        first_name = b.first_name
    FROM stg_dw_user b WHERE lower(a.username) = lower(b.login_name)
    """
destinationConnection.executeUpdate(usql)

def pasql = """
    UPDATE stg_dw_person_audit
    SET last_name = 'PA', first_name = 'PA'
    WHERE lower(username) = 'ejbclient'
    """
destinationConnection.executeUpdate(pasql)
sourceConnection.close()
destinationConnection.close()
