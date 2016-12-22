import groovy.sql.Sql
def sql = """SELECT 
                sos.date_last_created, 
                sos.date_last_updated, 
                sos.identifier,
                nci_id.extension,
                sos.status_code,
                sos.status_date,
                sos.comment_text,
                sos.addl_comments,
                sos.system_created,
                CASE WHEN NULLIF(ru_creator.first_name, '') is not null THEN ru_creator.first_name || ' ' || ru_creator.last_name
                    WHEN NULLIF(split_part(creator.login_name, 'CN=', 2), '') is null THEN creator.login_name
                    ELSE split_part(creator.login_name, 'CN=', 2)
                END as creator,
                CASE WHEN NULLIF(ru_updater.first_name, '') is not null THEN ru_updater.first_name || ' ' || ru_updater.last_name
                    WHEN NULLIF(split_part(updater.login_name, 'CN=', 2), '') is null THEN updater.login_name
                    ELSE split_part(updater.login_name, 'CN=', 2)
                END as updater
                FROM STUDY_OVERALL_STATUS sos
                inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sos.study_protocol_identifier
                    and nci_id.root = '2.16.840.1.113883.3.26.4.3'
				inner join study_protocol as sp on sos.study_protocol_identifier=sp.identifier 
                left outer join csm_user as creator on sos.user_last_created_id = creator.user_id
                left outer join registry_user as ru_creator on ru_creator.csm_user_id = creator.user_id
                left outer join csm_user as updater on sos.user_last_created_id = updater.user_id
                left outer join registry_user as ru_updater on ru_updater.csm_user_id = updater.user_id
				where sp.status_code = 'ACTIVE' and sos.deleted=false
                """

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def soses = destinationConnection.dataSet("STG_DW_STUDY_OVERALL_STATUS");

sourceConnection.eachRow(sql) { row ->
    soses.add(
    		date_created: row.date_last_created, 
    		date_last_updated: row.date_last_updated,
            internal_system_id: row.identifier, 
            nci_id: row.extension, 
            user_created: row.creator, 
            user_last_updated: row.updater,
            status: row.status_code,
            status_date: row.status_date,
            system_Created: row.system_created,
            why_study_stopped: row.comment_text,
            addl_comments: row.addl_comments
            )
}

sourceConnection.close()
destinationConnection.close()