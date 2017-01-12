import groovy.sql.Sql

def sourceConnection = Sql.newInstance(properties['datawarehouse.po.jdbc.url'], properties['datawarehouse.po.db.username'], 
    properties['datawarehouse.po.db.password'], properties['datawarehouse.po.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def orgs = destinationConnection.dataSet("STG_DW_ORGANIZATION_ROLE")

def sql = """SELECT id, assigned_identifier_extension, player_id, status, statusdate
             FROM identifiedorganization"""

sourceConnection.eachRow(sql) { row ->
    orgs.add(
        organization_po_id: row.player_id,
        role_po_id: row.id,
		identified_org_extension: row.assigned_identifier_extension,
		status: row.status,
		status_date: row.statusdate,
 	 	ROLE_NAME: "Identified Organization"
	)
}

sql = """select 
                         org.name,
                         org.id as organization_po_id,
			 sr.id as role_po_id,
			 sr.status,
			 sr.statusdate
			 from Organization org
			 inner join healthcarefacility SR on SR.player_id = org.id
			"""

sourceConnection.eachRow(sql) { row ->
    orgs.add(
        name: row.name,
        organization_po_id: row.organization_po_id,
        role_po_id: row.role_po_id,
 		status: row.status,
 		status_date: row.statusdate,
 	 	ROLE_NAME: "Healthcare Facility"
	)
}

sourceConnection.eachRow("""SELECT hcf_id, extension
                            FROM hcf_otheridentifier
							WHERE root = '2.16.840.1.113883.3.26.6.2'""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET ctep_id = ctep_id || chr(13) || ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Healthcare Facility'
                                       AND ctep_id IS NOT NULL
                                  """, [row.extension, row.hcf_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET ctep_id = ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Healthcare Facility'
                                       AND ctep_id IS NULL
                                  """, [row.extension, row.hcf_id]);
}

sourceConnection.eachRow("""SELECT hcf_address.hcf_id,
                                   add.streetaddressline, 
                                   COALESCE(add.deliveryaddressline,'') AS deliveryaddressline,
                                   add.cityormunicipality,
                                   COALESCE(add.stateorprovince,'') AS stateorprovince, 
                                   COALESCE(add.postalcode,'') AS postalcode,
                                   country.name 
                            FROM hcf_address 
							JOIN address add ON (hcf_address.address_id = add.id)
                            JOIN country ON (country.id = add.country_id)""") { row ->
    destinationConnection.executeUpdate("""
			UPDATE stg_dw_organization_role
               SET address_line_1 = address_line_1 || chr(13) || ?,
    	           address_line_2 = address_line_2 || chr(13) || ?,
    	           city = city || chr(13) || ?,
            	   state_or_province = state_or_province || chr(13) || ?,
    	           postal_code = postal_code || chr(13) || ?,
    	           country = country || chr(13) || ?
            WHERE role_po_id = ?
			  AND role_name = 'Healthcare Facility'
              AND address_line_1 IS NOT NULL
        """, [row.streetaddressline, row.deliveryaddressline, row.cityormunicipality,
		      row.stateorprovince, row.postalcode, row.name, row.hcf_id]);
    destinationConnection.executeUpdate("""
			UPDATE stg_dw_organization_role
               SET address_line_1 = ?,
    	           address_line_2 = ?,
    	           city = ?,
            	   state_or_province = ?,
    	           postal_code = ?,
    	           country = ?
            WHERE role_po_id = ?
			  AND role_name = 'Healthcare Facility'
              AND address_line_1 IS NULL
        """, [row.streetaddressline, row.deliveryaddressline, row.cityormunicipality,
		      row.stateorprovince, row.postalcode, row.name, row.hcf_id]);
}

sourceConnection.eachRow("""SELECT hcf_id, value
                            FROM hcf_email
							JOIN email ON (hcf_email.email_id = email.id)""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET email = email || chr(13) || ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Healthcare Facility'
                                       AND email IS NOT NULL
                                  """, [row.value, row.hcf_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET email = ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Healthcare Facility'
                                       AND email IS NULL
                                  """, [row.value, row.hcf_id]);
}

sourceConnection.eachRow("""SELECT rel.hcf_id, e.value
                            FROM hcf_fax rel
                            JOIN phonenumber e ON (e.id = rel.fax_id)""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET fax = fax || chr(13) || ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Healthcare Facility'
                                       AND fax IS NOT NULL
                                  """, [row.value, row.hcf_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET fax = ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Healthcare Facility'
                                       AND fax IS NULL
                                  """, [row.value, row.hcf_id]);
}

sourceConnection.eachRow("""SELECT rel.hcf_id, e.value
                            FROM hcf_phone rel
                            JOIN phonenumber e ON (e.id = rel.phone_id)""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET phone = phone || chr(13) || ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Healthcare Facility'
                                       AND phone IS NOT NULL
                                  """, [row.value, row.hcf_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET phone = ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Healthcare Facility'
                                       AND phone IS NULL
                                  """, [row.value, row.hcf_id]);
}

sourceConnection.eachRow("""SELECT rel.hcf_id, e.value
                            FROM hcf_tty rel
                            JOIN phonenumber e ON (e.id = rel.tty_id)""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET tty = tty || chr(13) || ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Healthcare Facility'
                                       AND tty IS NOT NULL
                                  """, [row.value, row.hcf_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET tty = ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Healthcare Facility'
                                       AND tty IS NULL
                                  """, [row.value, row.hcf_id]);
}


sql = """select 
                         org.name,
                         org.id as organization_po_id,
			 sr.id as role_po_id,
			 sr.status,
			 sr.statusdate,
			 srt.code
			 from Organization org
			 inner join researchorganization SR on SR.player_id = org.id
			 left outer join researchorganizationtype srt on sr.typecode_id=srt.id
			"""

sourceConnection.eachRow(sql) { row ->
    orgs.add(
        name: row.name,
        organization_po_id: row.organization_po_id,
        role_po_id: row.role_po_id,
 		status: row.status,
 		status_date: row.statusdate,
 		role_type: row.code,
 	 	ROLE_NAME: "Research Organization"
	)
}

sourceConnection.eachRow("""SELECT ro_id, extension
                            FROM ro_otheridentifier
							WHERE root = '2.16.840.1.113883.3.26.6.2'""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET ctep_id = ctep_id || chr(13) || ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Research Organization'
                                       AND ctep_id IS NOT NULL
                                  """, [row.extension, row.ro_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET ctep_id = ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Research Organization'
                                       AND ctep_id IS NULL
                                  """, [row.extension, row.ro_id]);
}

sourceConnection.eachRow("""SELECT ro_address.ro_id,
                                   add.streetaddressline, 
                                   COALESCE(add.deliveryaddressline,'') AS deliveryaddressline,
                                   add.cityormunicipality,
                                   COALESCE(add.stateorprovince,'') AS stateorprovince, 
                                   COALESCE(add.postalcode,'') AS postalcode,
                                   country.name 
                            FROM ro_address 
							JOIN address add ON (ro_address.address_id = add.id)
                            JOIN country ON (country.id = add.country_id)""") { row ->
    destinationConnection.executeUpdate("""
			UPDATE stg_dw_organization_role
               SET address_line_1 = address_line_1 || chr(13) || ?,
    	           address_line_2 = address_line_2 || chr(13) || ?,
    	           city = city || chr(13) || ?,
            	   state_or_province = state_or_province || chr(13) || ?,
    	           postal_code = postal_code || chr(13) || ?,
    	           country = country || chr(13) || ?
            WHERE role_po_id = ?
			  AND role_name = 'Research Organization'
              AND address_line_1 IS NOT NULL
        """, [row.streetaddressline, row.deliveryaddressline, row.cityormunicipality,
		      row.stateorprovince, row.postalcode, row.name, row.ro_id]);
    destinationConnection.executeUpdate("""
			UPDATE stg_dw_organization_role
               SET address_line_1 = ?,
    	           address_line_2 = ?,
    	           city = ?,
            	   state_or_province = ?,
    	           postal_code = ?,
    	           country = ?
            WHERE role_po_id = ?
			  AND role_name = 'Research Organization'
              AND address_line_1 IS NULL
        """, [row.streetaddressline, row.deliveryaddressline, row.cityormunicipality,
		      row.stateorprovince, row.postalcode, row.name, row.ro_id]);
}

sourceConnection.eachRow("""SELECT ro_id, value
                            FROM ro_email
							JOIN email ON (ro_email.email_id = email.id)""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET email = email || chr(13) || ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Research Organization'
                                       AND email IS NOT NULL
                                  """, [row.value, row.ro_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET email = ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Research Organization'
                                       AND email IS NULL
                                  """, [row.value, row.ro_id]);
}

sourceConnection.eachRow("""SELECT rel.ro_id, e.value
                            FROM ro_fax rel
                            JOIN phonenumber e ON (e.id = rel.fax_id)""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET fax = fax || chr(13) || ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Research Organization'
                                       AND fax IS NOT NULL
                                  """, [row.value, row.ro_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET fax = ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Research Organization'
                                       AND fax IS NULL
                                  """, [row.value, row.ro_id]);
}

sourceConnection.eachRow("""SELECT rel.ro_id, e.value
                            FROM ro_phone rel
                            JOIN phonenumber e ON (e.id = rel.phone_id)""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET phone = phone || chr(13) || ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Research Organization'
                                       AND phone IS NOT NULL
                                  """, [row.value, row.ro_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET phone = ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Research Organization'
                                       AND phone IS NULL
                                  """, [row.value, row.ro_id]);
}

sourceConnection.eachRow("""SELECT rel.ro_id, e.value
                            FROM ro_tty rel
                            JOIN phonenumber e ON (e.id = rel.tty_id)""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET tty = tty || chr(13) || ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Research Organization'
                                       AND tty IS NOT NULL
                                  """, [row.value, row.ro_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET tty = ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Research Organization'
                                       AND tty IS NULL
                                  """, [row.value, row.ro_id]);
}

sql = """select 
             org.name,
             org.id as organization_po_id,
			 sr.id as role_po_id,
			 sr.status,
			 sr.statusdate,
			 srt.code
			 from Organization org
			 inner join oversightcommittee SR on SR.player_id = org.id
			 left outer join oversightcommitteetype srt on sr.typecode_id=srt.id
			"""

sourceConnection.eachRow(sql) { row ->
    orgs.add(
        name: row.name,
        organization_po_id: row.organization_po_id,
        role_po_id: row.role_po_id,
 		status: row.status,
 		status_date: row.statusdate,
 		role_type: row.code,
 	 	ROLE_NAME: "Oversight Committee"
	)
}

sourceConnection.eachRow("""SELECT oco_id, extension
                            FROM oco_otheridentifier
							WHERE root = '2.16.840.1.113883.3.26.6.2'""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET ctep_id = ctep_id || chr(13) || ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Oversight Committee'
                                       AND ctep_id IS NOT NULL
                                  """, [row.extension, row.oco_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET ctep_id = ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Oversight Committee'
                                       AND ctep_id IS NULL
                                  """, [row.extension, row.oco_id]);
}

sourceConnection.eachRow("""SELECT oc_address.oc_id,
                                   add.streetaddressline, 
                                   COALESCE(add.deliveryaddressline,'') AS deliveryaddressline,
                                   add.cityormunicipality,
                                   COALESCE(add.stateorprovince,'') AS stateorprovince, 
                                   COALESCE(add.postalcode,'') AS postalcode,
                                   country.name 
                            FROM oc_address 
							JOIN address add ON (oc_address.address_id = add.id)
                            JOIN country ON (country.id = add.country_id)""") { row ->
    destinationConnection.executeUpdate("""
			UPDATE stg_dw_organization_role
               SET address_line_1 = address_line_1 || chr(13) || ?,
    	           address_line_2 = address_line_2 || chr(13) || ?,
    	           city = city || chr(13) || ?,
            	   state_or_province = state_or_province || chr(13) || ?,
    	           postal_code = postal_code || chr(13) || ?,
    	           country = country || chr(13) || ?
            WHERE role_po_id = ?
			  AND role_name = 'Oversight Committee'
              AND address_line_1 IS NOT NULL
        """, [row.streetaddressline, row.deliveryaddressline, row.cityormunicipality,
		      row.stateorprovince, row.postalcode, row.name, row.oc_id]);
    destinationConnection.executeUpdate("""
			UPDATE stg_dw_organization_role
               SET address_line_1 = ?,
    	           address_line_2 = ?,
    	           city = ?,
            	   state_or_province = ?,
    	           postal_code = ?,
    	           country = ?
            WHERE role_po_id = ?
			  AND role_name = 'Oversight Committee'
              AND address_line_1 IS NULL
        """, [row.streetaddressline, row.deliveryaddressline, row.cityormunicipality,
		      row.stateorprovince, row.postalcode, row.name, row.oc_id]);
}

sourceConnection.eachRow("""SELECT oc_id, value
                            FROM oc_email
							JOIN email ON (oc_email.email_id = email.id)""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET email = email || chr(13) || ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Oversight Committee'
                                       AND email IS NOT NULL
                                  """, [row.value, row.oc_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET email = ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Oversight Committee'
                                       AND email IS NULL
                                  """, [row.value, row.oc_id]);
}

sourceConnection.eachRow("""SELECT rel.oc_id, e.value
                            FROM oc_fax rel
                            JOIN phonenumber e ON (e.id = rel.fax_id)""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET fax = fax || chr(13) || ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Oversight Committee'
                                       AND fax IS NOT NULL
                                  """, [row.value, row.oc_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET fax = ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Oversight Committee'
                                       AND fax IS NULL
                                  """, [row.value, row.oc_id]);
}

sourceConnection.eachRow("""SELECT rel.oc_id, e.value
                            FROM oc_phone rel
                            JOIN phonenumber e ON (e.id = rel.phone_id)""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET phone = phone || chr(13) || ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Oversight Committee'
                                       AND phone IS NOT NULL
                                  """, [row.value, row.oc_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET phone = ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Oversight Committee'
                                       AND phone IS NULL
                                  """, [row.value, row.oc_id]);
}

sourceConnection.eachRow("""SELECT rel.oc_id, e.value
                            FROM oc_tty rel
                            JOIN phonenumber e ON (e.id = rel.tty_id)""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET tty = tty || chr(13) || ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Oversight Committee'
                                       AND tty IS NOT NULL
                                  """, [row.value, row.oc_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_organization_role
                                     SET tty = ?
                                     WHERE role_po_id = ?
									   AND role_name = 'Oversight Committee'
                                       AND tty IS NULL
                                  """, [row.value, row.oc_id]);
}
							
							sourceConnection.close()
							destinationConnection.close()
