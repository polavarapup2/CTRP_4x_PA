import groovy.sql.Sql

def sql = """select f.name as family_name, o.name as org_name, fo.functionaltype, f.id as family_id, o.id as org_id
                from organization o
                join familyorganizationrelationship fo on fo.organization_id=o.id
                join family f on f.id=fo.family_id where fo.enddate is null and f.statuscode='ACTIVE' order by f.name asc"""
def sourceConnection = Sql.newInstance(properties['datawarehouse.po.jdbc.url'], properties['datawarehouse.po.db.username'], 
    properties['datawarehouse.po.db.password'], properties['datawarehouse.po.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def paConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
    
def org_families = destinationConnection.dataSet("STG_DW_FAMILY_ORGANIZATION");

sourceConnection.eachRow(sql) { row ->
    org_families.add(family_name: row.family_name, organization_name: row.org_name,
                     functionaltype: row.functionaltype, family_id: row.family_id, organization_id: row.org_id, 
                     reporting_period_end_date:null,reporting_period_months:null)
}

                                       
paConnection.eachRow("""select f.rep_period_end, f.rep_period_len_months,f.po_id from family f""") { row ->
        rep_date = row.rep_period_end
        rep_period = row.rep_period_len_months
        familyId = row.po_id
destinationConnection.execute("UPDATE STG_DW_FAMILY_ORGANIZATION SET reporting_period_end_date = ?, reporting_period_months=? where family_id = ? ", [rep_date, rep_period, familyId])
    }


sourceConnection.close()
destinationConnection.close()
paConnection.close()