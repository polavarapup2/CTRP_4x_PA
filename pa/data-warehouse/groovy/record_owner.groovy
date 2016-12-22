import groovy.sql.Sql
def sql = """SELECT
                ru.identifier as user_id,
                ru.csm_user_id,
				ru.city,
				ru.state,
				ru.phone,
				ru.postal_code,
				ru.address_line,
				ru.email_address,
                nci_id.extension,
                CASE WHEN NULLIF(ru.first_name, '') is not null THEN ru.first_name || ' ' || ru.last_name
                     WHEN NULLIF(split_part(csm.login_name, 'CN=', 2), '') is null THEN csm.login_name
                     ELSE split_part(csm.login_name, 'CN=', 2)
                END as name
                FROM STUDY_OWNER owner
                inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = owner.study_id
                    and nci_id.root = '2.16.840.1.113883.3.26.4.3'                    
                left outer join registry_user as ru on ru.identifier = owner.user_id
                left outer join csm_user as csm on csm.user_id = ru.csm_user_id"""


def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def owners = destinationConnection.dataSet("STG_DW_STUDY_RECORD_OWNER");

sourceConnection.eachRow(sql) { row ->
    owners.add(
            user_id: row.user_id,
            csm_user_id: row.csm_user_id,
    		address_city: row.city,
    		address_line: row.address_line,
    		address_state: row.state,
    		address_zip_code: row.postal_code,
    		email: row.email_address,
            nci_id: row.extension,
            name: row.name,
            phone_number: row.phone)
            }
            

sourceConnection.close()
destinationConnection.close()