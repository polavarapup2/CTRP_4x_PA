import groovy.sql.Sql

def sql = """select 
			 add.streetaddressline,
			 add.deliveryaddressline,
			 add.postalcode,
			 per.birthdate,
			 add.cityormunicipality,
			 country.name as country_name,
			 ctepid.assigned_identifier_extension as ctep_id,
			 per.firstname,
			 per.middlename,
			 per.lastname,	
			 per.id,
			 per.prefix,
			 per.status,
			 per.statusdate,
			 per.sex,
			 per.suffix,
			 add.stateorprovince
			 from Person per
			 join address add on add.id = per.postal_address_id
			 left outer join identifiedperson ctepid on ctepid.player_id = per.id 
			 	and ctepid.assigned_identifier_root = '2.16.840.1.113883.3.26.6.1'
			 	and ctepid.status = 'ACTIVE'
			 left outer join country on country.id = add.country_id
			"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.po.jdbc.url'], properties['datawarehouse.po.db.username'], 
    properties['datawarehouse.po.db.password'], properties['datawarehouse.po.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def persons = destinationConnection.dataSet("STG_DW_PERSON")

sourceConnection.eachRow(sql) { row ->
    persons.add(
    	address_line_1: row.streetaddressline,
    	address_line_2: row.deliveryaddressline,
    	postal_code: row.postalcode,
    	birthdate: row.birthdate,
    	city: row.cityormunicipality,
    	country: row.country_name,
    	ctep_id: row.ctep_id,
    	name: ((row.firstname == null ? "" : row.firstname + " ") 
              + (row.middlename == null ? "" : row.middlename + " ") 
              + (row.lastname == null ? "" : row.lastname)),
 		po_id: row.id,
 		prefix: row.prefix,
 		status: row.status,
 		status_date: row.statusdate,
 		sex_code: row.sex,
 		state_or_province: row.stateorprovince,
 		suffix: row.suffix,
	)
}

sourceConnection.eachRow("""SELECT jt.person_id, c.value
                            FROM person_comment jt
                            JOIN comment c ON c.id = jt.comment_id AND c.value IS NOT NULL
                            ORDER BY jt.idx""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_person
                                           SET curator_comment = COALESCE(curator_comment || ', ' || ?, ?)
                                           WHERE po_id = ?
                                        """, [row.value, row.value, row.person_id]);
}

sourceConnection.eachRow("""SELECT jt.person_id, e.value 
                            FROM person_email jt
                            JOIN email e on e.id = jt.email_id
                            ORDER BY jt.idx""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_person
                                           SET email = COALESCE(email || ', ' || ?, ?)
                                           WHERE po_id = ?
                                        """, [row.value, row.value, row.person_id]);
}

sourceConnection.eachRow("""SELECT jt.person_id, p.value
                            FROM person_fax jt
                            JOIN phonenumber p on p.id = jt.fax_id
                            ORDER BY jt.idx""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_person
                                           SET fax = COALESCE(fax || ', ' || ?, ?)
                                           WHERE po_id = ?
                                        """, [row.value, row.value, row.person_id]);
}
 
sourceConnection.eachRow("""SELECT jt.person_id, p.value
                            FROM person_phone jt
                            JOIN phonenumber p on p.id = jt.phone_id
                            ORDER BY jt.idx""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_person
                                           SET phone = COALESCE(phone || ', ' || ?, ?)
                                           WHERE po_id = ?
                                        """, [row.value, row.value, row.person_id]);
}
 
sourceConnection.eachRow("""SELECT jt.person_id, p.value
                            FROM person_fax jt
                            JOIN phonenumber p on p.id = jt.fax_id
                            ORDER BY jt.idx""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_person
                                           SET tty = COALESCE(tty || ', ' || ?, ?)
                                           WHERE po_id = ?
                                        """, [row.value, row.value, row.person_id]);
}

sourceConnection.eachRow("""SELECT jt.person_id, u.value
                            FROM person_url jt
                            JOIN url u on u.id = jt.url_id
                            ORDER BY jt.idx""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_person
                                           SET website_url = COALESCE(website_url || ', ' || ?, ?)
                                           WHERE po_id = ?
                                        """, [row.value, row.value, row.person_id]);
}
							
							sourceConnection.close()
							destinationConnection.close()
