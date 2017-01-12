import groovy.sql.Sql

def sql = """select email_address, affiliate_org, affiliated_org_id 
		from registry_user router
		where identifier in (
			select identifier 
                     	from registry_user rinner
                     	where router.email_address = rinner.email_address
                     	fetch first row only
                )"""

def sourceConnectionPa = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def affiliate_orgs = destinationConnection.dataSet("STG_DW_AFFILIATE_ORG")

sourceConnectionPa.eachRow(sql) { row ->
    affiliate_orgs.add(
		email_address: row.email_address,
		affiliate_org: row.affiliate_org,
		affiliated_org_id: row.affiliated_org_id
	)
}

sourceConnectionPa.close()
destinationConnection.close()