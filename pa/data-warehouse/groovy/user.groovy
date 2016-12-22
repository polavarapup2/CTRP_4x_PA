import groovy.sql.Sql

def sql = """select ru.affiliated_org_id,
                    org.name as affiliated_org,
                    cu.user_id, 
                    cu.login_name,
                    CASE WHEN NULLIF(ru.first_name, '') is not null THEN ru.first_name || ' ' || ru.last_name
                      WHEN NULLIF(split_part(cu.login_name, 'CN=', 2), '') is null THEN cu.login_name
                      ELSE split_part(cu.login_name, 'CN=', 2) 
                    END as name,
                    ru.identifier,
                    ru.city,
                    ru.country,
                    ru.date_last_created,
                    ru.date_last_updated,
                    ru.email_address,
                    ru.enable_emails,
                    ru.first_name,
                    ru.last_name,
                    substr(ru.middle_name, 1, 1) as middle_initial,
                    ru.phone,
                    ru.postal_code,
                    ru.prs_org_name,
                    CASE WHEN (ru.affiliated_org_user_type = 'ADMIN') THEN 'Yes'
                         WHEN (ru.affiliated_org_user_type = 'MEMBER') THEN 'No'
                         WHEN (ru.affiliated_org_user_type = 'PENDING_ADMIN') THEN 'Pending Approval'
                         ELSE 'Information Not Available'
                    END as site_admin,
                    ru.state,
                    ru.address_line,
                    split_part(cu.login_name, 'CN=', 2) as user_name,
                    ru.user_last_created_id,
                    ru.user_last_updated_id
		from csm_user cu
		left outer join registry_user as ru on ru.csm_user_id = cu.user_id 
		left outer join organization as org on org.assigned_identifier::INTEGER=ru.affiliated_org_id
                """

def sourceConnectionPa = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def users = destinationConnection.dataSet("STG_DW_USER")

sourceConnectionPa.eachRow(sql) { row ->
    users.add(
        AFFILIATED_ORGANIZATION_ID: row.affiliated_org_id,
        AFFILIATED_ORGANIZATION: row.affiliated_org,
        INTERNAL_SYSTEM_ID : row.identifier,
		CSM_USER_ID: row.user_id,
		LOGIN_NAME: row.login_name,
		NAME: row.name,
		CITY: row.city,
		COUNTRY: row.country,
        CTRP_ACCESS_PRIVILEGES: '',
        DATE_LAST_CREATED: row.date_last_created,
        DATE_LAST_UPDATED: row.date_last_updated,
        EMAIL: row.email_address,
        EMAIL_NOTIFICATION_REQUIRED: row.enable_emails,
		FIRST_NAME: row.first_name,
		LAST_NAME: row.last_name,
        MIDDLE_INITIAL: row.middle_initial,
        PHONE: row.phone,
        POSTAL_CODE: row.postal_code,
        PRS_ORGANIZATION: row.prs_org_name,
        SITE_ADMIN: row.site_admin,
        STATE: row.state,
        STREET_ADDRESS: row.address_line,
        USER_NAME: row.user_name,
        USER_LAST_CREATED_ID: row.user_last_created_id,
        USER_LAST_UPDATED_ID: row.user_last_updated_id
	)
}

def getPrivSql = """
    select user_id, group_name 
    from csm_user
    join csm_user_group using (user_id)
    join csm_group using (group_id)
    order by user_id, group_name
    """

def setPrivSql = """
    update STG_DW_USER set ctrp_access_privileges = ctrp_access_privileges || ? || '; '
    where csm_user_id = ?
    """

sourceConnectionPa.eachRow(getPrivSql) { row ->
    destinationConnection.executeUpdate(setPrivSql, [row.group_name, row.user_id])
}

destinationConnection.execute("""UPDATE stg_dw_user absu
                                 SET user_name_last_created = us.name 
                                 FROM stg_dw_user us where absu.user_last_created_id = us.csm_user_id""");

destinationConnection.execute("""UPDATE stg_dw_user absu
                                 SET user_name_last_updated = us.name 
                                 FROM stg_dw_user us where absu.user_last_updated_id = us.csm_user_id""");
							 
							 
							 sourceConnectionPa.close()
							 destinationConnection.close()
