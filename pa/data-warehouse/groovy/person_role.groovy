import groovy.sql.Sql

def sql = """select 
             crs.id,
             crs.person_id,
             crs.status,
             crs.statusdate,
             org.name as organization
             from clinicalresearchstaff crs
             join organization org on crs.organization_id = org.id
			"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.po.jdbc.url'], properties['datawarehouse.po.db.username'], 
    properties['datawarehouse.po.db.password'], properties['datawarehouse.po.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def persons = destinationConnection.dataSet("STG_DW_PERSON_ROLE")

sourceConnection.eachRow(sql) { row ->
    persons.add(
        person_po_id: row.person_id,
 		role_po_id: row.id,
 		status: row.status,
 		status_date: row.statusdate,
 	 	role_name: "Clinical Research Staff",
 	 	organization_name: row.organization
	)
}

sql = """select 
			 hcp.id,
             hcp.person_id,
			 hcp.status,
			 hcp.statusdate,
			 org.name as organization
			 from healthcareprovider hcp
			 join organization org on hcp.organization_id = org.id
			"""

sourceConnection.eachRow(sql) { row ->
    persons.add(
        person_po_id: row.person_id,
        role_po_id: row.id,
 		status: row.status,
 		status_date: row.statusdate,
	 	role_name: "Healthcare Provider",
 	 	organization_name: row.organization
	)
}

destinationConnection.executeUpdate("""UPDATE stg_dw_person_role pr
                                       SET address_line_1 = person.address_line_1,
                                           address_line_2 = person.address_line_2,
                                           postal_code = person.postal_code,
                                           city = person.city,
                                           country = person.country,
                                           curator_comment = person.curator_comment,
                                           state_or_province = person.state_or_province,
                                           email = person.email,
                                           fax = person.fax,
                                           phone = person.phone,
                                           tty = person.tty
                                       FROM stg_dw_person person
                                       WHERE pr.person_po_id = person.po_id""");
								   
								   sourceConnection.close()
								   destinationConnection.close()

