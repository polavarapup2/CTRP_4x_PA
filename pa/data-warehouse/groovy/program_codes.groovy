import groovy.sql.Sql

def sql = """select f.po_id as family_po_id,null as org_family,pg.program_code, pg.program_name, pg.status_code 
			 from PROGRAM_CODE pg join family f on f.identifier=pg.family_id"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def programcodes = destinationConnection.dataSet("STG_DW_PROGRAM_CODE_MASTER_LIST")

sourceConnection.eachRow(sql) { row ->
    programcodes.add(
        family_po_id: row.family_po_id,
        org_family: row.org_family,
    	program_code: row.program_code,
    	program_name: row.program_name, 
    	program_code_status: row.status_code)
}

                                       
destinationConnection.eachRow("""select  family_id , family_name from STG_DW_FAMILY_ORGANIZATION f""") { row ->
        familyid = row.family_id
        familyname = row.family_name
destinationConnection.execute("UPDATE stg_dw_program_code_master_list SET org_family = ? where family_po_id = ? ", [familyname, familyid])
    }

sourceConnection.close()
destinationConnection.close()