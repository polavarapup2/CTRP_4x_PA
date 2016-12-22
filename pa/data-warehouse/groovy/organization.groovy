import groovy.sql.Sql

def sql = """select 
			 add.streetaddressline,
			 add.deliveryaddressline,
			 add.postalcode,
			 add.cityormunicipality,
			 country.name as country_name,
			 org.id,
			 org.status,
			 org.statusdate,
			 add.stateorprovince,
			 org.name as orgname,
			 crcount
			 from Organization org
			 join address add on add.id = org.postal_address_id
			 join country on country.id = add.country_id
			 left outer join (select count(*) as crcount, target from organizationcr group by target) AS orgcr
				on orgcr.target = org.id
			"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.po.jdbc.url'], properties['datawarehouse.po.db.username'], 
    properties['datawarehouse.po.db.password'], properties['datawarehouse.po.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def orgs = destinationConnection.dataSet("STG_DW_ORGANIZATION")

sourceConnection.eachRow(sql) { row ->
    orgs.add(
    	address_line_1: row.streetaddressline,
    	address_line_2: row.deliveryaddressline,
    	postal_code: row.postalcode,
    	city: row.cityormunicipality,
    	country: row.country_name,
        name: row.orgname,
 		po_id: row.id,
 		status: row.status,
 		status_date: row.statusdate,
 		state_or_province: row.stateorprovince,
 	 	internal_id: row.id,
 	 	CHANGE_REQUEST_COUNT: row.crcount
	)
}

destinationConnection.execute("UPDATE STG_DW_ORGANIZATION set CHANGE_REQUEST_COUNT = 0 where CHANGE_REQUEST_COUNT is null")

sourceConnection.eachRow("""SELECT rel.organization_id, c.value
                            FROM organization_comment rel
                            JOIN comment c ON (c.id = rel.comment_id)""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization
                                     SET curator_comment = curator_comment || chr(13) || ?
                                     WHERE po_id = ?
                                       AND curator_comment IS NOT NULL
                                  """, [row.value, row.organization_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization
                                     SET curator_comment = ?
                                     WHERE po_id = ?
                                       AND curator_comment IS NULL
                                  """, [row.value, row.organization_id]);
}

sourceConnection.eachRow("""SELECT player_id, assigned_identifier_extension
                            FROM identifiedorganization
							WHERE assigned_identifier_root = '2.16.840.1.113883.3.26.6.2'
							  AND status IN ('ACTIVE','PENDING')""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization
                                     SET ctep_id = ctep_id || chr(13) || ?
                                     WHERE po_id = ?
                                       AND ctep_id IS NOT NULL
                                  """, [row.assigned_identifier_extension, row.player_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization
                                     SET ctep_id = ?
                                     WHERE po_id = ?
                                       AND ctep_id IS NULL
                                  """, [row.assigned_identifier_extension, row.player_id]);
}

sourceConnection.eachRow("""SELECT rel.organization_id, e.value
                            FROM organization_email rel
                            JOIN email e ON (e.id = rel.email_id)""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization
                                     SET email = email || chr(13) || ?
                                     WHERE po_id = ?
                                       AND email IS NOT NULL
                                  """, [row.value, row.organization_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization
                                     SET email = ?
                                     WHERE po_id = ?
                                       AND email IS NULL
                                  """, [row.value, row.organization_id]);
}
sourceConnection.eachRow("""SELECT rel.organization_id, e.value
                            FROM organization_fax rel
                            JOIN phonenumber e ON (e.id = rel.fax_id)""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization
                                     SET fax = fax || chr(13) || ?
                                     WHERE po_id = ?
                                       AND fax IS NOT NULL
                                  """, [row.value, row.organization_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization
                                     SET fax = ?
                                     WHERE po_id = ?
                                       AND fax IS NULL
                                  """, [row.value, row.organization_id]);
}
sourceConnection.eachRow("""SELECT rel.organization_id, e.value
                            FROM organization_phone rel
                            JOIN phonenumber e ON (e.id = rel.phone_id)""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization
                                     SET phone = phone || chr(13) || ?
                                     WHERE po_id = ?
                                       AND phone IS NOT NULL
                                  """, [row.value, row.organization_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization
                                     SET phone = ?
                                     WHERE po_id = ?
                                       AND phone IS NULL
                                  """, [row.value, row.organization_id]);
}
sourceConnection.eachRow("""SELECT rel.organization_id, e.value
                            FROM organization_tty rel
                            JOIN phonenumber e ON (e.id = rel.tty_id)""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization
                                     SET tty = tty || chr(13) || ?
                                     WHERE po_id = ?
                                       AND tty IS NOT NULL
                                  """, [row.value, row.organization_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization
                                     SET tty = ?
                                     WHERE po_id = ?
                                       AND tty IS NULL
                                  """, [row.value, row.organization_id]);
}
sourceConnection.eachRow("""SELECT rel.organization_id, e.name, rel.functionaltype
                            FROM familyorganizationrelationship rel
                            JOIN family e ON (e.id = rel.family_id)
							WHERE rel.enddate IS NULL OR rel.enddate > NOW()""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization
                                     SET family = family || chr(13) || ?
									   , org_to_family_relationship = org_to_family_relationship || chr(13) || ?
                                     WHERE po_id = ?
                                       AND family IS NOT NULL
                                  """, [row.name, row.functionaltype, row.organization_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization
                                     SET family = ?
									   , org_to_family_relationship = ?
                                     WHERE po_id = ?
                                       AND family IS NULL
                                  """, [row.name, row.functionaltype, row.organization_id]);
}
							
							sourceConnection.close()
							destinationConnection.close()

