import groovy.sql.Sql

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def audit = destinationConnection.dataSet("STG_DW_STUDY_AUDIT")

def maxId = 0;
try {
    def maxRow = destinationConnection.firstRow("select max(internal_system_id) from dw_study_audit");
    if (maxRow[0] != null) {
        maxId = maxRow[0];
    }
} catch (Exception e) {
    println "Error reading maximum current value from dw_study_audit... rebuilding table.";
    destinationConnection.executeUpdate("DROP TABLE IF EXISTS DW_STUDY_AUDIT");
    destinationConnection.executeUpdate("ALTER TABLE STG_DW_STUDY_AUDIT RENAME TO DW_STUDY_AUDIT");
    def file = new File("..//pa//data-warehouse//sql//study_audit.sql");
    if (!file.exists()) {
        file = new File("sql//study_audit.sql");
		if (!file.exists()) {
		        file = new File("pa//data-warehouse//sql//study_audit.sql");
 	        }
    }
    destinationConnection.executeUpdate(file.getText());
}

def spSql = """
    select 
        id,
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
        join study_protocol sp on sp.identifier = audit.entityid and audit.entityname = 'STUDY_PROTOCOL'
                and audit.id > ${maxId}
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
    """

def dwsSql = """
    select 
        id,
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join document_workflow_status dws on dws.identifier = audit.entityid and entityname = 'DOCUMENT_WORKFLOW_STATUS'
                and audit.id > ${maxId}
        join study_protocol sp on sp.identifier = dws.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
    """

def ssSql = """
    select 
        id,
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join study_site ss on ss.identifier = audit.entityid and entityname = 'STUDY_SITE'
                and audit.id > ${maxId}
        join study_protocol sp on sp.identifier = ss.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def docSql = """
    select 
        id,
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join document doc on doc.identifier = audit.entityid and entityname = 'DOCUMENT'
                and audit.id > ${maxId}
        join study_protocol sp on sp.identifier = doc.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def paSql = """
    select 
        id,
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join PLANNED_ACTIVITY pa on pa.identifier = audit.entityid and entityname = 'PLANNED_ACTIVITY'
                and audit.id > ${maxId}
        join study_protocol sp on sp.identifier = pa.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def holdSql = """
    select 
        id,
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_ONHOLD obj on obj.identifier = audit.entityid and entityname = 'STUDY_ONHOLD'
                and audit.id > ${maxId}
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def indSql = """
    select 
        id,
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_INDLDE obj on obj.identifier = audit.entityid and entityname = 'STUDY_INDLDE'
                and audit.id > ${maxId}
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'

      """

def inboxSql = """
    select 
        id,
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_INBOX obj on obj.identifier = audit.entityid and entityname = 'STUDY_INBOX'
                and audit.id > ${maxId}
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def omSql = """
    select 
        id,
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_OUTCOME_MEASURE obj on obj.identifier = audit.entityid and entityname = 'STUDY_OUTCOME_MEASURE'
                and audit.id > ${maxId}
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def recSql = """
    select 
        id,
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_RECRUITMENT_STATUS obj on obj.identifier = audit.entityid and entityname = 'STUDY_RECRUITMENT_STATUS'
                and audit.id > ${maxId}
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def regSql = """
    select 
        id,
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_REGULATORY_AUTHORITY obj on obj.identifier = audit.entityid and entityname = 'STUDY_REGULATORY_AUTHORITY'
                and audit.id > ${maxId}
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def armSql = """
    select 
        id,
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join ARM obj on obj.identifier = audit.entityid and entityname = 'ARM'
                and audit.id > ${maxId}
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def groupSql = """
    select 
        id,
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STRATUM_GROUP obj on obj.identifier = audit.entityid and entityname = 'STRATUM_GROUP'
                and audit.id > ${maxId}
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def objSql = """
    select 
        id,
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_OBJECTIVE obj on obj.identifier = audit.entityid and entityname = 'STUDY_OBJECTIVE'
                and audit.id > ${maxId}
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'

      """

def conSql = """
    select 
        id,
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_CONTACT obj on obj.identifier = audit.entityid and entityname = 'STUDY_CONTACT'
                and audit.id > ${maxId}
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def osSql = """
    select 
        id,
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_OVERALL_STATUS obj on obj.identifier = audit.entityid and entityname = 'STUDY_OVERALL_STATUS'
                and audit.id > ${maxId}
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def resSql = """
    select 
        id,
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_RESOURCING obj on obj.identifier = audit.entityid and entityname = 'STUDY_RESOURCING'
                and audit.id > ${maxId}
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def disSql = """
    select 
        id,
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_DISEASE obj on obj.identifier = audit.entityid and entityname = 'STUDY_DISEASE'
                and audit.id > ${maxId}
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

println "Protocol"
sourceConnection.eachRow(spSql) { row ->
        audit.add(
                internal_system_id: row.id,
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "DWS"
sourceConnection.eachRow(dwsSql) { row ->
        audit.add(
                internal_system_id: row.id,
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "SS"
sourceConnection.eachRow(ssSql) { row ->
        audit.add(
                internal_system_id: row.id,
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Doc"
sourceConnection.eachRow(docSql) { row ->
        audit.add(
                internal_system_id: row.id,
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "PA"
sourceConnection.eachRow(paSql) { row ->
        audit.add(
                internal_system_id: row.id,
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Hold"
sourceConnection.eachRow(holdSql) { row ->
        audit.add(
                internal_system_id: row.id,
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "IND"
sourceConnection.eachRow(indSql) { row ->
        audit.add(
                internal_system_id: row.id,
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "inbox"
sourceConnection.eachRow(inboxSql) { row ->
        audit.add(
                internal_system_id: row.id,
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "OM"
sourceConnection.eachRow(omSql) { row ->
        audit.add(
                internal_system_id: row.id,
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Rec"
sourceConnection.eachRow(recSql) { row ->
        audit.add(
                internal_system_id: row.id,
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Reg"
sourceConnection.eachRow(regSql) { row ->
        audit.add(
                internal_system_id: row.id,
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Arm"
sourceConnection.eachRow(armSql) { row ->
        audit.add(
                internal_system_id: row.id,
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Group"
sourceConnection.eachRow(groupSql) { row ->
        audit.add(
                internal_system_id: row.id,
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Objective"
sourceConnection.eachRow(objSql) { row ->
        audit.add(
                internal_system_id: row.id,
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Contact"
sourceConnection.eachRow(conSql) { row ->
        audit.add(
                internal_system_id: row.id,
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Overall Status"
sourceConnection.eachRow(osSql) { row ->
        audit.add(
                internal_system_id: row.id,
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Resourcing"
sourceConnection.eachRow(resSql) { row ->
        audit.add(
                internal_system_id: row.id,
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Disease"
sourceConnection.eachRow(disSql) { row ->
        audit.add(
                internal_system_id: row.id,
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Populate name columns"
def usql = """
    UPDATE STG_DW_STUDY_AUDIT a
    SET last_name = b.last_name,
        first_name = b.first_name
    FROM stg_dw_user b WHERE lower(a.username) = lower(b.login_name)
    """
destinationConnection.executeUpdate(usql)

sourceConnection.close()
destinationConnection.close()
