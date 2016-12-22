import groovy.sql.Sql

def sql = """SELECT fam.id, fam.name, p30.serialnumber
             FROM family_p30_grant p30
             JOIN family fam ON (p30.family_id = fam.id)
          """

def sourceConnection = Sql.newInstance(properties['datawarehouse.po.jdbc.url'], properties['datawarehouse.po.db.username'],
    properties['datawarehouse.po.db.password'], properties['datawarehouse.po.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def p30grants = destinationConnection.dataSet("stg_dw_grants_p30")

sourceConnection.eachRow(sql) { row ->
    p30grants.add(
        family_id: row.id,
        family_name: row.name,
        serial_number: row.serialnumber
    )
}
sourceConnection.close()
destinationConnection.close()