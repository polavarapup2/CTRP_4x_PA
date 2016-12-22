import groovy.sql.Sql
def sql = """
    SELECT ssaa.status_code AS access_status, 
       ru.identifier as accrual_admin_id,
       CASE WHEN ru.first_name IS NOT NULL THEN ru.first_name || ' ' ELSE '' END 
           || CASE WHEN ru.middle_name IS NOT NULL THEN ru.middle_name || ' ' ELSE '' END 
           || CASE WHEN ru.last_name IS NOT NULL THEN ru.last_name ELSE '' END 
           AS accrual_admin,
       ssaa.date_last_created,
       ssaa.date_last_updated, 
       request_details, 
       ssaa.user_last_created_id,
       ssaa.user_last_updated_id,
       ss.identifier AS internal_system_id
    FROM study_site_accrual_access ssaa
    JOIN study_site ss ON (ssaa.study_site_identifier = ss.identifier)
    JOIN registry_user ru ON (ssaa.registry_user_id = ru.identifier)
    LEFT OUTER JOIN csm_user AS creator ON (ssaa.user_last_created_id = creator.user_id)   
    LEFT OUTER JOIN registry_user AS ru_creator ON (ru_creator.csm_user_id = creator.user_id)
    LEFT OUTER JOIN csm_user AS updater ON (ssaa.user_last_created_id = updater.user_id)
    LEFT OUTER JOIN registry_user AS ru_updater ON (ru_updater.csm_user_id = updater.user_id)
"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def ssaa = destinationConnection.dataSet("stg_dw_study_site_accrual_access");

sourceConnection.eachRow(sql) { row ->
    ssaa.add(
        access_status: row.access_status, 
        accrual_admin_id: row.accrual_admin_id,
        accrual_admin: row.accrual_admin, 
        date_last_created: row.date_last_created, 
        date_last_updated: row.date_last_updated, 
        request_details: row.request_details, 
        user_last_created_id: row.user_last_created_id, 
        user_last_updated_id: row.user_last_updated_id,
        internal_system_id: row.internal_system_id
    )};
    
destinationConnection.execute("""UPDATE stg_dw_study_site_accrual_details ssad
                                 SET user_name_last_created = us.name 
                                 FROM stg_dw_user us where ssad.user_last_created_id = us.csm_user_id""");
   
destinationConnection.execute("""UPDATE stg_dw_study_site_accrual_details ssad
                                 SET user_name_last_updated = us.name 
                                 FROM stg_dw_user us where ssad.user_last_updated_id = us.csm_user_id""");
    
destinationConnection.execute("""UPDATE stg_dw_study_site_accrual_access ssaa
                                 SET nci_id = ps.nci_id, 
                                     org_name = ps.org_name, 
                                     org_org_family = ps.org_org_family,
                                     org_recruitment_status = ps.recruitment_status                        
                                 FROM stg_dw_study_participating_site ps where ssaa.internal_system_id = ps.internal_system_id""");
							 
							 sourceConnection.close()
							 destinationConnection.close()
