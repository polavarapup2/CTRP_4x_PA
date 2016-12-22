import groovy.sql.Sql


class PoPreLoad {

	Sql poConn

	PoPreLoad(poConn) {
		this.poConn = poConn
	}



	Map getOrgsMap() {
		def orgSQL = """select 
             add.streetaddressline,
             add.deliveryaddressline,
             add.postalcode,
             add.cityormunicipality,
             country.name as country_name,
             ctepid.assigned_identifier_extension as ctep_id,
             org.id as org_poid,
             org.status,
             org.statusdate,
             org.name,
             add.stateorprovince,
             e.value as email,
             fax.value as faxnumber,     
             phone.value as phone,
             tty.value as tty
             from Organization org
             left outer join address add on add.id = org.postal_address_id
             left outer join identifiedorganization ctepid on ctepid.player_id = org.id and ctepid.assigned_identifier_root = '2.16.840.1.113883.3.26.6.2'
             left outer join country on country.id = add.country_id
             left outer join organization_email o_e on o_e.organization_id = org.id
             left outer join email e on e.id = o_e.email_id
             left outer join organization_fax o_f on o_f.organization_id = org.id
             left outer join phonenumber fax on fax.id = o_f.fax_id
             left outer join organization_phone o_ph on o_ph.organization_id = org.id
             left outer join phonenumber phone on phone.id = o_ph.phone_id           
             left outer join organization_tty o_tty on o_tty.organization_id = org.id
             left outer join phonenumber tty on tty.id = o_tty.tty_id
             """

		println "preloading orgs..."

		def result = [:]
		poConn.eachRow(orgSQL) { row ->
			result.put(row.org_poid, row.toRowResult())
		}
		return result
	}
}