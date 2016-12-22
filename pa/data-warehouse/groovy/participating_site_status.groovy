import groovy.sql.Sql
def sql = """SELECT 
                sos.date_last_created, 
                sos.date_last_updated, 
                sos.identifier,
                sos.study_site_identifier,
                sos.status_code,
                sos.status_date,
                sos.comments,
                CASE WHEN NULLIF(ru_creator.first_name, '') is not null THEN ru_creator.first_name || ' ' || ru_creator.last_name
                    WHEN NULLIF(split_part(creator.login_name, 'CN=', 2), '') is null THEN creator.login_name
                    ELSE split_part(creator.login_name, 'CN=', 2)
                END as creator,
                CASE WHEN NULLIF(ru_updater.first_name, '') is not null THEN ru_updater.first_name || ' ' || ru_updater.last_name
                    WHEN NULLIF(split_part(updater.login_name, 'CN=', 2), '') is null THEN updater.login_name
                    ELSE split_part(updater.login_name, 'CN=', 2)
                END as updater
                FROM STUDY_SITE_ACCRUAL_STATUS sos
				inner join study_site as ss on sos.study_site_identifier=ss.identifier 
                left outer join csm_user as creator on sos.user_last_created_id = creator.user_id
                left outer join registry_user as ru_creator on ru_creator.csm_user_id = creator.user_id
                left outer join csm_user as updater on sos.user_last_created_id = updater.user_id
                left outer join registry_user as ru_updater on ru_updater.csm_user_id = updater.user_id
				where ss.functional_code = 'TREATING_SITE' and sos.deleted=false
                """

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def soses = destinationConnection.dataSet("STG_DW_STUDY_PARTICIPATING_SITE_ACCRUAL_STATUS");

sourceConnection.eachRow(sql) { row ->
    soses.add(
    		date_created: row.date_last_created, 
    		date_last_updated: row.date_last_updated,
            internal_system_id: row.identifier, 
            study_site_id: row.study_site_identifier, 
            user_created: row.creator, 
            user_last_updated: row.updater,
            status: row.status_code,
            status_date: row.status_date,
            comments: row.comments
            )
}

sourceConnection.close()
destinationConnection.close()