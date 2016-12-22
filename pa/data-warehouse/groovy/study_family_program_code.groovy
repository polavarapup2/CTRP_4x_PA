import groovy.sql.Sql

def sql = """select so.extension as nci_id,null as org_family,f.po_id,CASE WHEN count(pg.program_code)>1 THEN  array_to_string(array_agg(pg.program_code || ' - ' || pg.program_name ),' ; ')
ELSE array_to_string(array_agg(pg.program_code),', ')  END as program_codes from study_program_code spc 
join study_protocol sp on sp.identifier=spc.study_protocol_id
join study_otheridentifiers so on so.study_protocol_id= sp.identifier
join program_code pg on pg.identifier=spc.program_code_id
join family f on f.identifier=pg.family_id
where  so.root='2.16.840.1.113883.3.26.4.3' and sp.status_code='ACTIVE' group by f.po_id, so.extension order by 
so.extension"""

def dt4ProgramCodesSql ="""select so.extension as nci_id,f.po_id,CASE WHEN count(pg.program_code)>1 THEN  
array_to_string(array_agg(distinct pg.program_code order by  pg.program_code),' ; '  )
ELSE array_to_string(array_agg(pg.program_code),', ')  END as program_codes from study_program_code spc 
join study_protocol sp on sp.identifier=spc.study_protocol_id
join study_otheridentifiers so on so.study_protocol_id= sp.identifier
join program_code pg on pg.identifier=spc.program_code_id
join family f on f.identifier=pg.family_id
where  so.root='2.16.840.1.113883.3.26.4.3' group by f.po_id, so.extension order by 
so.extension"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def familyprogramcodes = destinationConnection.dataSet("stg_dw_study_family_program_code")

sourceConnection.eachRow(sql) { row ->
    familyprogramcodes.add(
        NCI_ID: row.nci_id,
        org_family:row.org_family,
    	family_po_id: row.po_id,
    	program_codes: row.program_codes
    	)
}

destinationConnection.eachRow("""select  family_id , family_name from STG_DW_FAMILY_ORGANIZATION f""") { row ->
        familyid = row.family_id
        familyname = row.family_name
destinationConnection.execute("UPDATE stg_dw_study_family_program_code SET org_family = ? where family_po_id = ? ", [familyname, familyid])
    }

sourceConnection.eachRow(dt4ProgramCodesSql) { row -> 
    destinationConnection.execute("UPDATE stg_dw_study_family_program_code SET dt4_program_codes = ? where family_po_id = ?  and  NCI_ID=?",
         [row.program_codes,row.po_id, row.nci_id])
}
sourceConnection.close()
destinationConnection.close()