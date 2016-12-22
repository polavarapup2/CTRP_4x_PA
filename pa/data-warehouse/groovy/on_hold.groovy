import groovy.sql.Sql
def sql = """SELECT
				hold.onhold_reason_text,
				hold.onhold_reason_code,
				hold.identifier,
				hold.onhold_date,
				hold.offhold_date,
				hold.date_last_created, 
				hold.date_last_updated,
                hold.user_last_created_id,
                hold.user_last_updated_id,
                nci_id.extension
                FROM STUDY_ONHOLD hold
                inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = hold.study_protocol_identifier
                    and nci_id.root = '2.16.840.1.113883.3.26.4.3'
                join study_protocol sp on (sp.identifier = hold.study_protocol_identifier)
                where sp.status_code = 'ACTIVE'
              """

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def holds = destinationConnection.dataSet("STG_DW_STUDY_ON_HOLD_STATUS");

sourceConnection.eachRow(sql) { row ->
    holds.add(
    		reason_description: row.onhold_reason_text,
    		reason: row.onhold_reason_code,
    		on_hold_date: row.onhold_date,
    		off_hold_date: row.offhold_date,
    		date_created: row.date_last_created,
    		date_last_updated: row.date_last_updated,
            internal_system_id: row.identifier,
            nci_id: row.extension,
            user_last_created_id: row.user_last_created_id,
            user_last_updated_id: row.user_last_updated_id
            )
}

destinationConnection.execute("""UPDATE STG_DW_STUDY_ON_HOLD_STATUS holds
                                 SET user_name_created = us.name,
                                     first_name_created = us.first_name,
                                     last_name_created = us.last_name
                                 FROM stg_dw_user us where holds.user_last_created_id = us.csm_user_id""");

destinationConnection.execute("""UPDATE STG_DW_STUDY_ON_HOLD_STATUS holds
                                 SET user_name_last_updated = us.name,
                                     first_name_last_updated = us.first_name,
                                     last_name_last_updated = us.last_name 
                                 FROM stg_dw_user us where holds.user_last_updated_id = us.csm_user_id""");


							 sourceConnection.close()
							 destinationConnection.close()